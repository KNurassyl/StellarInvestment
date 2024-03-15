package com.example.stellarinvestment.controller;

import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String projectCreateStartPage(HttpServletRequest request, Model model) {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        return "Create_page";
    }

    @GetMapping(value = "/create/new/")
    public String projectCreateNewPage(HttpServletRequest request, Model model) {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        return "Create_page_start";
    }
}
