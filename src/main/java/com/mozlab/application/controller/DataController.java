package com.mozlab.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mozlab.application.dto.CaptainTeamDto;
import com.mozlab.application.dto.response.WrapperResponseDto;
import com.mozlab.application.model.CaptainTeam;
import com.mozlab.application.service.IMongoDBService;

@RestController

public class DataController {

	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private IMongoDBService mongoDBService;

	@GetMapping("/captain/isExist/{teamId}")
	public ResponseEntity<WrapperResponseDto<CaptainTeamDto>> isExistCaptain(@PathVariable int teamId) {

		WrapperResponseDto<CaptainTeamDto> response = new WrapperResponseDto<>();

		if (!mongoDBService.isExistCaptainTeam(teamId)) {

			response.setResult(false);
			response.setPayLoad(null);
			return ResponseEntity.ok().body(response);

		}
		CaptainTeam result = mongoDBService.get(teamId);

		response.setResult(true);
		response.setPayLoad(result.dto());

		return ResponseEntity.ok().body(response);

	}

	@PostMapping("captain/create/")
	public ResponseEntity<Map<String, Boolean>> createCaptain(@RequestBody CaptainTeamDto captain) {

		Map<String, Boolean> response = new HashMap<>();

		if (!mongoDBService.isExistCaptainTeam(captain.getTeamId())) {

			CaptainTeam createdCaptain = mongoDBService.create(captain.getTeamId(), captain.getParticipantId(),
					captain.getParticipantIdentifier());

			if (createdCaptain != null) {

				response.put("success", true);

				logger.info("Create captain team {}", createdCaptain.toString());

				return ResponseEntity.ok().body(response);
			}

			else

			{
				response.put("success", false);

				return ResponseEntity.ok().body(response);

			}

		}

		response.put("success", false);

		return ResponseEntity.ok().body(response);

	}

	@PostMapping("captain/cancel/")
	public ResponseEntity<Map<String, Boolean>> cancelCaptain(@RequestBody CaptainTeamDto captain) {

		Map<String, Boolean> response = new HashMap<>();

		if (mongoDBService.isExistCaptainTeam(captain.getTeamId())) {

			mongoDBService.removeCaptainTeam(captain.getTeamId());

			response.put("success", true);

			logger.info("Cancel captain team {}", captain);

			return ResponseEntity.ok().body(response);

		}

		response.put("success", false);

		return ResponseEntity.ok().body(response);

	}

}
