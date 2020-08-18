package com.egmox.poc.service;

import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.egmox.poc.dto.APIResponse;
import com.egmox.poc.dto.SearchDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResponseReaderConverter {

	RestTemplate restTemplate = new RestTemplate();

	String url = "http://localhost:8082/nearby/parking/find";

	public APIResponse getResponse(SearchDTO search, String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat", "");
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		log.info(result.toString());
		return null;
	}
}
