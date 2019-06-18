package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-10.
 */

@Getter
@NoArgsConstructor
public class AdSetCreateRequest {

	private String accountId;
	private String campaignId;
	private String customAudienceId;
	private String adSetName;


	@Builder
	public AdSetCreateRequest(String accountId, String campaignId, String customAudienceId, String adSetName) {
		this.accountId = accountId;
		this.campaignId = campaignId;
		this.customAudienceId = customAudienceId;
		this.adSetName = adSetName;
	}
}
