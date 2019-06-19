package com.eney.fbmarketingapi.dto;

import com.facebook.ads.sdk.AdSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by YoungMan on 2019-06-11.
 */

@Getter
@NoArgsConstructor
public class AdSetResponse {

	private String id;
	private String name;
	private Targeting targeting;


	@Builder
	public AdSetResponse(String id, String name, Targeting targeting) {
		this.id = id;
		this.name = name;
		this.targeting = targeting;
	}

	public static AdSetResponse of(AdSet adSet) {
		return AdSetResponse.builder()
				.id(adSet.getId())
				.name(adSet.getFieldName())
				.targeting(Targeting.builder()
						.ageMax(adSet.getFieldTargeting().getFieldAgeMax())
						.ageMin(adSet.getFieldTargeting().getFieldAgeMin())
						.customAudienceResponses(adSet.getFieldTargeting().getFieldCustomAudiences().stream()
								.map(i -> CustomAudienceResponse.ofRaw(i))
								.collect(Collectors.toList()))
						.build())
				.build();
	}


	@Getter
	@NoArgsConstructor
	public static class Targeting {
		private Long ageMax;
		private Long ageMin;
		private List<CustomAudienceResponse> customAudienceResponses = new ArrayList<>();

		@Builder
		public Targeting(Long ageMax, Long ageMin, List<CustomAudienceResponse> customAudienceResponses) {
			this.ageMax = ageMax;
			this.ageMin = ageMin;
			this.customAudienceResponses = customAudienceResponses;
		}
	}
}
