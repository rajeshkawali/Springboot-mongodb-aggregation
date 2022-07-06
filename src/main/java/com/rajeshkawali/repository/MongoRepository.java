package com.rajeshkawali.repository;

import java.util.List;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;

public interface MongoRepository {

	public List<States> fetchStates();

	public List<States> fetchStatesWithSorted();

	public List<Address> fetchStateCapitalAndType();

	public List<States> getAllStatesDistCountIs10SortedByCode();

}
