package com.rajeshkawali.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "StatesDistricts") //india.StatesDistricts
public class States {

	@JsonProperty("stateId")
	private String stateId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("capital")
	private String capital;

	@JsonProperty("population")
	private Integer population;
	
	@JsonProperty("area")
	private Integer area;
	
	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;

	@JsonProperty("districts")
	private List<District> districts;

}
