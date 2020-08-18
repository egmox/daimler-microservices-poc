package com.egmox.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
	private Integer status;
	private String message;
	private Object result;

	public APIResponse(int status, String message, Object result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
	}
}
