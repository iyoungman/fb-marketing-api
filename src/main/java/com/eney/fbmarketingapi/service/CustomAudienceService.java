package com.eney.fbmarketingapi.service;

import com.eney.fbmarketingapi.dto.*;
import com.eney.fbmarketingapi.exception.UserDefineException;
import com.facebook.ads.sdk.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by YoungMan on 2019-04-26.
 */

@Service
@RequiredArgsConstructor
public class CustomAudienceService {

	private APIContext apiContext;


	public void setApiContext(String accessToken) {
		if (apiContext == null) {
			apiContext = new APIContext(accessToken).enableDebug(true);
		}
	}


	public void createCustomAudience(CustomAudienceCreateRequest customAudienceCreateRequest) {

		try {
			new AdAccount(customAudienceCreateRequest.getAccountId(), apiContext).createCustomAudience()
					.setName(customAudienceCreateRequest.getName())//맞춤타겟이름
					.setDescription(customAudienceCreateRequest.getDescription())//잠재고객 설명
					.setCustomerFileSource(CustomAudience.EnumCustomerFileSource.VALUE_USER_PROVIDED_ONLY)//고객 정보를 수집한 방법
					.setSubtype(CustomAudience.EnumSubtype.VALUE_CUSTOM)//잠재고객 유형
					.execute();
		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객 저장 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	public void updateCustomAudience(CustomAudienceUpdateRequest customAudienceUpdateRequest) {

		try {
			CustomAudience customAudience = findCustomAudience(customAudienceUpdateRequest.getCustomAudienceId());

			//잠재고객 이름 수정
			if(StringUtils.isNotBlank(customAudienceUpdateRequest.getUpdateName())) {
				customAudience.update()
						.setName(customAudienceUpdateRequest.getUpdateName())
						.execute();
			}

			//잠재고객 설명 수정
			if(StringUtils.isNotBlank(customAudienceUpdateRequest.getUpdateDescription())) {
				customAudience.update()
						.setDescription(customAudienceUpdateRequest.getUpdateDescription())
						.execute();
			}

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객 이름, 설명 수정 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	public void deleteCustomAudience(String customAudienceId) {

		try {
			CustomAudience customAudience = findCustomAudience(customAudienceId);
			customAudience.delete().execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객 삭제 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	public CustomAudience findCustomAudience(String customAudienceId) {

		try {
			return new CustomAudience(customAudienceId, apiContext).get()
					.requestNameField()
					.requestCustomerFileSourceField()
					.requestDataSourceField()
					.requestDescriptionField()
					.execute();
		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	public CustomAudienceResponse getCustomAudience(String customAudienceId) {

		try {
			CustomAudience customAudience = new CustomAudience(customAudienceId, apiContext).get()
					.requestNameField()
					.requestCustomerFileSourceField()
					.requestDataSourceField()
					.requestDescriptionField()
					.execute();

			return CustomAudienceResponse.of(customAudience);

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	public CustomAudienceListResponse getCustomAudiences(String accountId) {

		try {
			APINodeList<CustomAudience> apiNodeList = new AdAccount(accountId, apiContext).getCustomAudiences()
					.requestField("name")//잠재고객 이름 조회
					.requestField("description")
					.execute();

			List<CustomAudienceResponse> customAudienceResponses = apiNodeList.stream()
					.map(i -> CustomAudienceResponse.of(i))
					.collect(Collectors.toList());

			return new CustomAudienceListResponse(customAudienceResponses);

		} catch (APIException e) {
			throw UserDefineException.builder()
					.message("잠재고객 리스트 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 핸드폰 번호를 기반으로 저장하기 위해 hash 값으로 변환
	 */
	public boolean saveUserInCustomAudience(CustomAudienceDetailRequest customAudienceDetailRequest) {

		final String KOREAN_HASHCODE = DigestUtils.sha256Hex("82");
		List<String> hashedDataList = new ArrayList<>();

		CustomAudience customAudience = findCustomAudience(customAudienceDetailRequest.getCustomAudienceId());
		List<String> phoneNums = customAudienceDetailRequest.getPhoneNums();

		for (String phoneNumber : phoneNums) {
			hashedDataList.add("[\"" + DigestUtils.sha256Hex(phoneNumber) + "\"," + "\"" + KOREAN_HASHCODE + "\"]");
		}
		String data = String.join(",", hashedDataList);

		try {
			customAudience.createUser()
					.setPayload("{\"schema\":[\"PHONE\", \"COUNTRY\"],\"data\":[" + data + "]}")
					.execute();

			return true;
		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("잠재고객에 유저정보 저장 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}
}
