package com.viniciusmassari.gestao_cursos_frontend.modules.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.CreateCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.DeleteCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/create")
    public String create() {
        return "course/create";
    }

    @PostMapping("/create")
    public String create_course(CreateCourseDTO createCourseDTO, Model model) {
        try {
            courseService.create_course(createCourseDTO);
            model.addAttribute("created_message", "Curso criado com sucesso");
            return "/course/create";
        } catch (Exception e) {
            model.addAttribute("error_message", "Erro ao criar, tente novamente");
            return "/course/create";
        }
    }

    @GetMapping("/show")
    public String show_courses(Model model) {
        try {
            var courses = courseService.showAllCourses();
            model.addAttribute("courses", courses.getCourses());
            System.out.println(courses.getCourses());
            return "course/show";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error_message", "Não foi possível buscar os cursos");
            return "course/show";
        }
    }

    @PostMapping("/delete")
    public void delete_course(DeleteCourseDTO deleteCourseDTO) {
        System.out.println(deleteCourseDTO.getCourseID());
        System.out.println("DELETAR");
    }

}
