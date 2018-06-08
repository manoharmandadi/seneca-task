package com.seneca.test.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seneca.test.exceptions.ValidationException;

@RestController
@RequestMapping("/seneca")
public class TaskController {

	@GetMapping(path="/taskDistribution", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, Integer>> distributeTasks(@RequestParam(name="totalTasks") int totalTask,
			@RequestParam(name="vendors")List<String> vendorNames,
			@RequestParam(name="percentages")List<Integer> vendorTaskShare
			){
		String[] arr = {"10","20","20"};
		int totalPercentage = 0;
		for(int taskShare : vendorTaskShare){
			totalPercentage+=taskShare;
		}
		if(totalPercentage!=100){
			throw new ValidationException(100, "Total Percentages not equal to 100");
		}
		
		Map<String, Integer> response = distributeTask(totalTask, vendorNames,vendorTaskShare);
		return new ResponseEntity<Map<String, Integer>>(response, HttpStatus.OK);
	}
	
	
	public Map<String, Integer> distributeTask(int totalTasks, List<String> vendorNames, List<Integer> percentages){
		Map<String, Integer> taskDistributionMap = new HashMap<String,Integer>();
		Boolean positiveDelta = null;
		int vendorCount = vendorNames.size();
		float delta = 0;
		for(int i=0; i< vendorCount;i++){
			int vendorShare = percentages.get(i);
			double taskCount = (float)(totalTasks * vendorShare) / 100;
			if(i==(vendorCount-1)){
				taskCount += delta;
			}
			int actualTaskCount = (int)Math.round(taskCount);
			delta += (taskCount - actualTaskCount); 
/*			if(positiveDelta == null){
				actualTaskCount = Math.floorDiv(totalTasks *  vendorShare, 100);
				delta += (taskCount - actualTaskCount); 
			} else if (positiveDelta == Boolean.TRUE){
				
				delta += (taskCount - actualTaskCount); 

			}
*/
			taskDistributionMap.put(vendorNames.get(i), actualTaskCount);
		}
		return taskDistributionMap;
	}
}
