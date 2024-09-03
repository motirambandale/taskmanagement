package com.example.taskmanagement.dto;

import java.util.Date;

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

    @Schema(description = "Description of the product ordered", example = "Task description")
    private String description;

    @Schema(description = "Status of the task", example = " Todo, In Progress, Done")
    private String status;
    
    @Schema(description = "priority of the task", example = "Low , Medium , High")
    private String priority;

    @Schema(description = "Due date of the task", example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date due_date;   
    
    
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updated_At;   
    
    
}
