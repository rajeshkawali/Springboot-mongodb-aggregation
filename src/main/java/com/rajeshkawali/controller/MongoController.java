package com.rajeshkawali.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;
import com.rajeshkawali.service.MongoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Rajesh Kawali
 * 
 */

@RestController
@RequestMapping("/api/v1")
@Api(value = "Spring boot async API")
public class MongoController {
	
	public static final String CLASS_NAME = MongoController.class.getName();
	private static final Logger log = LoggerFactory.getLogger(CLASS_NAME);

	@Autowired
	private MongoService mongoService;

	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStates" })
	public List<States> fetchStates() {
		String _function = ".fetchStates";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.fetchStates();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStatesWithSorted" })
	public List<States> fetchStatesWithSorted() {
		String _function = ".fetchStatesWithSorted";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.fetchStatesWithSorted();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStateCapitalAndType" })
	public List<Address> fetchStateCapitalAndType() {
		String _function = ".fetchStateCapitalAndType";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<Address> address = null;
		try {
			address = mongoService.fetchStateCapitalAndType();
			log.info(CLASS_NAME + _function + "::address: "+ address);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return address;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/getAllStatesDistCountIs10SortedByCode" })
	public List<States> getAllStatesDistCountIs10SortedByCode() {
		String _function = ".getAllStatesDistCountIs10SortedByCode";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.getAllStatesDistCountIs10SortedByCode();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
}
