/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
    
    public static void handleEmptyInputsMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "Ups!!, llena todas las casillas" + templateHtmlEnd + "\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleWrongDataInputsMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Usuario o contraseña incorrectos " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static void handleSuccesLoginMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Has iniciado sesión. " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, ok);
    }
    
    public static void handleNotValidDataMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "Ups!!, Los campos deben tener:</h1><br><h2 style='color:#FFC6FF'>Usuario: 3 caracteres.</h2><br><h2 style='color:#FFC6FF'>Contraseña: 9 caracteres.</h2></html>", "Guardar usuario.", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotEqualsPasswordsMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Ups!!, las contraseñas no son iguales. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotCorrectKeyWordMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Ha ocurrido un error, no cumple con los requerimientos. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static int handleCreateQuestionMessage(Component component, String message, String title){
        return JOptionPane.showConfirmDialog(component, templateHtmlStart + message + templateHtmlEnd, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
    }
    
    public static void handleCancelActionMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Se ha cancelado el registrar usuario. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleSuccessCreationMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Usuario guardado. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, ok);
    }
}
