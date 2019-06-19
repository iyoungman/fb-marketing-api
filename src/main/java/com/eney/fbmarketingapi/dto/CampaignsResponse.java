package com.eney.fbmarketingapi.dto;

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

	private List<MyCampaign> myCampaigns = new ArrayList<>();


	@Builder
	public CampaignsResponse(List<MyCampaign> myCampaigns) {
		this.myCampaigns = myCampaigns;
	}



	@Getter @Setter
	@NoArgsConstructor
	public static class MyCampaign {
		private String id;
		private String name;

		@Builder
		public MyCampaign(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public static MyCampaign of(com.facebook.ads.sdk.Campaign campaign) {
			return MyCampaign.builder()
					.id(campaign.getId())
					.name(campaign.getFieldName())
					.build();
		}
	}
}




