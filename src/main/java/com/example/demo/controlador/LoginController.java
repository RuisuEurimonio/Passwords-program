/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.modelo.LoginModel;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
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
    private PasswordRepository passwordRepository;
    private Login loginVista;

    private RegisterNewUser dialogRegister;
    private LoginModel loginModel;

    private SecurityController securityController;

    private String templateHtmlStart = Utils.templateHtmlStart;
    private String templateHtmlEnd = Utils.templateHtmlEnd;

    private ImageIcon okImage = Utils.ok;
    private ImageIcon alertImage = Utils.alert;
    private ImageIcon questionImage = Utils.question;
    private ImageIcon errorImage = Utils.error;

    private final String KEY_WORD = SecurityController.REGISTER_KEY;

    public LoginController(LoginRepository loginRepository, Login loginVista, PasswordRepository pr) {
        this.loginRepository = loginRepository;
        this.loginVista = loginVista;
        this.passwordRepository = pr;
        this.dialogRegister = new RegisterNewUser(loginVista, true);
        this.securityController = new SecurityController();

        initializeOtherResources();
    }

    private void initializeOtherResources() {
        createEvents();
        loginVista.setIconImage(new ImageIcon(getClass().getResource("/lockIcon.png")).getImage());
        dialogRegister.setIconImage(new ImageIcon(getClass().getResource("/lockIcon.png")).getImage());
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
            cleanLoginInputs();
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
        if ("".equals(txtUser) && "".equals(txtPassword)) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + "Ups!!, llena todas las casillas" + templateHtmlEnd + "\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alertImage);
            return;
        }

        String passwordEncode = securityController.encryptPass(txtPassword);
        Optional<LoginModel> DataLoginDB = loginRepository.findByNameAndPassword(txtUser, passwordEncode);

        if (DataLoginDB.isEmpty()) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Usuario o contraseña incorrectos " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, errorImage);
            cleanLoginInputs();
            return;
        }

        loginVista.dispose();
        Home home = new Home();
        HomeController hm = new HomeController(home, passwordRepository, loginRepository, txtUser, passwordEncode);
        home.setVisible(true);
        JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Has iniciado sesión. " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, okImage);
    }

    private void register() {
        String user = dialogRegister.getLabelNewUser().getText();
        String password = dialogRegister.getLabelNewPassword().getText();
        String repeatPass = dialogRegister.getLabelNewRepeatPass().getText();

        if ("".equals(user) && "".equals(password) && "".equals(repeatPass)) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ups!!, llena todas las casillas </h1></html>\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. </h1></html>", "Registrarse", JOptionPane.PLAIN_MESSAGE, alertImage);
            return;
        }

        if (user.length() < 3 && password.length() < 9) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + "Ups!!, Los campos deben tener:</h1><br><h2 style='color:#FFC6FF'>Usuario: 3 caracteres.</h2><br><h2 style='color:#FFC6FF'>Contraseña: 9 caracteres.</h2></html>", "Guardar usuario.", JOptionPane.PLAIN_MESSAGE, alertImage);
            return;
        }

        if (!password.equals(repeatPass)) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ups!!, las contraseñas no son iguales. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alertImage);
            dialogRegister.getLabelNewRepeatPass().setText("");
            return;
        }

        if (!user.contains(KEY_WORD)) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Ha ocurrido un error, no cumple con los requerimientos. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, errorImage);
            return;
        }

        String passwordEncrypt = securityController.encryptPass(password);
        LoginModel account = new LoginModel();
        account.setName(user);
        account.setPassword(passwordEncrypt);
        
        int option = JOptionPane.showConfirmDialog(loginVista, templateHtmlStart + " Deseas crear esta cuenta? " + templateHtmlEnd, "Registrarse", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, questionImage);
        if (option != 0) {
            JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Se ha cancelado el registrar usuario. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alertImage);
            resetInputsRegister();
        }
        
        loginRepository.save(account);
        JOptionPane.showMessageDialog(dialogRegister, templateHtmlStart + " Usuario guardado. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, okImage);
        resetInputsRegister();
        dialogRegister.dispose();
    }

    private void resetInputsRegister() {
        dialogRegister.getLabelNewUser().setText("");
        dialogRegister.getLabelNewPassword().setText("");
        dialogRegister.getLabelNewRepeatPass().setText("");
    }

    private void cleanLoginInputs() {
        loginVista.getLabelUser().setText("");
        loginVista.getLabelPassword().setText("");
    }

}
