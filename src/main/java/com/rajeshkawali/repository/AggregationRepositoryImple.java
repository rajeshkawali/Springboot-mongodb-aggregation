package com.rajeshkawali.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

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
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.rajeshkawali.model.Address;
import com.rajeshkawali.model.States;

@Repository
public class AggregationRepositoryImple implements AggregationRepository {
	
	public static final String CLASS_NAME = MongoRepositoryImple.class.getName();
	private static final Logger log = LoggerFactory.getLogger(CLASS_NAME);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<States> fetchStates2() {
		String _function = ".fetchStates2";
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
	public List<States> fetchStatesWithSorted2() {
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
	public List<Address> fetchStateCapitalAndType2() {
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
	public List<States> getAllStatesWithGroupSortedByCode() {
		String _function = ".getAllStatesWithGroupSortedByCode";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = new ArrayList<States>();
		try {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("type").exists(true),
					Criteria.where("name").exists(true));
			MatchOperation matchOperation = match(criteria);
			GroupOperation groupOperation = group("type").sum("population").as("population").addToSet("$type").as("type");
			SortOperation sortOperation = sort(Sort.by(Direction.DESC, "type"));
			//ConditionalOperators.Cond cond = ConditionalOperators.Cond.when(ComparisonOperators.Eq.valueOf("$type").equalToValue("Union Territory")).then(1).otherwise(0);
			//GroupOperation groupOperation2 = Aggregation.group("type").sum(cond).as("code");
			Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
			AggregationResults<States> result = mongoTemplate.aggregate(aggregation, "StatesDistricts", States.class);
			states = result.getMappedResults();
			
			
			List<AggregationOperation> operationList = new ArrayList<>();
			List<ConditionalOperators.Switch.CaseOperator> cases = new ArrayList<>();
			ConditionalOperators.Switch.CaseOperator cond1 = ConditionalOperators.Switch.CaseOperator
					.when(BooleanOperators.And.and(ComparisonOperators.valueOf("dose").greaterThanValue(0),
							ComparisonOperators.valueOf("dose").lessThanValue(50)))
					.then("0-50");
			
			ConditionalOperators.Switch.CaseOperator cond2 = ConditionalOperators.Switch.CaseOperator
					.when(BooleanOperators.And.and(ComparisonOperators.valueOf("dose").greaterThanValue(0),
							ComparisonOperators.valueOf("dose").lessThanValue(50)))
					.then("0-50");
			
			cases.add(cond1);
			cases.add(cond2);

			ProjectionOperation projectionOperation = Aggregation.project().and(ConditionalOperators.switchCases(cases)).as("range");
			operationList.add(projectionOperation);
			Aggregation aggregation2 = newAggregation(operationList);
			System.out.println(aggregation2.toString());
			
			
			log.info(CLASS_NAME + _function + "::states: " + states);
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return states;
	}
	
	
	public List<States> getSwitchCase() {
		String _function = ".getSwitchCase";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<States> states = new ArrayList<States>();
		try {
			List<AggregationOperation> operationList = new ArrayList<>();
			List<ConditionalOperators.Switch.CaseOperator> cases = new ArrayList<>();
			ConditionalOperators.Switch.CaseOperator cond1 = ConditionalOperators.Switch.CaseOperator
					.when(BooleanOperators.And.and(ComparisonOperators.valueOf("dose").greaterThanValue(0),
							ComparisonOperators.valueOf("dose").lessThanValue(50)))
					.then("0-50");

			ConditionalOperators.Switch.CaseOperator cond2 = ConditionalOperators.Switch.CaseOperator
					.when(BooleanOperators.And.and(ComparisonOperators.valueOf("dose").greaterThanValue(0),
							ComparisonOperators.valueOf("dose").lessThanValue(50)))
					.then("0-50");

			cases.add(cond1);
			cases.add(cond2);

			ProjectionOperation projectionOperation = Aggregation.project().and(ConditionalOperators.switchCases(cases))
					.as("range");
			operationList.add(projectionOperation);
			Aggregation aggregation = newAggregation(operationList);
			System.out.println(aggregation.toString());

			log.info(CLASS_NAME + _function + "::aggregation: " + aggregation.toString());
		} catch (Exception e) {
			log.error(CLASS_NAME + _function + "::Exception occurred while fetching user details: " + e.toString());
		}
		return states;
	}
}
