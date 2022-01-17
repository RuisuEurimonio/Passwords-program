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
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JDialog;
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
    String BOXPOWER = "RuxXEuri";

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
                  String prueba = encryptPass(password);
                  System.out.println(prueba);
                  String prueba2 = decryptPass(prueba);
                  System.out.println(prueba2);
             }else{
                 JOptionPane.showMessageDialog(dialogRegister, "Las contraeñas no son iguales");
                 dialogRegister.getLabelNewRepeatPass().setText("");
             }
         } else {
             JOptionPane.showMessageDialog(dialogRegister, "Existen campos vacios.");
         }
     }
     
     private SecretKeySpec CreateBit(String text){
         try {
             byte [] stringText = text.getBytes("UTF-8");
             MessageDigest md = MessageDigest.getInstance("SHA-1");
             stringText = md.digest(stringText);
             stringText = Arrays.copyOf(stringText, 16);
             SecretKeySpec secretKeySpec = new SecretKeySpec(stringText, "AES");
             return secretKeySpec;
         } catch (Exception e) {
             System.out.println("Error: "+ e.toString());
             return null;
         }
     }
     
     private String encryptPass(String pass){
         try {
             SecretKeySpec secretKeySpec = CreateBit(BOXPOWER);
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
             
             byte [] stringText = pass.getBytes("UTF-8");
             byte [] encryptText = cipher.doFinal(stringText);
             
             String passEncrypt = Base64.encode(encryptText);
             return passEncrypt;
             
         } catch (Exception e) {
             System.out.println("Error: "+e.toString());
             return null;
         }
     }
     
      private String decryptPass(String passEncrypt){
         try{
             SecretKeySpec secretKeySpec = CreateBit(BOXPOWER);
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
             
             byte [] stringText = Base64.decode(passEncrypt);
             byte [] decryptText = cipher.doFinal(stringText);
             
             String passDecrypt = new String(decryptText);
             return passDecrypt;
         } catch(Exception e){
             return "";
         }   
     }
      
}
