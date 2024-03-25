package com.example.stellarinvestment.repository;

import com.example.stellarinvestment.model.Role;
import com.example.stellarinvestment.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
