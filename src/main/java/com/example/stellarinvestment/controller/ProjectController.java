package com.example.stellarinvestment.controller;

import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.model.project.Project;
import com.example.stellarinvestment.model.project.Tariff;
import com.example.stellarinvestment.model.project.Team;
import com.example.stellarinvestment.service.ProjectService;
import com.example.stellarinvestment.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

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

    @PostMapping("/create_new")
    public String createNewProject(@RequestParam("tariffsList") String tariffsListJson, @RequestParam("personsList") String personsListJson,
                                   @RequestParam(name = "amountNeeded") float amountNeeded,
                                   @RequestParam(name = "shortDescription") String shortDescription,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "longDescription") String longDescription,
                                   @RequestParam(name = "aboutCreator") String aboutCreator,
                                   @RequestParam("finishTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date finishTime,
                                   @RequestParam(name = "userEmail") String userEmail) throws JsonProcessingException {
        projectService.createNewProject(tariffsListJson, personsListJson, amountNeeded, shortDescription, title, longDescription, aboutCreator, finishTime, userEmail);

        return "redirect:/project/create/new/";
    }
}
