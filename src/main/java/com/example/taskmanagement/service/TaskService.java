package com.example.taskmanagement.service;

import java.util.List;

import com.example.taskmanagement.dto.TaskDTO;

public interface TaskService {
	
	List<TaskDTO> getAllTasks(int page, int size);
	TaskDTO getTaskById(Long id);
    List<TaskDTO> searchTaskByTitleOrDescription(String searchTerm);
	TaskDTO createTask(TaskDTO taskDTO);
	TaskDTO updateTask(Long id,TaskDTO taskDTO);	
    boolean deleteTask(Long id);
}
