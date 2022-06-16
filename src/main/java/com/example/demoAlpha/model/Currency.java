package com.example.demoAlpha.model;

import lombok.Data;

@Data
public class Currency {
	private String code;
	private String description;

	public Currency(String code, String description) {
		this.code = code;
		this.description = description;
	}
}
