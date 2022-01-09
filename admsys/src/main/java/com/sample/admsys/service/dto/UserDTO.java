package com.sample.admsys.service.dto;

import com.sample.admsys.model.Role;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public class UserDTO {

    private Long id;

    @NotNull(message = "You must set an USERNAME.")
    private String username;

    @NotNull(message = "You must inform a PASSWORD.")
    private String password;

    @NotNull(message = "You must inform a NAME")
    private String name;

    private LocalDateTime createTime;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
