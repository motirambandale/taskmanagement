package com.example.taskmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.TaskRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepository;

	private UserService userService;

	public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
		super();
		this.taskRepository = taskRepository;
		this.userService = userService;
	}

	public TaskDTO createTask(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(taskDTO.getStatus());
		task.setPriority(taskDTO.getPriority());
		task.setDue_date(taskDTO.getDue_date());
		User user = userService.getLoggedInUser();
		task.setUser(user);
		Task savedTask = taskRepository.save(task);
		return convertToDTO(savedTask);
	}

	@Override
	public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
		Task task = taskRepository.findById(id).orElse(null);
		task.setId(id);
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(taskDTO.getStatus());
		task.setPriority(taskDTO.getPriority());
		task.setDue_date(taskDTO.getDue_date());
		task.setUpdatedAt(LocalDateTime.now());
		Task savedTask = taskRepository.save(task);
		return convertToDTO(savedTask);
	}

	@Override
	public TaskDTO getTaskById(Long id) {
		Optional<Task> task = taskRepository.findById(id);
		return task.map(this::convertToDTO).orElse(null);
	}

	@Override
	public List<TaskDTO> getAllTasks(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Task> taskPage = taskRepository.findAll(pageable);
		return taskPage.getContent().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public boolean deleteTask(Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			taskRepository.delete(task.get());
			return true;
		}
		return false;
	}

	@Override
	public List<TaskDTO> searchTaskByTitleOrDescription(String searchTerm) {
		List<Task> tasks = taskRepository.searchTaskByTitleOrDescription(searchTerm);
		return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private TaskDTO convertToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(task.getId());
		taskDTO.setTitle(task.getTitle());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setStatus(task.getStatus());
		taskDTO.setPriority(task.getPriority());
		taskDTO.setDue_date(task.getDue_date());
		return taskDTO;
	}
}
