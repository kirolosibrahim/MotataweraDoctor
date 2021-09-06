package com.kmk.motatawera.doctor.model;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

public class QuizModel {
    private String id;
    private String title;
    private Boolean QuizActive;
    private Boolean QuizFinished;
    private String doctor_id;
    private String subject_id;


     @ServerTimestamp
     private Date time;


    public QuizModel() {
    }

    public QuizModel(String id, String title, Boolean quizActive, Boolean quizFinished, String doctor_id, String subject_id, Date time) {
        this.id = id;
        this.title = title;
        QuizActive = quizActive;
        QuizFinished = quizFinished;
        this.doctor_id = doctor_id;
        this.subject_id = subject_id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getQuizActive() {
        return QuizActive;
    }

    public void setQuizActive(Boolean quizActive) {
        QuizActive = quizActive;
    }

    public Boolean getQuizFinished() {
        return QuizFinished;
    }

    public void setQuizFinished(Boolean quizFinished) {
        QuizFinished = quizFinished;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
