/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author Usuario
 */
@Table("password")
public class Password {
    
    @Id
    @Column("idPassword")
    private Long idPassword;
    @Column("email")
    private Long email;
    @Column("password")
    private String password;
    @Column("description")
    private String description;

    public Password() {
    } 

    public Password(Long idPassword, Long email, String password, String description) {
        this.idPassword = idPassword;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public Long getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(Long idPassword) {
        this.idPassword = idPassword;
    }

    public Long getEmail() {
        return email;
    }

    public void setEmail(Long email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
