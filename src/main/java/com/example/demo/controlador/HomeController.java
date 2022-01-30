/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.modelo.PasswordModel;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Home;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class HomeController implements ActionListener {
    
    Home home;
    PasswordRepository passwordRepository;
    SecurityController sc = new SecurityController();
    Validations validations;
    
    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N
    
    String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    String templateHtmlEnd = "</h1></html>";

    public HomeController(Home home, PasswordRepository pr) {
        this.home = home;
        this.passwordRepository = pr;
        createEvents();
        home.getJtpInputs().setSelectedIndex(0);
        home.getJtpTables().setSelectedIndex(0);
    }


    
    private void createEvents(){
        home.getBtnSelectPassword().addActionListener(this);
        home.getBtnSelectUsers().addActionListener(this);
        home.getBtnSelectConfig().addActionListener(this);
        //Passwords
        home.getBtnCleanPassword().addActionListener(this);
        home.getBtnDeletePassword().addActionListener(this);
        home.getBtnSavePassword().addActionListener(this);
        home.getBtnUpdatePassword().addActionListener(this);
        home.getBtnSearchPassword().addActionListener(this);
        //Users
        home.getBtnDeleteUser().addActionListener(this);
        home.getBtnNewUser().addActionListener(this);
        home.getBtnSearchUser().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == home.getBtnSelectPassword()){
            home.getJtpInputs().setSelectedIndex(0);
            home.getJtpTables().setSelectedIndex(0);
        } else if( event.getSource() == home.getBtnSelectUsers()){
            home.getJtpInputs().setSelectedIndex(1);
            home.getJtpTables().setSelectedIndex(1);
        } else if (event.getSource() == home.getBtnSelectConfig()){
            home.getJtpInputs().setSelectedIndex(2);
            home.getJtpTables().setSelectedIndex(2);
        } else if (event.getSource()== home.getBtnCleanPassword()){
            cleanPasswords();
        } else if (event.getSource() == home.getBtnSavePassword()){
            savePassword();
        }
    }
    
    public void cleanPasswords(){
        home.getTxtEmailPassword().setText("");
        home.getTxtIdPassword().setText("");
        home.getTxtNotePassword().setText("");
        home.getTxtSearchPassword().setText("");
        home.getTxtPasswordPassword().setText("");
    }
    
    public void savePassword(){
        String email = home.getTxtEmailPassword().getText();
        String password = home.getTxtPasswordPassword().getText();
        String addition = home.getTxtNotePassword().getText();
        if (!"".equals(email) && !"".equals(password) && !"".equals(addition)){
            if(validations.validationEmail(email)){
                try{
                    String passwordEncrypt = sc.encryptPass(password);
                    PasswordModel passwordModel = new PasswordModel();
                    passwordModel.setEmail(email);
                    passwordModel.setPassword(passwordEncrypt);
                    passwordModel.setDescription(addition);
                    int answer = JOptionPane.showConfirmDialog(home, templateHtmlStart+"¿Desea guardar esta contraseña?"+templateHtmlEnd,"Guardar contraseña.", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
                    if(answer == 0){
                        passwordRepository.save(passwordModel);
                        cleanPasswords();
                        JOptionPane.showMessageDialog(home, templateHtmlStart+"Contraseña guardada."+templateHtmlEnd, "Guardar contraseña",JOptionPane.PLAIN_MESSAGE, ok);
                    } else {
                        cleanPasswords();
                        JOptionPane.showMessageDialog(home, templateHtmlStart+"Contraseña no guardada."+templateHtmlEnd, "Guardar contraseña",JOptionPane.PLAIN_MESSAGE, alert);
                    }
                } catch(Exception e){
                    System.out.println("Error: "+e.toString());
                }
            }else{
                JOptionPane.showMessageDialog(home, templateHtmlStart+"El correo no es valido."+templateHtmlEnd, "Guardar contraseña",JOptionPane.PLAIN_MESSAGE, alert);
            }
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart+"Hay espacios vacios necesarios."+templateHtmlEnd, "Guardar contraseña",JOptionPane.PLAIN_MESSAGE, alert);
        }
    }
    
}
