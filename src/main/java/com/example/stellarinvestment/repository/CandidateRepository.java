package com.example.stellarinvestment.repository;

import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.model.project.Candidate;
import com.example.stellarinvestment.model.project.Project;
import com.example.stellarinvestment.model.project.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    long countByProjectTeamAndResultIsTrue(Team projectTeam);

    List<Candidate> findAllByProjectAndResultIsTrue(Project project);
    Candidate getCandidateByProjectTeamIdAndUser(Integer id, User user);

    List<Candidate> findAllByUser(User user);
}

