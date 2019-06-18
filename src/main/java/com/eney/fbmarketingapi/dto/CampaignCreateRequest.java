package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-10.
 */

@Getter
@NoArgsConstructor
public class CampaignCreateRequest {

	private String accountId;
	private String campaignName;


	@Builder
	public CampaignCreateRequest(String accountId, String campaignName) {
		this.accountId = accountId;
		this.campaignName = campaignName;
	}
}
