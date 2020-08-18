package com.egmox.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egmox.poc.constants.RestUriConstants;
import com.egmox.poc.dto.APIResponse;
import com.egmox.poc.dto.SearchDTO;
import com.egmox.poc.service.ResponseReaderConverter;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestUriConstants.NEARBY_SPOTS)
@Slf4j
public class NearByFinder {
	APIResponse response = new APIResponse();

	@Autowired
	ResponseReaderConverter responseReaderConverter;

	@Autowired
	Environment env;

	@PostMapping(RestUriConstants.FIND)
	public APIResponse getNearbyPlaces(@RequestBody SearchDTO search, @RequestHeader(value = "api-key") String apiKey) {
		log.info("starting to search");
		return responseReaderConverter.getResponse(search, apiKey);
	}
}
