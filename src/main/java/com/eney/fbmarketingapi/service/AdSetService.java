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
					.originalMessage(e.toString())
					.build();
		}
	}

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


	public AdSet findAdSet(String adSetId) {

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
					.originalMessage(e.toString())
					.build();
		}
	}


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
					.originalMessage(e.toString())
					.build();
		}
	}


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
					.originalMessage(e.toString())
					.build();
		}
	}


//	public void terere() {
//
//		try {
//			new Ad("23843470601260258", apiContext).getLeads()
//					.execute();
//		} catch (APIException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public void gett() {
//		String accessToken = SAMPLE_DATA.ACCESS_TOKEN;
//		String facebookAppId = SAMPLE_DATA.APPLICATION_ID_STR;
//		String facebookAppSecret = SAMPLE_DATA.APP_SECRET;
//
//		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
//		FacebookClient.AccessToken extendedAccessToken = null;
//		try {
//			extendedAccessToken = facebookClient.obtainExtendedAccessToken(facebookAppId, facebookAppSecret, accessToken);
//			System.out.println(extendedAccessToken);
//		} catch (FacebookException e) {
//			if (e.getMessage().contains("Error validating access token")) {
////				throw new UserDefineException("Error validating access token", e.toString(), );
//			}
//			throw new RuntimeException("Error exchanging short lived token for long lived token", e);
//		}
//		System.out.println(extendedAccessToken.getAccessToken());
////		return extendedAccessToken.getAccessToken();
//
//	}
//
//	public void searchUser() {
//		String facebookAppId = SAMPLE_DATA.APPLICATION_ID_STR;
//		String facebookAppSecret = SAMPLE_DATA.APP_SECRET;
//		FacebookClient.AccessToken appAccessToken = new DefaultFacebookClient(Version.VERSION_3_2)
//				.obtainAppAccessToken(facebookAppId, facebookAppSecret);
//
//		FacebookClient.AccessToken userAccessToken = new DefaultFacebookClient(Version.VERSION_3_2)
//				.obtainUserAccessToken(facebookAppId, facebookAppSecret, "http://localhost:8080/login", appAccessToken.getAccessToken());
//
//		System.out.println(userAccessToken.getAccessToken());
////		FacebookClient facebookClient23 = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_3);
////		User user = facebookClient23.fetchObject(id, User.class, Parameter.with("fields","about,age_range, birthday,context, education,email,gender, first_name, last_name, relationship_status, work"));
//
////		return user;
//	}
//
//	public void obtatin() {
//		String facebookAppId = SAMPLE_DATA.APPLICATION_ID_STR;
//		String facebookAppSecret = SAMPLE_DATA.APP_SECRET;
//		FacebookClient.AccessToken accessToken = new DefaultFacebookClient(Version.VERSION_3_2).obtainAppAccessToken(facebookAppId, facebookAppSecret);
//		System.out.println(accessToken.getAccessToken());
//	}


	/**
	 * 광고형식 디자인 만들기
	 */
	/*public AdCreative saveAdCreative() {

		APIContext context = new APIContext(ExampleConfig.ACCESS_TOKEN).enableDebug(true);
		try {
			AdCreative adCreative = new AdAccount(ExampleConfig.ACCOUNT_ID, context).createAdCreative()
					.setName("Sample Ad Creative")
					.setObjectStorySpec(
							new AdCreativeObjectStorySpec()
									.setFieldLinkData(
											new AdCreativeLinkData()
//													.setFieldCaption("My caption")
													//												.setFieldImageHash()
													.setFieldLink("https://www.facebook.com/327278767980313")
													.setFieldMessage("try it out")
									)
									.setFieldPageId(ExampleConfig.PAGE_ID)
					)
					.execute();

			return adCreative;
		} catch (APIException e) {
			e.printStackTrace();
		}
		return null;
	}*/

}
