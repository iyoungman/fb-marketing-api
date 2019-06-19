package com.eney.fbmarketingapi.service;

import com.eney.fbmarketingapi.dto.CampaignCreateRequest;
import com.eney.fbmarketingapi.dto.CampaignUpdateRequest;
import com.eney.fbmarketingapi.dto.CampaignsResponse;
import com.eney.fbmarketingapi.exception.UserDefineException;
import com.facebook.ads.sdk.*;
import com.google.gson.Gson;
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
					.originalMessage(e.getMessage())
					.build();
		}
	}

	public void updateCampaign(CampaignUpdateRequest campaignUpdateRequest) {

		try {
			Campaign campaign = findCampaign(campaignUpdateRequest.getCampaignId());
			campaign.update()
					.setName(campaignUpdateRequest.getUpdateName())
					.execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 이름 수정 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}

	public void deleteCampaign(String campaignId) {

		try {
			Campaign campaign = findCampaign(campaignId);
			campaign.delete().execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 삭제 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}

	private Campaign findCampaign(String campaignId) {

		try {
			return new Campaign(campaignId, apiContext).get()
					.requestNameField()
					.requestStatusField()
					.execute();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}

	public CampaignsResponse getCampaigns(String accountId) {

		try {
			AdAccount account = new AdAccount(accountId, apiContext);
			APINodeList<Campaign> campaigns = account.getCampaigns()
					.requestNameField()
					.requestStatusField()
//					.setEffectiveStatus(Arrays.asList(MyCampaign.EnumEffectiveStatus.VALUE_ACTIVE, MyCampaign.EnumEffectiveStatus.VALUE_PAUSED))
					.execute();

			List<CampaignsResponse.MyCampaign> myCampaigns = campaigns.stream()
					.map(i -> CampaignsResponse.MyCampaign.of(i))
					.collect(Collectors.toList());

			return CampaignsResponse.builder()
					.myCampaigns(myCampaigns)
					.build();

		} catch (Exception e) {
			throw UserDefineException.builder()
					.message("캠페인 리스트 조회 실패")
					.originalMessage(e.getMessage())
					.build();
		}
	}

}
