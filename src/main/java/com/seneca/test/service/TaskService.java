/**
 * 
 */
package com.seneca.test.service;

import java.util.List;
import java.util.Map;

/**
 * @author Manohar_Mandadi
 *
 */
public interface TaskService {

	public Map<String,Integer> distributeTasks(int totalTasks, List<String> vendorNames, List<Integer> percentages);
}
