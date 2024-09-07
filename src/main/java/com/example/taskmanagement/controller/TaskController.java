package com.example.taskmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private TaskService taskService;

	public TaskController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}

	@PostMapping
	@Operation(summary = "Create a new Task", description = "Create a new task in the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "task created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
		if (taskDTO == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			TaskDTO createdTask = taskService.createTask(taskDTO);
			return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update the task by ID", description = "Update an task by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Task retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	public ResponseEntity<TaskDTO> getTaskById(@Parameter @PathVariable Long id, @RequestBody TaskDTO taskDTO) {
		TaskDTO updatedTaskDTO = taskService.updateTask(id, taskDTO);
		if (updatedTaskDTO != null) {
			return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/search/{searchTerm}")
	@Operation(summary = "Search the task by Title or Description", description = "Retrieve an task by its Title or Description.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Task not found") })
	public ResponseEntity<List<TaskDTO>> searchTaskByTitleOrDescription(@Parameter @PathVariable String searchTerm) {
		List<TaskDTO> taskDTO = taskService.searchTaskByTitleOrDescription(searchTerm);
		if (taskDTO != null) {
			return new ResponseEntity<>(taskDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	@Operation(summary = "Get all tasks", description = "Retrieve a list of all tasks.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of tasks retrieved"),
			@ApiResponse(responseCode = "204", description = "No tasks found") })
	public ResponseEntity<List<TaskDTO>> getAllTasks(@Parameter @RequestParam(defaultValue = "0") int page,
			@Parameter @RequestParam(defaultValue = "10") int size) {
		List<TaskDTO> tasks = taskService.getAllTasks(page, size);
		if (tasks.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete the task", description = "Delete an existing task by ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Task not found") })
	public ResponseEntity<Void> deleteTask(@Parameter @PathVariable Long id) {
		boolean isDeleted = taskService.deleteTask(id);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
