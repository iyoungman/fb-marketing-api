package com.eney.fbmarketingapi.service;

import com.eney.fbmarketingapi.dto.CampaignCreateRequest;
import com.eney.fbmarketingapi.dto.CampaignUpdateRequest;
import com.eney.fbmarketingapi.dto.CampaignsResponse;
import com.eney.fbmarketingapi.exception.UserDefineException;
import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by YoungMan on 2019-04-22.
 */

@Service
@RequiredArgsConstructor
public class CampaignService {

	private APIContext apiContext;


	public void setApiContext(String accessToken) {
		if (apiContext == null) {
			apiContext = new APIContext(accessToken).enableDebug(true);
		}
	}

	public void createCampaign(CampaignCreateRequest campaignCreateRequest) {

		try {
			new AdAccount(campaignCreateRequest.getAccountId(), apiContext).createCampaign()
					.setName(campaignCreateRequest.getCampaignName())
					.setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
					.setStatus(Campaign.EnumStatus.VALUE_PAUSED)
					.execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 저장 실패")
					.originalMessage(e.toString())
					.build();
		}
	}

	public void updateCampaign(CampaignUpdateRequest campaignUpdateRequest) {

		try {
			Campaign campaign = Campaign.fetchById(campaignUpdateRequest.getCampaignId(), apiContext);
			campaign.update()
					.setName(campaignUpdateRequest.getUpdateName())
					.execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 이름 수정 실패")
					.originalMessage(e.toString())
					.build();
		}
	}

	public void deleteCampaign(String campaignId) {

		try {
			Campaign campaign = Campaign.fetchById(campaignId, apiContext);
			campaign.delete().execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 삭제 실패")
					.originalMessage(e.toString())
					.build();
		}
	}


	public CampaignsResponse getCampaigns(String accountId) {

		try {
			AdAccount account = new AdAccount(accountId, apiContext);
			APINodeList<Campaign> campaigns = account.getCampaigns()
					.requestNameField()
					.requestStatusField()
//					.setEffectiveStatus(Arrays.asList(Campaign.EnumEffectiveStatus.VALUE_ACTIVE, Campaign.EnumEffectiveStatus.VALUE_PAUSED))
					.execute();

			List<CampaignsResponse.CampaignDto> campaignDtos = campaigns.stream()
					.map(i -> CampaignsResponse.CampaignDto.of(i))
					.collect(Collectors.toList());

			return CampaignsResponse.builder()
					.campaignDtos(campaignDtos)
					.build();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 리스트 조회 실패")
					.originalMessage(e.toString())
					.build();
		}
	}

}
