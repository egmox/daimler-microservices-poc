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
import com.egmox.poc.dto.SearchDTO;
import com.egmox.poc.management.AbstractManagement;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Service
@Slf4j
public class ResponseReaderConverter extends AbstractManagement {

	@Autowired
	ResponseReaderConverter responseReaderConverter;

	RestTemplate restTemplate = new RestTemplate();
	String url = "";
	APIResponse response = null;

	public APIResponse getResponse(SearchDTO search, String apiKey) {
		APIResponse parkingResponse = responseReaderConverter.getParking(apiKey, search);
		ArrayList<Object> parkingResponseArray = null;
		if (parkingResponse.getStatus() == 200) {
			parkingResponseArray = (ArrayList<Object>)parkingResponse.getResult();
		}

		APIResponse evChargingResponse = responseReaderConverter.getEvCharging(apiKey, search);
		ArrayList<Object> evChargingResponseArray = null;
		if (evChargingResponse.getStatus() == 200) {
			evChargingResponseArray = (ArrayList<Object>)evChargingResponse.getResult();
		}
		
		APIResponse restaurantResponse = responseReaderConverter.getRestaurant(apiKey, search);
		ArrayList<Object> restaurantResponseArray = null;
		if (restaurantResponse.getStatus() == 200) {
			restaurantResponseArray = (ArrayList<Object>)restaurantResponse.getResult();
		}

		ArrayList<Object> resultList = resultListBuilder(parkingResponseArray, evChargingResponseArray, restaurantResponseArray);

		response = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
				resultList);
		return response;
	}

	@Async
	public APIResponse getParking(String apiKey, SearchDTO search) {
		APIResponse internalResponse = null;
		HttpEntity<String> entity = new HttpEntity<String>(bodyCreator(search), headerCreator(apiKey));
		ResponseEntity<String> result = restTemplate.exchange(getVariable(GenericConstants.PARKING_URL),
				HttpMethod.POST, entity, String.class);

		JSONArray jsonArray = (JSONArray) JsonPath.read(result.getBody(), GenericConstants.RESULT_PATH);
		ArrayList<Object> placesList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); placesList.add(jsonArray.get(i++)))
			;

		internalResponse = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
				placesList);
		log.info(result.toString());
		return internalResponse;
	}

	@Async
	private APIResponse getEvCharging(String apiKey, SearchDTO search) {
		APIResponse internalResponse = null;
		HttpEntity<String> entity = new HttpEntity<String>(bodyCreator(search), headerCreator(apiKey));
		ResponseEntity<String> result = restTemplate.exchange(getVariable(GenericConstants.EV_CHARGING_URL),
				HttpMethod.POST, entity, String.class);

		JSONArray jsonArray = (JSONArray) JsonPath.read(result.getBody(), GenericConstants.RESULT_PATH);
		ArrayList<Object> placesList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); placesList.add(jsonArray.get(i++)))
			;

		internalResponse = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
				placesList);
		log.info(result.toString());
		return internalResponse;
	}

	@Async
	private APIResponse getRestaurant(String apiKey, SearchDTO search) {
		APIResponse internalResponse = null;
		HttpEntity<String> entity = new HttpEntity<String>(bodyCreator(search), headerCreator(apiKey));
		ResponseEntity<String> result = restTemplate.exchange(getVariable(GenericConstants.RESTAURANT_URL),
				HttpMethod.POST, entity, String.class);

		JSONArray jsonArray = (JSONArray) JsonPath.read(result.getBody(), GenericConstants.RESULT_PATH);
		ArrayList<Object> placesList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); placesList.add(jsonArray.get(i++)))
			;

		internalResponse = new APIResponse(MessageConstants.RESPONSE_OK_CODE, getMessage(MessageConstants.RESPONSE_OK),
				placesList);
		log.info(result.toString());
		return internalResponse;
	}
	
	private HttpHeaders headerCreator(String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	private String bodyCreator(SearchDTO search) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat", search.getLat());
		jsonObject.put("lon", search.getLon());
		return jsonObject.toString();
	}

	private ArrayList<Object> resultListBuilder(ArrayList<Object>... list) {
		ArrayList<Object> resultSet = new ArrayList<>();
		ArrayList<Object> resultObject = new ArrayList<>();
		int largestList = Math.max(list[0].size(), Math.max(list[1].size(), list[2].size()));
		for(int i=0; i<largestList; i++) {
			for(int j=0; j<list.length; j++) 
			resultObject.add(list[j].get(i));
		resultSet.add(resultObject);
		}
		return resultSet;
	}
}
