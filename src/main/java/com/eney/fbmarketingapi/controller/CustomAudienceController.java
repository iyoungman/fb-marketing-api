package com.eney.fbmarketingapi.controller;

import com.eney.fbmarketingapi.dto.*;
import com.eney.fbmarketingapi.service.CustomAudienceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by YoungMan on 2019-04-22.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/audience")
@RequiredArgsConstructor
public class CustomAudienceController {

	private final CustomAudienceService customAudienceService;


	@ApiOperation(value = "광고계정에 잠재고객 타겟팅 저장", notes = "광고계정에 잠재고객 타겟팅 저장")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고계정 ID", required = true),
			@ApiImplicitParam(name = "name", value = "만들 잠재고객 이름", required = true),
			@ApiImplicitParam(name = "description", value = "만들 잠재고객 설명", required = true)
	})
	@PostMapping
	public void createCustomAudience(@RequestBody CustomAudienceCreateRequest customAudienceCreateRequest,
									 @RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		customAudienceService.createCustomAudience(customAudienceCreateRequest);
	}


	@ApiOperation(value = "잠재고객 이름, 설명 수정", notes =
			"1).잠재고객 이름을 수정하거나 " +
			"2).잠재고객 설명을 수정, " +
			"&&. 1)과 2) 둘 다 한꺼번에, 둘 중 하나만 일 때 수정 가능, 둘 다 비어있으면 수정이 안되고 이전 내용 그대로이다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customAudienceId", value = "잠재고개 ID", required = true),
			@ApiImplicitParam(name = "updateName", value = "수정할 잠재고객 이름", required = false),
			@ApiImplicitParam(name = "updateDescription", value = "수정할 잠재고객 설명", required = false)
	})
	@PutMapping
	public void updateCustomAudience(@RequestBody CustomAudienceUpdateRequest customAudienceUpdateRequest,
									 @RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		customAudienceService.updateCustomAudience(customAudienceUpdateRequest);
	}


	@ApiOperation(value = "잠재고객 삭제", notes = "잠재고객 삭제")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customAudienceId", value = "잠재고객 ID", required = true)
	})
	@DeleteMapping
	public void deleteCustomAudience(@RequestParam("customAudienceId") String customAudienceId,
									 @RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		customAudienceService.deleteCustomAudience(customAudienceId);
	}


	@ApiOperation(value = "잠재고객 조회", notes = "잠재고객 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customAudienceId", value = "잠재 고객 ID", required = true)
	})
	@GetMapping
	public CustomAudienceResponse getCustomAudience(@RequestParam("customAudienceId") String customAudienceId,
													@RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		return customAudienceService.getCustomAudience(customAudienceId);
	}


	@ApiOperation(value = "광고계정 내 잠재고객 목록 조회", notes = "광고계정 내 잠재고객 목록 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountId", value = "광고 계정 ID", required = true)
	})
	@GetMapping("/list")
	public CustomAudienceListResponse getCustomAudiences(@RequestParam("accountId") String accountId,
														 @RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		return customAudienceService.getCustomAudiences(accountId);
	}


	@ApiOperation(value = "잠재고객 타겟팅에 자세한 유저정보 저장", notes = "핸드폰 번호 기반으로 저장")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customAudienceId", value = "잠재 고객 ID", required = true),
			@ApiImplicitParam(name = "phoneNums", value = "핸드폰 번호 목록", example = "10 5322 1661", required = true)
	})
	@PostMapping("/detail")
	public void saveUserInCustomAudience(@RequestBody CustomAudienceDetailRequest customAudienceDetailRequest,
										 @RequestHeader(value="x-access-token") String accessToken) {

		customAudienceService.setApiContext(accessToken);
		customAudienceService.saveUserInCustomAudience(customAudienceDetailRequest);
	}
}