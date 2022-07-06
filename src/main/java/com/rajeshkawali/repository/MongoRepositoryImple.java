package com.rajeshkawali.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;

@Repository
public class MongoRepositoryImple implements MongoRepository {
	
	public static final String CLASS_NAME = MongoRepositoryImple.class.getName();
	private static final Logger log = LoggerFactory.getLogger(CLASS_NAME);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<States> fetchStates() {
		String _function = ".fetchStates";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = new ArrayList<States>();
		try {
			Query query = new Query();
			query.fields().include("name").exclude("_id").include("id");
			states = mongoTemplate.find(query, States.class);
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return states;
	}
	
	@Override
	public List<States> fetchStatesWithSorted() {
		String _function = ".fetchStatesWithSorted";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = new ArrayList<States>();
		try {
			MatchOperation matchStage = Aggregation.match(new Criteria("code").in(Arrays.asList("KA","DL","AP","MH")));
			SortOperation sortByName = sort(Sort.by(Direction.ASC, "name"));
			ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("id")
					.andExpression("stateId").as("stateId")
					.andExpression("name").as("name")
					.andExpression("capital").as("capital")
					.andExpression("code").as("code")
					.andExpression("type").as("type")
					.andExpression("population").as("population")
					.andExpression("area").as("area")
					.andExpression("districts.districtId").as("districts.districtId")
					.andExpression("districts.name").as("districts.name");
			Aggregation aggregation = Aggregation.newAggregation(matchStage,sortByName, projectStage);
			AggregationResults<States> output = mongoTemplate.aggregate(aggregation, "StatesDistricts", States.class);
			states = output.getMappedResults();
			log.info(CLASS_NAME + _function + "::states: "+ states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return states;
	}

	@Override
	public List<Address> fetchStateCapitalAndType() {
		String _function = ".fetchStateCapitalAndType";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<Address> address = new ArrayList<Address>();
		try {
			MatchOperation matchStage = Aggregation.match(new Criteria("code").is("MH"));
			//LimitOperation limitToOnlyFirstDoc = Aggregation.limit(1);
			ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("_id")
					.andExpression("name").as("stateName")
					.andExpression("capital").as("capitalName")
					.andExpression("type").as("stateOrUT")
					//.and("districts.name").arrayElementAt(0).as("districts.name")
					//.and(ArrayOperators.ArrayElemAt.arrayOf("districts.name").elementAt(0)).as("districtsName")
					.and(ArrayOperators.Filter.filter("districts.name").as("name").by(Eq.valueOf("name").equalToValue("Pune"))).as("districtsName");
			Aggregation aggregation = Aggregation.newAggregation(matchStage, projectStage);
			AggregationResults<Address> output = mongoTemplate.aggregate(aggregation, "StatesDistricts", Address.class);
			log.info(CLASS_NAME + _function + "::states: "+ output.getUniqueMappedResult());
			address = output.getMappedResults();
			log.info(CLASS_NAME + _function + "::states: "+ address);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return address;
	}
	
	@Override
	public List<States> getAllStatesDistCountIs10SortedByCode() {
		String _function = ".getAllStatesDistCountIs10SortedByCode";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = new ArrayList<States>();
		try {
			//GroupOperation groupByAreaAndSumPopulation = group("type").sum("population").as("population");
			//LimitOperation limitOperation = Aggregation.limit(5);
			//GroupOperation groupOperation = group(SWOConstant.SALES_ORDER_NUMBER).addToSet("$transactionContract.metaData.eventType").as("eventType").push("$$ROOT").as(SWOConstant.SNP_WORK_ORDERS);
			//GroupOperation groupOperation = group(SWOConstant.SALES_ORDER_NUMBER).push("$$ROOT").as(SWOConstant.SNP_WORK_ORDERS);
			//AggregationExpression conditionExpression = ConditionalOperators.ifNull(SWOConstant.CANCEL_REQUEST).then(SWOConstant.N);
			//GroupOperation groupOperation = group(SWOConstant.SALES_ORDER_NUMBER).addToSet(SWOConstant.METADATA_EVENT_TYPE).as(SWOConstant.POJO_EVENT_TYPE);
			MatchOperation filterStates = match(new Criteria("code").in(Arrays.asList("KA","DL","AP","MH")));
			SortOperation sortByStateId = sort(Sort.by(Direction.DESC, "name"));
			Aggregation aggregation = newAggregation(filterStates, sortByStateId);
			AggregationResults<States> result = mongoTemplate.aggregate(aggregation, "StatesDistricts", States.class);
			states = result.getMappedResults();
			log.info(CLASS_NAME + _function + "::states: " + states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return states;
	}
}
