package com.example.taskmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.taskmanagement.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
    Page<Task> findAll(Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
     List<Task> searchTaskByTitleOrDescription(@Param("searchTerm") String searchTerm);
}
