package com.egmox.spotfinder.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.egmox.spotfinder.constants.GenericConstants;
import com.egmox.spotfinder.constants.MessageConstants;
import com.egmox.spotfinder.dto.APIResponse;
import com.egmox.spotfinder.dto.SearchDTO;
import com.egmox.spotfinder.management.AbstractManagement;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Service
@Slf4j
public class SpotResponseReaderConverter extends AbstractManagement {

	RestTemplate restTemplate = new RestTemplate();
	APIResponse response = null;

	String baseUrl = "https://places.ls.hereapi.com/places/v1/discover/explore?";

	public APIResponse getSpots(SearchDTO search, String apiKey) {

		if (isEmpty(apiKey)) {
			response = new APIResponse(MessageConstants.UNAUTHORIZED_CODE, getMessage(MessageConstants.UNAUTHORIZED));
			return response;
		}

		if (isEmpty(search.getLat()) && isEmpty(search.getLon())) {
			response = new APIResponse(MessageConstants.NO_LOCATION_ENTERED_CODE,
					getMessage(MessageConstants.NO_LOCATION_ENTERED));
			return response;
		}

		String urlFormed = urlBuilder(baseUrl, search, apiKey);

		log.info("URL formed: " + urlFormed);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("", headers);

		ResponseEntity<String> result;
		try {
			result = restTemplate.exchange(urlFormed, HttpMethod.GET, entity, String.class);
			log.info(result.toString());
			String resultBody = result.getBody();

			JSONArray jsonArray = JsonPath.read(resultBody, "$.results.items");

			ArrayList<JSONObject> placesList = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); placesList.add((JSONObject) jsonArray.get(i++)))
				;

			response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
					placesList);
		} catch (Exception e) {
			e.printStackTrace();
			response = new APIResponse(MessageConstants.NO_LOCATION_ENTERED_CODE,
					getMessage(MessageConstants.SOMETHING_WENT_WRONG));
		}
		return response;
	}

	private String urlBuilder(String baseUrl, SearchDTO search, String apiKey) {
		StringBuilder builder = new StringBuilder(baseUrl);
		builder.append("at=" + search.getLat() + "," + search.getLon());
		builder.append("&apiKey=" + apiKey);
		builder.append("&cat=" + getVariable(GenericConstants.CATEGORY));
		return builder.toString();
	}

	private boolean isEmpty(String value) {
		if (value == null || value.equals(""))
			return true;
		return false;
	}

	private boolean isEmpty(Float value) {
		if (value == null || value == 0.0)
			return true;
		return false;
	}

}
