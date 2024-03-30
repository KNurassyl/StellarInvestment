package com.example.stellarinvestment.controller;

import com.example.stellarinvestment.file.FileUploadUtil;
import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.model.project.Project;
import com.example.stellarinvestment.model.project.ProjectNotFoundException;
import com.example.stellarinvestment.service.ProjectService;
import com.example.stellarinvestment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;


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

    @GetMapping(value = "/my/")
    public String allMyProjects(HttpServletRequest request, Model model) {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        model.addAttribute("myProjects", projectService.getProjectsCreatedByUser(authenticatedUser));
        return "My_Projects";
    }

    @GetMapping(value = "/all/team/")
    public String allProjectsTeam(HttpServletRequest request, Model model) {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        if (userService.hasRole(authenticatedUser, "User")) {
            model.addAttribute("teamProjects", projectService.getProjectsInTeamFormationStatus());
        }
        return "client_projects";
    }

    @GetMapping(value = "/details/{project_id}")
    public String detailOfProjectPage(HttpServletRequest request, Model model, @PathVariable(name = "project_id") Integer id) throws ProjectNotFoundException {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        model.addAttribute("project", projectService.get(id));
        return "Detail";
    }

    @PostMapping("/create_new")
    public String createNewProject(@RequestParam("tariffsList") String tariffsListJson, @RequestParam("personsList") String personsListJson,
                                   @RequestParam(name = "amountNeeded") float amountNeeded,
                                   @RequestParam(name = "shortDescription") String shortDescription,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "longDescription") String longDescription,
                                   @RequestParam(name = "aboutCreator") String aboutCreator,
                                   @RequestParam("finishTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date finishTime,
                                   @RequestParam(name = "userEmail") String userEmail,
                                   @RequestParam("fileImage") MultipartFile mainImageMultipart,
                                   @RequestParam("extraImage") MultipartFile[] extraImageMultiparts) throws IOException {

        Project project = projectService.createNewProject(tariffsListJson, personsListJson, amountNeeded, shortDescription, title, longDescription, aboutCreator, finishTime, userEmail);

        projectService.setMainImageName(mainImageMultipart, project);
        projectService.setExtraImageNames(extraImageMultiparts, project);
        projectService.saveUploadedImages(mainImageMultipart, extraImageMultiparts, project);

        projectService.saveProject(project);

        return "redirect:/project/my/";
    }
}
