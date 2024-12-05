package com.viniciusmassari.gestao_cursos_frontend.modules.instructor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto.*;
import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.service.InstructorService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/instructor")
@Slf4j
public class InstructorController {

    Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/login")
    public String login() {
        return "instructor/login";
    }

    @GetMapping("/create")
    public String create() {
        return "instructor/create";
    }

    @GetMapping("/profile")
    public String profile(Model model, RedirectAttributes redirectAttributes) {
        try {
            var instructor = this.instructorService.get_instructor_profile(this.instructorService.getToken());
            model.addAttribute("instructor", instructor);
            return "instructor/profile";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error_message", "Erro ao fazer login");
            return "redirect:instructor/login";
        }

    }

    @PostMapping("/create")
    public String create_instructor(RedirectAttributes redirectAttributes, CreateInstructorDTO createInstructorDTO) {
        try {
            this.instructorService.createInstructor(createInstructorDTO);
            return "redirect:/instructor/login";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                redirectAttributes.addFlashAttribute("error_message", "Usuário já existe");
            } else {
                redirectAttributes.addFlashAttribute("error_message", "Erro interno, tente novamente mais tarde");

            }

            return "redirect:/instructor/create";
        }
    }

    @PostMapping("/logout")
    public String instructor_logout(RedirectAttributes redirectAttributes, HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        session.setAttribute("token", null);

        return "redirect:/instructor/login";
    }

    @PostMapping("/login")
    public String instructor_login(RedirectAttributes redirectAttributes, HttpSession session,
            AuthInstructorDTO authInstructorDTO) {
        try {
            AuthInstructorResponseDTO token = this.instructorService.loginInstructor(authInstructorDTO);
            var grants = token.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .toList();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);
            auth.setDetails(token.getToken());
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();

            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token.getToken());
            return "redirect:/instructor/profile";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error_message", "Usuário ou senha incorretos");
            return "redirect:/instructor/login";
        }
    }
}
