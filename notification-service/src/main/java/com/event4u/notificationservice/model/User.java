/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.event4u.notificationservice.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 *
 * @author DT User
 */
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @NotNull(message = "Id cannot be null")
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> subscriber;

    @ManyToMany(mappedBy = "subscriber")
    private List<User> subsribedTo;

    @OneToMany (mappedBy = "user")
    private List<Notification> notifications;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Events> events;

    protected User() {
    }

    public User(Long id) {
        this.userId=id;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d]",
                userId);
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId=userId;
    }


}
