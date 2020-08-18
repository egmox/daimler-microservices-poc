package com.egmox.poc.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.egmox.poc.constants.GenericConstants;
import com.egmox.poc.constants.MessageConstants;
import com.egmox.poc.dto.APIResponse;
import com.egmox.poc.dto.ResultObject;
import com.egmox.poc.dto.SearchDTO;
import com.egmox.poc.management.AbstractManagement;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResponseReaderConverter extends AbstractManagement {
	
	@Autowired
	ResponseReaderConverter responseReaderConverter;

	RestTemplate restTemplate = new RestTemplate();
	String url = "";
	APIResponse response = null;

	public APIResponse getResponse(SearchDTO search, String apiKey) {
		ArrayList<ResultObject> resultList = new ArrayList<>();
		Object parkingObj = responseReaderConverter.getParking(apiKey, search);
//		Object evCharObject = responseReaderConverter.getEvCharging(apiKey, search);
//		Object restaurantObject = responseReaderConverter.getEvCharging(apiKey, search);
		response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
				parkingObj);
		return response;
	}

	@Async
	public APIResponse getParking(String apiKey, SearchDTO search) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat", search.getLat());
		jsonObject.put("lon", search.getLon());

		HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> result = restTemplate.exchange(getVariable(GenericConstants.PARKING_URL),
				HttpMethod.POST, entity, String.class);

		response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK), result);
		log.info(result.toString());
		return response;
	}

	@Async
	public APIResponse getEvCharging(String apiKey, SearchDTO search) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat", search.getLat());
		jsonObject.put("lon", search.getLon());

		HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK), result);
		log.info(result.toString());
		return response;
	}

	@Async
	public APIResponse getRestaurant(String apiKey, SearchDTO search) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat", search.getLat());
		jsonObject.put("lon", search.getLon());

		HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK), result);
		log.info(result.toString());
		return response;
	}

}
