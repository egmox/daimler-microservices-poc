package com.egmox.spotfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egmox.spotfinder.constants.RestUriConstants;
import com.egmox.spotfinder.dto.APIResponse;
import com.egmox.spotfinder.dto.SearchDTO;
import com.egmox.spotfinder.service.SpotResponseReaderConverter;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestUriConstants.NEARBY_RESTAURANT)
@Slf4j
public class SpotFinder {
	APIResponse response = new APIResponse();

	@Autowired
	SpotResponseReaderConverter responseReaderConverter;

	@PostMapping(RestUriConstants.FIND)
	public APIResponse getNearbyPlaces(@RequestBody SearchDTO search, @RequestHeader(value = "api-key") String apiKey) {

		log.info("inside parking-finder: starting to search");
		return responseReaderConverter.getSpots(search, apiKey);
	}
}
