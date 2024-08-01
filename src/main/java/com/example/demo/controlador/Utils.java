/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class Utils {

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static ImageIcon ok = new javax.swing.ImageIcon(Utils.class.getResource("/ok.png")); // NOI18N
    public static ImageIcon error = new javax.swing.ImageIcon(Utils.class.getResource("/error.png")); //NOI18N
    public static ImageIcon question = new javax.swing.ImageIcon(Utils.class.getResource("/question.png")); // NOI18N
    public static ImageIcon alert = new javax.swing.ImageIcon(Utils.class.getResource("/alert.png")); //NOI18N
    
    public static String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    public static String templateHtmlEnd = "</h1></html>";
}
