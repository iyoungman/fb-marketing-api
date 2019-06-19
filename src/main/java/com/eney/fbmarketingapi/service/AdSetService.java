package com.eney.fbmarketingapi.service;

import com.eney.fbmarketingapi.dto.*;
import com.eney.fbmarketingapi.exception.UserDefineException;
import com.facebook.ads.sdk.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by YoungMan on 2019-04-24.
 */

@Service
@RequiredArgsConstructor
public class AdSetService {

	private APIContext apiContext;
	private final CustomAudienceService customAudienceService;


	public void setApiContext(String accessToken) {
		if (apiContext == null) {
			apiContext = new APIContext(accessToken).enableDebug(true);
		}
	}


	/**
	 * 광고세트 생성
	 */
	public void createAdSet(AdSetCreateRequest adSetCreateRequest) {

		try {
			CustomAudience customAudience = customAudienceService.findCustomAudience(
					adSetCreateRequest.getCustomAudienceId()
			);
			String customAudienceFieldId = customAudience.getFieldId();

			new AdAccount(adSetCreateRequest.getAccountId(), apiContext).createAdSet()
					.setName(adSetCreateRequest.getAdSetName())
					.setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_REACH)
					.setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
					.setBidAmount(2L)
					.setDailyBudget(1000L)
					.setCampaignId(adSetCreateRequest.getCampaignId())
					.setTargeting(
							new Targeting()
									.setFieldCustomAudiences("[{id:" + customAudienceFieldId + "}]")
					)
					.setStatus(AdSet.EnumStatus.VALUE_ACTIVE)
					.execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("광고세트 생성 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 광고세트 수정
	 */
	public boolean updateAdSet(AdSetUpdateRequest adSetUpdateRequest) {

		try {
			AdSet asSet = findAdSet(adSetUpdateRequest.getAdSetId());

			//CustomAudience 추가
			if (StringUtils.isNotBlank(adSetUpdateRequest.getCustomAudienceId())) {
				CustomAudience customAudience = customAudienceService.findCustomAudience(
						adSetUpdateRequest.getCustomAudienceId()
				);

				asSet.update().setTargeting(
						new Targeting()
								.setFieldCustomAudiences("[{id:" + customAudience.getFieldId() + "}]")
				)
						.execute();
			}

			//AdSet Name 수정
			if(StringUtils.isNotBlank(adSetUpdateRequest.getUpdateName())) {
				asSet.update()
						.setName(adSetUpdateRequest.getUpdateName())
						.execute();
			}

			return true;
		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("광고세트에 잠재고객 셋팅 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 광고세트 삭제
	 */
	public void deleteAdSet(String adSetId) {

		try {
			AdSet adSet = findAdSet(adSetId);
			adSet.delete().execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("광고세트 삭제 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 광고세트 찾기
	 */
	private AdSet findAdSet(String adSetId) {

		try {
			return new AdSet(adSetId, apiContext).get()
					.requestNameField()
					.requestConfiguredStatusField()
					.requestEffectiveStatusField()
					.requestTargetingField()
					.execute();
		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("광고세트 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 광고세트 조회
	 */
	public AdSetResponse getAdSet(String adSetId) {

		try {
			AdSet adSet = new AdSet(adSetId, apiContext).get()
					.requestNameField()
					.requestConfiguredStatusField()
					.requestEffectiveStatusField()
					.requestTargetingField()
					.execute();

			return AdSetResponse.of(adSet);

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("광고세트 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


	/**
	 * 광고세트 리스트 조회
	 */
	public AdSetListResponse getAllAdSets(String campaignId) {

		try {
			APINodeList<AdSet> adSets = new Campaign(campaignId, apiContext).getAdSets()
					.requestNameField()
					.requestConfiguredStatusField()
					.requestEffectiveStatusField()
					.requestStatusField()
					.requestTargetingField()
					.execute();

			List<AdSetResponse> adSetResponses = adSets.stream()
					.map(i -> AdSetResponse.of(i))
					.collect(Collectors.toList());

			return new AdSetListResponse(adSetResponses);

		} catch (APIException e) {
			throw UserDefineException.builder()
					.message("광고세트 리스트 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}


}
