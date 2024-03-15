package com.example.stellarinvestment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projects")
public class Project extends IdBasedEntity{

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "long_description")
    private String longDescription;

    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
