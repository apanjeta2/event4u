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
    private Long userId;
    private String message;
    private Date date;
    private boolean isRead;

    protected Notification() {
    }

    public Notification(Long userId, String message, Date date, boolean isRead) {
        this.userId=userId;
        this.message=message;
        this.date=date;
        this.isRead=isRead;
  }
      
    @Override
    public String toString() {
        return String.format(
                "Notification[NotificationId='%s', UserId='%s', Message='%s', Date='%s', IsRead='%s']",
                notificationId, userId, message, date, isRead);
    }

    public Long getUserId() {
        return notificationId;
    }

    public Long getNotificationId() {
        return userId;
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
