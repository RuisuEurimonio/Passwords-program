/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.modelo.LoginModel;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.vista.Login;
import com.example.demo.vista.RegisterNewUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class LoginController implements ActionListener {

    private LoginRepository loginRepository;
    
    private Login loginVista;
    
    RegisterNewUser dialogRegister = new RegisterNewUser(loginVista,true);
    private LoginModel loginModel;
    
    private SecurityController securityController = new SecurityController();
    
    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N

    public LoginController(LoginRepository loginRepository, Login loginVista) {
        this.loginRepository = loginRepository;
        this.loginVista = loginVista;
        createEvents();
    }

    
    
    public void createEvents(){
        loginVista.getButtonCreate().addActionListener(this);
        loginVista.getButtonLogin().addActionListener(this);
        dialogRegister.getButtonNewAccept().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == loginVista.getButtonCreate()){
            dialogRegister.setVisible(true);
        } else if(event.getSource() == loginVista.getButtonLogin()){
            login();
        } else if(event.getSource()==dialogRegister.getButtonNewAccept()){
            register();
        }
    }
    
     private void login(){
        String txtUser = loginVista.getLabelUser().toString();
        String txtPassword = loginVista.getLabelPassword().toString();
        Optional<LoginModel> loginDB = loginRepository.findByNameAndPassword(txtUser, txtPassword);
        System.out.println(loginDB); 
    }
     
     private void register(){
         String user = dialogRegister.getLabelNewUser().getText();
         String password = dialogRegister.getLabelNewPassword().getText();
         String repeatPass = dialogRegister.getLabelNewRepeatPass().getText();
         if(!"".equals(user) && !"".equals(password) && !"".equals(repeatPass)){
             if(password.equals(repeatPass)){
                 if(user.contains("Ruisu")){
                    String passwordEncrypt = securityController.encryptPass(password);
                    LoginModel account = new LoginModel();
                    account.setName(user);
                    account.setPassword(passwordEncrypt);
                    loginRepository.save(account);
                    JOptionPane.showMessageDialog(dialogRegister, "<html><h1 style='color:#cc8398'> Usuario guardado. </h1></html>","Registrarse", JOptionPane.PLAIN_MESSAGE , ok);
                    dialogRegister.getLabelNewUser().setText("");
                    dialogRegister.getLabelNewPassword().setText("");
                    dialogRegister.getLabelNewRepeatPass().setText("");
                    dialogRegister.dispose();
                 }else{
                    JOptionPane.showMessageDialog(dialogRegister, "<html><h1 style='color:#cc8398'> Ha ocurrido un error, no cumple con los requerimientos. </h1></html>","Registrarse", JOptionPane.PLAIN_MESSAGE , error); 
                 }
             }else{
                 JOptionPane.showMessageDialog(dialogRegister,  "<html><h1 style='color:#cc8398'> Ups!!, las contraseñas no son iguales. </h1></html>","Registrarse", JOptionPane.PLAIN_MESSAGE , alert);
                 dialogRegister.getLabelNewRepeatPass().setText("");
             }
         } else {
             JOptionPane.showMessageDialog(dialogRegister, "<html><h1 style='color:#cc8398'> Ups!!, llena todas las casillas ~~Onegaishimasu Oniichan. </h1></html>","Registrarse", JOptionPane.PLAIN_MESSAGE , alert);
         }
     }
      
}
