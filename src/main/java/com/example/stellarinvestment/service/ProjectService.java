package com.example.stellarinvestment.service;

import com.example.stellarinvestment.file.FileUploadUtil;
import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.model.project.*;
import com.example.stellarinvestment.repository.ProjectRepository;
import com.example.stellarinvestment.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Project createNewProject(String tariffsListJson, String personsListJson,
                                 float amountNeeded, String shortDescription, String title,
                                 String longDescription, String aboutCreator, Date finishTime, String userEmail) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Tariff> tariffsList = objectMapper.readValue(tariffsListJson, new TypeReference<>() {
        });
        List<Team> personsList = objectMapper.readValue(personsListJson, new TypeReference<>() {
        });

        Project project = new Project();
        project.setCreatedTime(new Date());
        project.setAmountNeeded(amountNeeded);
        project.setAboutCreator(aboutCreator);
        project.setEnabled(false);
        project.setFinishTime(finishTime);
        project.setLongDescription(longDescription);
        project.setShortDescription(shortDescription);
        project.setMainImage("");
        project.setTitle(title);
        if (aboutCreator.isEmpty()) {
            project.setStatus(ProjectStatus.TEAM_FORMATION);
        } else {
            project.setStatus(ProjectStatus.WAITING);
        }


        User user = userService.getUserByEmail(userEmail);
        project.setUser(user);

        for (Tariff tariff : tariffsList) {
            tariff.setProject(project);
        }

        for (Team team : personsList) {
            team.setProject(project);
        }

        project.setTariffs(tariffsList);
        project.setTeams(personsList);

        projectRepository.save(project);

        return project;
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getProjectsInTeamFormationStatus() {
        return projectRepository.findByStatus(ProjectStatus.TEAM_FORMATION);
    }

    public List<Project> getProjectsCreatedByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public void saveUploadedImages(MultipartFile mainImageMultipart,
                                    MultipartFile[] extraImageMultiparts, Project project) throws IOException {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(mainImageMultipart.getOriginalFilename()));
            String uploadDir = "project-images/" + project.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
        }

        if (extraImageMultiparts.length > 0) {
            String uploadDir = "project-images/" + project.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (multipartFile.isEmpty()) continue;

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }

    }

    public void setExtraImageNames(MultipartFile[] extraImageMultiparts, Project project) {
        if (extraImageMultiparts.length > 0) {
            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                    project.addExtraImage(fileName);
                }
            }
        }
    }

    public void setMainImageName(MultipartFile mainImageMultipart, Project project) {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(mainImageMultipart.getOriginalFilename()));
            project.setMainImage(fileName);
        }
    }

    public Project get(Integer id) throws ProjectNotFoundException {
        try {
            return projectRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProjectNotFoundException("Could not find any product with ID " + id);
        }
    }
}
