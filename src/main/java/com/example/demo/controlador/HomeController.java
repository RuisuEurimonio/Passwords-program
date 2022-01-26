/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.vista.Home;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class HomeController implements ActionListener {
    
    Home home = new Home();
    
    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N
    
    public HomeController(Home home){
        this.home = home;
        createEvents();
        home.getJtpInputs().setSelectedIndex(0);
        home.getJtpTables().setSelectedIndex(0);
    }
    
    private void createEvents(){
        home.getBtnSelectPassword().addActionListener(this);
        home.getBtnSelectUsers().addActionListener(this);
        home.getBtnSelectConfig().addActionListener(this);
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
        }
    }
    
}
