package com.viniciusmassari.gestao_cursos_frontend.modules.course.dto;

import lombok.Data;

@Data
public class CreateCourseDTO {

    private String name;

    private String description;

    private String category;
}
