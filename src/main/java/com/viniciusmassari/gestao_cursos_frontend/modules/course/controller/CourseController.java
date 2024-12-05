package com.viniciusmassari.gestao_cursos_frontend.modules.course.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.CreateCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.DeleteCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/course")
@Slf4j
public class CourseController {

    // private Logger logger = LoggerFactory.getLogger(getClass());

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
            return "course/show";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error_message", "Não foi possível buscar os cursos");
            return "course/show";
        }
    }

    @PostMapping("/delete")
    public String delete_course(DeleteCourseDTO deleteCourseDTO, RedirectAttributes redirectAttributes) {
        try {
            this.courseService.delete_course(deleteCourseDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Curso apagado com sucesso");
            return "redirect:/instructor/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível deletar o curso ");
            return "redirect:/instructor/profile";
        }

    }

}
