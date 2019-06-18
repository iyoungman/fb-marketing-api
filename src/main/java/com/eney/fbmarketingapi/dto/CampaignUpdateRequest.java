package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-19.
 */

@Getter
@NoArgsConstructor
public class CampaignUpdateRequest {

	private String campaignId;
	private String updateName;


	@Builder
	public CampaignUpdateRequest(String campaignId, String updateName) {
		this.campaignId = campaignId;
		this.updateName = updateName;
	}
}
