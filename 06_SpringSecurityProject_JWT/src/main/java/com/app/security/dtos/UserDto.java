package com.app.security.dtos;

import java.io.Serializable;
import java.util.List;

import com.app.security.entity.Role;

import lombok.Data;

@Data
public class UserDto implements Serializable {
    private String userId;
    private String username;
    private String password;
    private List<Role> roles;
}