package com.seneca.test.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seneca.test.exceptions.ValidationException;
import com.seneca.test.service.TaskService;

@RestController
@RequestMapping("/seneca")
public class TaskController {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping(path="/taskDistribution", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Integer>> distributeTasks(@RequestParam(name="totalTasks") int totalTask,
			@RequestParam(name="vendors") int vendorCount,
			@RequestParam(name="percentages")List<Integer> vendorTaskShare
			){
		if(vendorCount!= vendorTaskShare.size()){
			logger.debug("Vendor and Percentages count doesnt match.");
			throw new ValidationException(101, "Total Number of Vendors and Percentages should be equal");
		}
		int totalPercentage = 0;
		for(int taskShare : vendorTaskShare){
			totalPercentage+=taskShare;
		}
		if(totalPercentage!=100){
			logger.debug("Total Percentages is not equal to 100.");
			throw new ValidationException(100, "Total Percentages not equal to 100");
		}
		List<Integer> response = taskService.distributeTasks(totalTask,vendorTaskShare);
		return new ResponseEntity<List<Integer>>(response, HttpStatus.OK);
	}
	
}
