package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by YoungMan on 2019-06-10.
 */

@Getter
@NoArgsConstructor
public class CustomAudienceCreateRequest {

	private String accountId;
	private String name;
	private String description;


	@Builder
	public CustomAudienceCreateRequest(String accountId, String name, String description) {
		this.accountId = accountId;
		this.name = name;
		this.description = description;
	}
}
