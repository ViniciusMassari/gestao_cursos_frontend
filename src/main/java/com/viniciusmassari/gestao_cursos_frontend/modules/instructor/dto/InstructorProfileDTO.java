package com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto;

import java.util.List;

import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.CourseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorProfileDTO {
    private String name;
    private String email;
    private List<CourseDTO> courses;
}