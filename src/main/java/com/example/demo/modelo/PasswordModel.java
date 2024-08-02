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
@Table("Password")
public class PasswordModel {

    @Id
    @Column("idPassword")
    private int idPassword;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("description")
    private String description;
    @Column("date")
    private String date;

    public PasswordModel() {
    }

    public PasswordModel(int idPassword, String email, String password, String description) {
        this.idPassword = idPassword;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public int getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(int idPassword) {
        this.idPassword = idPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
