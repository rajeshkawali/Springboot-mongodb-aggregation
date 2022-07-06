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
public class Address {

	@JsonProperty("stateName")
	private String stateName;
	
	@JsonProperty("stateOrUT")
	private String stateOrUT;
	
	@JsonProperty("districtsName")
	private String districtsName;
	
	@JsonProperty("capitalName")
	private String capitalName;
}
