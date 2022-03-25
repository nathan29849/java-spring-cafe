package com.kakao.cafe.domain.dto;

import java.io.Serializable;

public class LoginForm implements Serializable {
    private String userId;
    private String password;
    private String name;
    private int id;

    public LoginForm(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
