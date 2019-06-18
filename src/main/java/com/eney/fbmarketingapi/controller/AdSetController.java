package com.eney.fbmarketingapi.controller;

import com.eney.fbmarketingapi.dto.AdSetCreateRequest;
import com.eney.fbmarketingapi.dto.AdSetResponse;
import com.eney.fbmarketingapi.dto.AdSetListResponse;
import com.eney.fbmarketingapi.dto.AdSetUpdateRequest;
import com.eney.fbmarketingapi.service.AdSetService;
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
@RequestMapping(value = "/api/adset")
@RequiredArgsConstructor
public class AdSetController {

	private final AdSetService adSetService;


	@ApiOperation(value = "캠페인에 광고세트 생성", notes = "광고세트 생성하면서 기존에 만들어놓은 잠재고객도 세팅")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고계정 ID", required = true),
			@ApiImplicitParam(name = "campaignId", value = "캠페인 ID", required = true),
			@ApiImplicitParam(name = "adSetName", value = "만들 광고세트 이름", required = true),
			@ApiImplicitParam(name = "customAudienceId", value = "미리 만들어놓은 잠재고객 있을때만 필요", required = true)
	})
	@PostMapping
	public void createAdSet(@RequestBody AdSetCreateRequest adSetCreateRequest,
							@RequestHeader(value="x-access-token") String accessToken) {

		log.info("adSetCreateRequest => {}", adSetCreateRequest);
		log.info("accessToken => {}", accessToken);

		adSetService.setApiContext(accessToken);
		adSetService.createAdSet(adSetCreateRequest);
	}


	@ApiOperation(value = "광고세트에 update", notes =
			"1)기존에 만들어져있는 광고세트에 기존에 만들어 놓은 잠재고객 셋팅하거나 " +
			"2).광고세트 이름을 수정, " +
			"&&. 1)과 2) 둘 다 한꺼번에, 둘 중 하나만 일 때 수정 가능, 수정이 안되고 이전 내용 그대로이다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "adSetId", value = "광고세트 ID", required = true),
			@ApiImplicitParam(name = "updateName", value = "수정될 광고세트 이름", required = false),
			@ApiImplicitParam(name = "customAudienceId", value = "잠재고객 ID", required = false)
	})
	@PutMapping
	public void updateAdSet(@RequestBody AdSetUpdateRequest adSetUpdateRequest,
							@RequestHeader(value="x-access-token") String accessToken) {

		log.info("adSetUpdateRequest => {}", adSetUpdateRequest);
		log.info("accessToken => {}", accessToken);

		adSetService.setApiContext(accessToken);
		adSetService.updateAdSet(adSetUpdateRequest);
	}


	@ApiOperation(value = "광고세트 삭제", notes = "광고세트 삭제")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "adSetId", value = "광고세트 ID", required = true)
	})
	@DeleteMapping
	public void deleteAdSet(@RequestParam("adSetId") String adSetId,
							@RequestHeader(value="x-access-token") String accessToken) {

		log.info("adSetId => {}", adSetId);
		log.info("accessToken => {}", accessToken);

		adSetService.setApiContext(accessToken);
		adSetService.deleteAdSet(adSetId);
	}


	@ApiOperation("특정 광고세트 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "adSetId", value = "광고세트 ID", required = true),
	})
	@GetMapping
	public AdSetResponse getAdSet(@RequestParam("adSetId") String adSetId,
								  @RequestHeader(value="x-access-token") String accessToken) {

		log.info("adSetId => {}", adSetId);
		log.info("accessToken => {}", accessToken);

		adSetService.setApiContext(accessToken);
		return adSetService.getAdSet(adSetId);
	}


	@ApiOperation("캠페인 내 광고세트 리스트 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "campaignId", value = "캠페인 ID", required = true),
	})
	@GetMapping("/list")
	public AdSetListResponse getAllAdSets(@RequestParam("campaignId") String campaignId,
										  @RequestHeader(value="x-access-token") String accessToken) {

		log.info("campaignId => {}", campaignId);
		log.info("accessToken => {}", accessToken);

		adSetService.setApiContext(accessToken);
		return adSetService.getAllAdSets(campaignId);
	}


}
