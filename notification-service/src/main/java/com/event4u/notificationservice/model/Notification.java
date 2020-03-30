/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.event4u.notificationservice.model;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author DT User
 */

@Entity
public class Notification {


    @Id
    @NotNull(message = "Id cannot be null")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    //Kome se salje notifikacija
    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    //Vise notifikacija moze biti za isti događaj
    @ManyToOne
    @JoinColumn(name="events_id")
    private Events event;

    @NotNull(message = "Message cannot be null")
    @Size(min = 5, max = 100, message = "Message must be between 5 and 100 characters")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    @AssertFalse
    private boolean isRead;

    protected Notification() {
    }

    public Notification(User u, Events e, String message, Date date, boolean isRead) {
        this.user=u;
        this.event=e;
        this.message=message;
        this.date=date;
        this.isRead=isRead;
  }
      
    @Override
    public String toString() {
        return String.format(
                "Notification[NotificationId='%s', UserId='%s', Event='%s', Message='%s', Date='%s', IsRead='%s']",
                notificationId, user.getUserId(), event.getEventId(), message, date, isRead);
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public Long getEventId() {
        return event.getEventId();
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean b) { this.isRead=b;}

}