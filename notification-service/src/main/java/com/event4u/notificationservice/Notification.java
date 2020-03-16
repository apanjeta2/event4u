/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.event4u.notificationservice;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author DT User
 */
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;
    @ManyToOne
    private User userId;
    private String message;
    private Date date;
    private boolean isRead;


    protected Notification() {
    }

    public Notification(User u,String message, Date date, boolean isRead) {
        this.userId=u;
        this.message=message;
        this.date=date;
        this.isRead=isRead;
  }
      
    @Override
    public String toString() {
        return String.format(
                "Notification[NotificationId='%s', UserId='%s', Message='%s', Date='%s', IsRead='%s']",
                notificationId, userId.getUserId(), message, date, isRead);
    }

    public Long getUserId() {
        return userId.getUserId();
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

    public boolean getIsRead() {
        return isRead;
    }

}
