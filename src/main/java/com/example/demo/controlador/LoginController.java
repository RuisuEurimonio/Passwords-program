/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.modelo.LoginModel;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.vista.Home;
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

    RegisterNewUser dialogRegister = new RegisterNewUser(loginVista, true);
    private LoginModel loginModel;

    private SecurityController securityController = new SecurityController();
    
    String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    String templateHtmlEnd = "</h1></html>";

    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N

    public LoginController(LoginRepository loginRepository, Login loginVista) {
        this.loginRepository = loginRepository;
        this.loginVista = loginVista;
        createEvents();
    }

    public void createEvents() {
        loginVista.getButtonCreate().addActionListener(this);
        loginVista.getButtonLogin().addActionListener(this);
        loginVista.getLabelUser().addActionListener(this);
        loginVista.getLabelPassword().addActionListener(this);
        dialogRegister.getButtonNewAccept().addActionListener(this);
        dialogRegister.getLabelNewUser().addActionListener(this);
        dialogRegister.getLabelNewPassword().addActionListener(this);
        dialogRegister.getLabelNewRepeatPass().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginVista.getButtonCreate()) {
            dialogRegister.setVisible(true);
        } else if (event.getSource() == loginVista.getButtonLogin()
                || event.getSource() == loginVista.getLabelUser()
                || event.getSource() == loginVista.getLabelPassword()) {
            login();
        } else if (event.getSource() == dialogRegister.getButtonNewAccept()
                || event.getSource() == dialogRegister.getLabelNewUser()
                || event.getSource() == dialogRegister.getLabelNewPassword()
                || event.getSource() == dialogRegister.getLabelNewRepeatPass()) {
            register();
        }
    }

    private void login() {
        String txtUser = loginVista.getLabelUser().getText();
        String txtPassword = new String(loginVista.getLabelPassword().getPassword());
        if (!"".equals(txtUser) && !"".equals(txtPassword)) {
            String password = securityController.encryptPass(txtPassword);
            Optional<LoginModel> loginDB = loginRepository.findByNameAndPassword(txtUser, password);
            if (loginDB.isPresent()) {
                loginVista.dispose();
                Home home = new Home();
                HomeController hm = new HomeController(home);
                home.setVisible(true);
                JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Has iniciado sesión. " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, ok);
            } else {
                JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Usuario o contraseña incorrectos " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, error);
            }
        } else {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart+"Ups!!, llena todas las casillas"+templateHtmlEnd+"\n"+templateHtmlStart+"~~Onegaishimasu Oniichan. "+templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    private void register() {
        String user = dialogRegister.getLabelNewUser().getText();
        String password = dialogRegister.getLabelNewPassword().getText();
        String repeatPass = dialogRegister.getLabelNewRepeatPass().getText();
        if (!"".equals(user) && !"".equals(password) && !"".equals(repeatPass)) {
            if (user.length() >= 3 && password.length() >= 9) {
                if (password.equals(repeatPass)) {
                    if (user.contains("Ruisu")) {
                        String passwordEncrypt = securityController.encryptPass(password);
                        LoginModel account = new LoginModel();
                        account.setName(user);
                        account.setPassword(passwordEncrypt);
                        int option = JOptionPane.showConfirmDialog(loginVista, templateHtmlStart + " Desea crear esta cuenta? " + templateHtmlEnd, "Registrarse", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
                        if (option == 0) {
                            loginRepository.save(account);
                            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Usuario guardado. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, ok);
                            dialogRegister.getLabelNewUser().setText("");
                            dialogRegister.getLabelNewPassword().setText("");
                            dialogRegister.getLabelNewRepeatPass().setText("");
                            dialogRegister.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Se ha cancelado el registrar usuario. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
                            dialogRegister.getLabelNewUser().setText("");
                            dialogRegister.getLabelNewPassword().setText("");
                            dialogRegister.getLabelNewRepeatPass().setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ha ocurrido un error, no cumple con los requerimientos. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, error);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ups!!, las contraseñas no son iguales. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
                    dialogRegister.getLabelNewRepeatPass().setText("");
                }
            } else {
                JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " El usuario debe tener minimo 3 caracteres y la contraseña 9. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
            }
        } else {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ups!!, llena todas las casillas </h1></html>\n"+ templateHtmlStart + "~~Onegaishimasu Oniichan. </h1></html>", "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

}
