package com.example.stellarinvestment.model.project;

import com.example.stellarinvestment.model.IdBasedEntity;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends IdBasedEntity {
    @Column(length = 256, nullable = false)
    private String position;

    @Column(length = 256, nullable = false)
    private String experience;

    private int quantity;

    @Column(length = 512, nullable = false, name = "additional_info")
    private String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
