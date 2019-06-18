package com.eney.fbmarketingapi.dto;

import com.facebook.ads.sdk.Campaign;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungMan on 2019-06-11.
 */

@Getter
@NoArgsConstructor
public class CampaignsResponse {

	private List<CampaignDto> campaignDtos = new ArrayList<>();


	@Builder
	public CampaignsResponse(List<CampaignDto> campaignDtos) {
		this.campaignDtos = campaignDtos;
	}



	@Getter @Setter
	@NoArgsConstructor
	public static class CampaignDto {
		private String id;
		private String name;

		@Builder
		public CampaignDto(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public static CampaignDto of(Campaign campaign) {
			return CampaignDto.builder()
					.id(campaign.getId())
					.name(campaign.getFieldName())
					.build();
		}
	}
}




