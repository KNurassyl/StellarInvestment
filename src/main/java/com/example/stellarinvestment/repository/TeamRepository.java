package com.example.stellarinvestment.repository;

import com.example.stellarinvestment.model.project.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Integer> {

}
