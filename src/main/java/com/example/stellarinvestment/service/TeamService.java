package com.example.stellarinvestment.service;

import com.example.stellarinvestment.model.project.Team;
import com.example.stellarinvestment.repository.ProjectRepository;
import com.example.stellarinvestment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team getTeamById(Integer id) {
        return teamRepository.findById(id).get();
    }

    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }
}
