package com.example.taskmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.service.TaskService;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask_Success() {
        TaskDTO taskDTO = new TaskDTO(); // Fill this with valid task data
        TaskDTO createdTask = new TaskDTO(); // Simulate the created task

        when(taskService.createTask(taskDTO)).thenReturn(createdTask);

        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
        verify(taskService).createTask(taskDTO);
    }

    @Test
    void testCreateTask_BadRequest() {
        ResponseEntity<TaskDTO> response = taskController.createTask(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateTask_Success() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO(); // Fill with valid task data
        TaskDTO updatedTask = new TaskDTO(); // Simulate the updated task

        when(taskService.updateTask(taskId, taskDTO)).thenReturn(updatedTask);

        ResponseEntity<TaskDTO> response = taskController.getTaskById(taskId, taskDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
        verify(taskService).updateTask(taskId, taskDTO);
    }

    @Test
    void testUpdateTask_BadRequest() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();

        when(taskService.updateTask(taskId, taskDTO)).thenReturn(null);

        ResponseEntity<TaskDTO> response = taskController.getTaskById(taskId, taskDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(taskService).updateTask(taskId, taskDTO);
    }

    @Test
    void testSearchTaskByTitleOrDescription_Success() {
        String searchTerm = "Test";
        List<TaskDTO> taskList = Arrays.asList(new TaskDTO(), new TaskDTO());

        when(taskService.searchTaskByTitleOrDescription(searchTerm)).thenReturn(taskList);

        ResponseEntity<List<TaskDTO>> response = taskController.searchTaskByTitleOrDescription(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskList, response.getBody());
        verify(taskService).searchTaskByTitleOrDescription(searchTerm);
    }

    @Test
    void testSearchTaskByTitleOrDescription_NotFound() {
        String searchTerm = "NonExistent";

        when(taskService.searchTaskByTitleOrDescription(searchTerm)).thenReturn(null);

        ResponseEntity<List<TaskDTO>> response = taskController.searchTaskByTitleOrDescription(searchTerm);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService).searchTaskByTitleOrDescription(searchTerm);
    }

    @Test
    void testGetAllTasks_Success() {
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(), new TaskDTO());

        when(taskService.getAllTasks(0, 10)).thenReturn(tasks);

        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService).getAllTasks(0, 10);
    }

    @Test
    void testGetAllTasks_NoContent() {
        when(taskService.getAllTasks(0, 10)).thenReturn(Arrays.asList());

        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks(0, 10);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService).getAllTasks(0, 10);
    }

    @Test
    void testDeleteTask_Success() {
        Long taskId = 1L;

        when(taskService.deleteTask(taskId)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(taskService).deleteTask(taskId);
    }

    @Test
    void testDeleteTask_NotFound() {
        Long taskId = 1L;

        when(taskService.deleteTask(taskId)).thenReturn(false);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService).deleteTask(taskId);
    }
}
