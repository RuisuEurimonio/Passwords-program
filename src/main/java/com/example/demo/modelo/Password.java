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
@Table("listpassword")
public class Password {
    
    @Id
    @Column("codigo")
    private Long codigo;
    @Column("cuenta")
    private Long cuenta;
    @Column("correo")
    private String correo;
    @Column("contrasena")
    private String contrasena;
    @Column("nota")
    private String nota;

    public Password() {
    }

    public Password(Long codigo, Long cuenta, String correo, String contrasena, String nota) {
        this.codigo = codigo;
        this.cuenta = cuenta;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nota = nota;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContraseña(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    
    
    
}
