package com.rajeshkawali.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;
import com.rajeshkawali.repository.MongoRepository;

@Service
public class MongoServiceImpl implements MongoService {

	@Autowired
	private MongoRepository mongoRepository;
	
	@Override
	public List<States>  fetchStates() {
		return mongoRepository.fetchStates();
	}

	@Override
	public List<States> fetchStatesWithSorted() {
		return mongoRepository.fetchStatesWithSorted();
	}

	@Override
	public List<Address> fetchStateCapitalAndType() {
		return mongoRepository.fetchStateCapitalAndType();
	}

	@Override
	public List<States> getAllStatesDistCountIs10SortedByCode() {
		return mongoRepository.getAllStatesDistCountIs10SortedByCode();
	}

}
