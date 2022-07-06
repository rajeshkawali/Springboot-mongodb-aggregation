package com.rajeshkawali.repository;

import java.util.List;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;

public interface AggregationRepository {

	public List<States> fetchStates2();

	public List<States> fetchStatesWithSorted2();

	public List<Address> fetchStateCapitalAndType2();

	public List<States> getAllStatesWithGroupSortedByCode();

}
