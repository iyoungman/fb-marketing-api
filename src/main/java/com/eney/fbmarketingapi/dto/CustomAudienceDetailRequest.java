package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungMan on 2019-06-10.
 */

@Getter
@NoArgsConstructor
public class CustomAudienceDetailRequest {

	private String customAudienceId;
	private List<String> phoneNums = new ArrayList<>();


	@Builder
	public CustomAudienceDetailRequest(String customAudienceId, List<String> phoneNums) {
		this.customAudienceId = customAudienceId;
		this.phoneNums = phoneNums;
	}
}
