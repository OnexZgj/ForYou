package com.it.onex.foryou.bean;

import java.io.Serializable;

/**
 * Created by OnexZgj on 2018/8/21:08:14.
 * des:
 */

public class AddToDoDetail implements Serializable{

    private long completeDate ;
    private String completeDateStr;
    private String content;
    private long date;
    private String dateStr;
    private int id;
    private int status;
    private String  title;
    private int type;
    private int userId;

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "AddToDoDetail{" +
                "completeDate=" + completeDate +
                ", completeDateStr='" + completeDateStr + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                '}';
    }
}
