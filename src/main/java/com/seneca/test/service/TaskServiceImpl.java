package com.seneca.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.seneca.test.controller.TaskController;


@Service
public class TaskServiceImpl implements TaskService {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Override
	public Map<String, Integer> distributeTasks(int totalTasks, List<String> vendorNames, List<Integer> percentages) {
		Map<String, Integer> taskDistributionMap = new HashMap<String,Integer>();
		int vendorCount = vendorNames.size();
		double delta = 0;
		for(int i=0; i< vendorCount;i++){
			int vendorShare = percentages.get(i);
			double taskCount = (double)(totalTasks * vendorShare) / 100;
			int actualTaskCount = (int)Math.round(taskCount);
			delta += (taskCount - actualTaskCount);
			logger.info("Delta value "+delta+".Task Count:"+taskCount+". Actual Task Count:"+actualTaskCount);
			if(delta > 1){
				actualTaskCount = actualTaskCount + 1;
				delta -= 1;
			} else if (delta <-1){
				actualTaskCount = actualTaskCount - 1;
				delta += 1;
			}
			if(i==(vendorCount-1)){
				logger.info("Last Vendor.Delta value "+delta+". Actual Task Count:"+actualTaskCount);
				actualTaskCount = (int)Math.round(actualTaskCount + delta);
			}
			taskDistributionMap.put(vendorNames.get(i), actualTaskCount);
		}
		int total=0;
		for(String key:taskDistributionMap.keySet()){
			total+=taskDistributionMap.get(key);
		}
		logger.info("Total"+total);
		return taskDistributionMap;
	}

}
