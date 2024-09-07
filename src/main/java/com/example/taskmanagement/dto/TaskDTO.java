package com.example.taskmanagement.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Task")
public class TaskDTO {

    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;
    
    @Schema(description = "Title of the task", example = "New Task")
    private String title;

    @Schema(description = "Description of the task", example = "Task description")
    private String description;

    @Schema(description = "Status of the task", example = "Todo, In Progress, Done")
    private String status;
    
    @Schema(description = "Priority of the task", example = "Low, Medium, High")
    private String priority;

    @Schema(description = "Due date of the task", example = "2023-12-31T23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;   
    
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;   
}
