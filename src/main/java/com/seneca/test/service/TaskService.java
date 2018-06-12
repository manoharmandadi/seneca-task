/**
 * 
 */
package com.seneca.test.service;

import java.util.List;

/**
 * @author Manohar_Mandadi
 *
 */
public interface TaskService {

	public List<Integer> distributeTasks(int totalTasks, List<Integer> percentages);
}
