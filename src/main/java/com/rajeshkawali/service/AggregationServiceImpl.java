package com.rajeshkawali.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;
import com.rajeshkawali.repository.AggregationRepository;

@Service
public class AggregationServiceImpl implements AggregationService {

	@Autowired
	private AggregationRepository mongoRepository;
	
	@Override
	public List<States>  fetchStates2() {
		return mongoRepository.fetchStates2();
	}

	@Override
	public List<States> fetchStatesWithSorted2() {
		return mongoRepository.fetchStatesWithSorted2();
	}

	@Override
	public List<Address> fetchStateCapitalAndType2() {
		return mongoRepository.fetchStateCapitalAndType2();
	}

	@Override
	public List<States> getAllStatesWithGroupSortedByCode() {
		return mongoRepository.getAllStatesWithGroupSortedByCode();
	}

}
