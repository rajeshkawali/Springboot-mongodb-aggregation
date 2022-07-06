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
import com.rajeshkawali.service.AggregationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Rajesh Kawali
 * 
 */

@RestController
@RequestMapping("/api/v1")
@Api(value = "Spring boot async API")
public class AggregationController {
	
	public static final String CLASS_NAME = AggregationController.class.getName();
	private static final Logger log = LoggerFactory.getLogger(CLASS_NAME);

	@Autowired
	private AggregationService mongoService;

	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStates2" })
	public List<States> fetchStates() {
		String _function = ".fetchStates";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.fetchStates2();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStatesWithSorted2" })
	public List<States> fetchStatesWithSorted2() {
		String _function = ".fetchStatesWithSorted";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.fetchStatesWithSorted2();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/fetchStateCapitalAndType2" })
	public List<Address> fetchStateCapitalAndType2() {
		String _function = ".fetchStateCapitalAndType";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<Address> address = null;
		try {
			address = mongoService.fetchStateCapitalAndType2();
			log.info(CLASS_NAME + _function + "::address: "+ address);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return address;
	}
	
	@ApiOperation(value = "Fetch Indian States")
	@GetMapping(value = { "/getAllStatesWithGroupSortedByCode" })
	public List<States> getAllStatesWithGroupSortedByCode() {
		String _function = ".getAllStatesWithGroupSortedByCode";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = null;
		try {
			states = mongoService.getAllStatesWithGroupSortedByCode();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		log.info(CLASS_NAME + _function + "::EXIT");
		return states;
	}
}
