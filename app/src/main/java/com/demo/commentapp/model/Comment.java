package com.demo.commentapp.model;


import java.util.Date;
import java.util.Random;

public class Comment {

    private String comment;

    private Date created = new Date();

    public void generateRandomFakeComment() {

        int endNums = (int)(Math.random()*9000)+1000;

        comment = "abc " + endNums;

    }
}
