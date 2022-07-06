package com.rajeshkawali.service;

import java.util.List;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;

public interface AggregationService {

	public List<States> fetchStates2();

	public List<States> fetchStatesWithSorted2();
	
	public List<Address> fetchStateCapitalAndType2();
	
	public List<States> getAllStatesWithGroupSortedByCode();
}
