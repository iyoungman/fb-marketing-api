package com.eney.fbmarketingapi.dto;

import com.facebook.ads.sdk.CustomAudience;
import com.facebook.ads.sdk.RawCustomAudience;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by YoungMan on 2019-06-11.
 */

@Getter @Setter
@NoArgsConstructor
public class CustomAudienceResponse {

	private String id;
	private String name;
	private String description;


	@Builder
	public CustomAudienceResponse(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}


	public static CustomAudienceResponse of(CustomAudience customAudience) {
		return CustomAudienceResponse.builder()
				.id(customAudience.getId())
				.name(customAudience.getFieldName())
				.description(customAudience.getFieldDescription())
				.build();
	}

	public static CustomAudienceResponse ofRaw(RawCustomAudience rawCustomAudience) {
		return CustomAudienceResponse.builder()
				.id(rawCustomAudience.getId())
				.name(rawCustomAudience.getFieldName())
				.build();
	}
}
