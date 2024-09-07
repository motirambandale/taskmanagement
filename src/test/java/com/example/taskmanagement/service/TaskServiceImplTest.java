package com.example.taskmanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.TaskRepository;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TaskDTO createTaskDTO(String title, String description, String priority, String status, LocalDateTime dueDate) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(title);
        taskDTO.setDescription(description);
        taskDTO.setPriority(priority);
        taskDTO.setStatus(status);
        taskDTO.setDueDate(dueDate);
        return taskDTO;
    }

    private Task createTask(Long id, String title, String description, String priority, String status, LocalDateTime dueDate, User user) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(status);
        task.setDueDate(dueDate);
        task.setUser(user);
        return task;
    }

    @Test
    void testCreateTask() {
        TaskDTO taskDTO = createTaskDTO("Task 1", "Description 1", "High", "New", LocalDateTime.now());
        User user = new User();
        user.setUsername("testuser");

        Task task = createTask(1L, taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getPriority(), taskDTO.getStatus(), taskDTO.getDueDate(), user);

        when(userService.getLoggedInUser()).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO createdTaskDTO = taskService.createTask(taskDTO);

        assertNotNull(createdTaskDTO);
        assertEquals(taskDTO.getTitle(), createdTaskDTO.getTitle());
        assertEquals(taskDTO.getDescription(), createdTaskDTO.getDescription());
        assertEquals(taskDTO.getPriority(), createdTaskDTO.getPriority());
        assertEquals(taskDTO.getStatus(), createdTaskDTO.getStatus());
        assertEquals(taskDTO.getDueDate(), createdTaskDTO.getDueDate());
        verify(taskRepository).save(any(Task.class));
        verify(userService).getLoggedInUser();
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        TaskDTO taskDTO = createTaskDTO("Updated Task", "Updated Description", "Medium", "In Progress", LocalDateTime.now());

        Task existingTask = createTask(taskId, "Old Task", "Old Description", "Low", "New", LocalDateTime.now().minusDays(1), null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDTO updatedTaskDTO = taskService.updateTask(taskId, taskDTO);

        assertNotNull(updatedTaskDTO);
        assertEquals(taskDTO.getTitle(), updatedTaskDTO.getTitle());
        assertEquals(taskDTO.getDescription(), updatedTaskDTO.getDescription());
        assertEquals(taskDTO.getPriority(), updatedTaskDTO.getPriority());
        assertEquals(taskDTO.getStatus(), updatedTaskDTO.getStatus());
        assertEquals(taskDTO.getDueDate(), updatedTaskDTO.getDueDate());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testGetTaskById_Success() {
        Long taskId = 1L;
        Task task = createTask(taskId, "Test Task", "Test Description", "High", "New", LocalDateTime.now(), null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskDTO taskDTO = taskService.getTaskById(taskId);

        assertNotNull(taskDTO);
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getPriority(), taskDTO.getPriority());
        assertEquals(task.getStatus(), taskDTO.getStatus());
        assertEquals(task.getDueDate(), taskDTO.getDueDate());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetTaskById_NotFound() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskDTO taskDTO = taskService.getTaskById(taskId);

        assertNull(taskDTO);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = createTask(1L, "Task 1", "Description 1", "Low", "New", LocalDateTime.now(), null);
        Task task2 = createTask(2L, "Task 2", "Description 2", "High", "In Progress", LocalDateTime.now(), null);

        List<Task> taskList = Arrays.asList(task1, task2);
        Page<Task> taskPage = new PageImpl<>(taskList);

        when(taskRepository.findAll(any(Pageable.class))).thenReturn(taskPage);

        List<TaskDTO> tasks = taskService.getAllTasks(0, 10);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals(taskList.get(0).getTitle(), tasks.get(0).getTitle());
        assertEquals(taskList.get(1).getTitle(), tasks.get(1).getTitle());
        verify(taskRepository).findAll(any(Pageable.class));
    }

    @Test
    void testDeleteTask_Success() {
        Long taskId = 1L;
        Task task = createTask(taskId, "Task to delete", "Description", "Medium", "New", LocalDateTime.now(), null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        boolean isDeleted = taskService.deleteTask(taskId);

        assertTrue(isDeleted);
        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(task);
    }

    @Test
    void testDeleteTask_NotFound() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        boolean isDeleted = taskService.deleteTask(taskId);

        assertFalse(isDeleted);
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void testSearchTaskByTitleOrDescription() {
        String searchTerm = "Task";

        Task task1 = createTask(1L, "Task 1", "Description 1", "Low", "New", LocalDateTime.now(), null);
        Task task2 = createTask(2L, "Task 2", "Description 2", "High", "In Progress", LocalDateTime.now(), null);

        List<Task> taskList = Arrays.asList(task1, task2);

        when(taskRepository.searchTaskByTitleOrDescription(searchTerm)).thenReturn(taskList);

        List<TaskDTO> tasks = taskService.searchTaskByTitleOrDescription(searchTerm);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals(taskList.get(0).getTitle(), tasks.get(0).getTitle());
        assertEquals(taskList.get(1).getTitle(), tasks.get(1).getTitle());
        verify(taskRepository).searchTaskByTitleOrDescription(searchTerm);
    }
}
