package com.viniciusmassari.gestao_cursos_frontend.modules.course.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CourseDTO {

    private UUID id;

    private String name;

    private String description;

    private String category;

    private String instructorName;

    private CourseActive Active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
