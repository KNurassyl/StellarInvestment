package com.example.stellarinvestment.model.project;

import com.example.stellarinvestment.model.IdBasedEntity;
import com.example.stellarinvestment.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends IdBasedEntity {

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "long_description")
    private String longDescription;

    @Column(length = 512, nullable = false, name = "about_creator")
    private String aboutCreator;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "finish_time")
    private Date finishTime;

    @Column(name = "amount_needed")
    private float amountNeeded;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    private List<Tariff> tariffs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    private List<Team> teams;

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getAboutCreator() {
        return aboutCreator;
    }

    public void setAboutCreator(String aboutCreator) {
        this.aboutCreator = aboutCreator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public float getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(float amountNeeded) {
        this.amountNeeded = amountNeeded;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
