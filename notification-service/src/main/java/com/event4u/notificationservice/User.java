/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.event4u.notificationservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

/**
 *
 * @author DT User
 */
@Entity
public class User {

    @Id
    private Long userId;

    @ManyToMany
    private List<User> userList;
    @ManyToMany(mappedBy = "userList")
    private List<User> subsribedTo;
    @OneToMany (mappedBy = "userId")
    private List<Notification> notificationList;

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

    @ManyToMany
    private List<Events> eventsList;

}
