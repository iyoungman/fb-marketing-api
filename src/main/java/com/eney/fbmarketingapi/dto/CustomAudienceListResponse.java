package com.eney.fbmarketingapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungMan on 2019-06-11.
 */

@Getter
@NoArgsConstructor
public class CustomAudienceListResponse {

	private List<CustomAudienceResponse> customAudienceResponses = new ArrayList<>();

	public CustomAudienceListResponse(List<CustomAudienceResponse> customAudienceResponses) {
		this.customAudienceResponses = customAudienceResponses;
	}
}
