package com.rajeshkawali.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Data;

/**
 * @author Rajesh_Kawali
 * 
 */
@Data
@JsonPOJOBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class District {

	@JsonProperty("districtId")
	private String districtId;

	@JsonProperty("name")
	private String name;

}