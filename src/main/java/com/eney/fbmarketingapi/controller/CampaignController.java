package com.eney.fbmarketingapi.controller;

import com.eney.fbmarketingapi.dto.CampaignCreateRequest;
import com.eney.fbmarketingapi.dto.CampaignUpdateRequest;
import com.eney.fbmarketingapi.dto.CampaignsResponse;
import com.eney.fbmarketingapi.service.CampaignService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by YoungMan on 2019-05-14.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/campaign")
@RequiredArgsConstructor
public class CampaignController {

	private final CampaignService campaignService;


	@ApiOperation(value = "광고계정 내 캠페인 저장", notes = "광고계정 내 캠페인 저장")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고계정 ID", required = true),
			@ApiImplicitParam(name = "campaignName", value = "만들 캠페인 이름", required = true)
	})
	@PostMapping
	public void createCampaign(@RequestBody CampaignCreateRequest campaignCreateRequest,
							   @RequestHeader(value="x-access-token") String accessToken) {

		log.info("campaignCreateRequest => {}", campaignCreateRequest);
		log.info("accessToken => {}", accessToken);

		campaignService.setApiContext(accessToken);
		campaignService.createCampaign(campaignCreateRequest);
	}


	@ApiOperation(value = "캠페인 이름 수정", notes = "캠페인 이름 수정")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고계정 ID", required = true),
			@ApiImplicitParam(name = "updateName", value = "수정 될 캠페인 이름", required = true)
	})
	@PutMapping
	public void updateCampaign(@RequestBody CampaignUpdateRequest campaignUpdateRequest,
							   @RequestHeader(value="x-access-token") String accessToken) {

		log.info("campaignUpdateRequest => {}", campaignUpdateRequest);
		log.info("accessToken => {}", accessToken);

		campaignService.setApiContext(accessToken);
		campaignService.updateCampaign(campaignUpdateRequest);
	}


	@ApiOperation(value = "캠페인 삭제", notes = "캠페인 삭제")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "campaignId", value = "캠페인 ID", required = true)
	})
	@DeleteMapping
	public void deleteCampaign(@RequestParam("campaignId") String campaignId,
							   @RequestHeader(value="x-access-token") String accessToken) {

		log.info("campaignId => {}", campaignId);
		log.info("accessToken => {}", accessToken);

		campaignService.setApiContext(accessToken);
		campaignService.deleteCampaign(campaignId);
	}


	@ApiOperation(value = "광고계정 내 캠페인 리스트 조회", notes = "광고계정 내 캠페인 리스트 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고계정 ID", required = true)
	})
	@GetMapping
	public CampaignsResponse getCampaigns(@RequestParam("accountId") String accountId,
										  @RequestHeader(value="x-access-token") String accessToken) {

		log.info("accountId => {}", accountId);
		log.info("accessToken => {}", accessToken);

		campaignService.setApiContext(accessToken);
		return campaignService.getCampaigns(accountId);
	}


}
