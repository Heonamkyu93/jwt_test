package com.example.spring.jwt.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Table(name = "jwt")
public class Member {
    @Id
    @Column(name = "username")
    private String username;
    @Column (name = "password")
    private String password;
   @Column (name = "roles")
    private String roles;



    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
