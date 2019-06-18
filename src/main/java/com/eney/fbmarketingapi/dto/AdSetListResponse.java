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
public class AdSetListResponse {

	private List<AdSetResponse> adSetResponses = new ArrayList<>();

	public AdSetListResponse(List<AdSetResponse> adSetResponses) {
		this.adSetResponses = adSetResponses;
	}
}
