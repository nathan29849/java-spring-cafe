package com.kakao.cafe.domain.dto;

import java.io.Serializable;

public class LoginedUser implements Serializable {
    private String userId;
    private String password;
    private String name;
    private Long id;

    public LoginedUser(String userId, String password, String name) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
