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
@Table("usuario")
public class User {
    
    @Id
    @Column("idusuario")
    private Long idusuario;
    @Column("nombre")
    private String nombre;
    @Column("contrasena")
    private String contrasena;

    public User(Long idusuario, String nombre, String contrasena) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public User() {
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contraseña;
    }
    
    
}
