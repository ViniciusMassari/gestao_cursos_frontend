package com.viniciusmassari.gestao_cursos_frontend.modules.course.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShowAllCourseResponseDTO {
    List<CourseDTO> courses;
}
