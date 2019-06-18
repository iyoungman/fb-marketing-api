package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-19.
 */

@Getter
@NoArgsConstructor
public class CustomAudienceUpdateRequest {

	private String customAudienceId;
	private String updateName;
	private String updateDescription;


	@Builder
	public CustomAudienceUpdateRequest(String customAudienceId, String updateName, String updateDescription) {
		this.customAudienceId = customAudienceId;
		this.updateName = updateName;
		this.updateDescription = updateDescription;
	}
}
