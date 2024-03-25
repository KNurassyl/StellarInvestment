package com.example.stellarinvestment.service;

import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.model.project.Project;
import com.example.stellarinvestment.model.project.ProjectStatus;
import com.example.stellarinvestment.model.project.Tariff;
import com.example.stellarinvestment.model.project.Team;
import com.example.stellarinvestment.repository.ProjectRepository;
import com.example.stellarinvestment.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void createNewProject(String tariffsListJson, String personsListJson,
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
    }

}
