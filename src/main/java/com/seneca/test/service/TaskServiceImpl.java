package com.seneca.test.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.seneca.test.controller.TaskController;


@Service
public class TaskServiceImpl implements TaskService {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Override
	public List<Integer> distributeTasks(int totalTasks, List<Integer> percentages) {
		List<Integer> taskDistribution = new ArrayList<Integer>();
		int vendorCount = percentages.size();
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
			taskDistribution.add(actualTaskCount);
		}
		int total=0;
		for(int key:taskDistribution){
			total+=key;
		}
		logger.info("Total"+total);
		return taskDistribution;
	}

}
