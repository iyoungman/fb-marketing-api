package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-10.
 */

@Getter
@NoArgsConstructor
public class AdSetUpdateRequest {

	private String adSetId;
	private String customAudienceId;
	private String updateName;


	@Builder
	public AdSetUpdateRequest(String adSetId, String customAudienceId, String updateName) {
		this.adSetId = adSetId;
		this.customAudienceId = customAudienceId;
		this.updateName = updateName;
	}
}
