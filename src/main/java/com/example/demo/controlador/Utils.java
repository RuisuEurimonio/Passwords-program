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
        String regex = "^[\\w-]{2,}(\\.[\\w-]+)*@[A-Za-z0-9]{3,}+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    private static ImageIcon ok = new javax.swing.ImageIcon(Utils.class.getResource("/ok.png")); // NOI18N
    private static ImageIcon error = new javax.swing.ImageIcon(Utils.class.getResource("/error.png")); //NOI18N
    public static ImageIcon question = new javax.swing.ImageIcon(Utils.class.getResource("/question.png")); // NOI18N
    public static ImageIcon alert = new javax.swing.ImageIcon(Utils.class.getResource("/alert.png")); //NOI18N
    
    public static String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    public static String templateHtmlEnd = "</h1></html>";
    
    public static void handleEmptyInputsMessage(Component component, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "Ups!!, llena todas las casillas" + templateHtmlEnd + "\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. " + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleWrongDataInputsMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Usuario o contraseña incorrectos " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static void handleSuccesLoginMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Has iniciado sesión. " + templateHtmlEnd, "Iniciar sesión.", JOptionPane.PLAIN_MESSAGE, ok);
    }
    
    public static void handleNotValidDataMessage(Component component, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "Ups!!, Los campos deben tener:</h1><br><h2 style='color:#FFC6FF'>Usuario: 3 caracteres.</h2><br><h2 style='color:#FFC6FF'>Contraseña: 9 caracteres.</h2></html>", title, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotEqualsPasswordsMessage(Component component, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Ups!!, las contraseñas no son iguales. " + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotCorrectKeyWordMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + " Ha ocurrido un error, no cumple con los requerimientos. " + templateHtmlEnd, "Registrarse", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static int handleActionQuestionMessage(Component component, String message, String title){
        return JOptionPane.showConfirmDialog(component, templateHtmlStart + message + templateHtmlEnd, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
    }
    
    public static void handleCancelActionMessage(Component component, String message, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + message + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleSuccessActionMessage(Component component, String message, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + message + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, ok);
    }
    
    public static void handleWrongEmailFormatMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "El correo no es valido." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleEmptyInputMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "El campo esta vacío." + templateHtmlEnd, "Ver contraseña", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static void handleWrongPasswordMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "Ups!!! Contraseña incorrecta." + templateHtmlEnd, "Ver contraseña", JOptionPane.PLAIN_MESSAGE, error);
    }
    
    public static void handleNotRowSelectedMessage(Component component, String message, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + message + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotChangeDataMessage(Component component){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "No hay cambio en los datos." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static int handleCustomQuestionUserMessage(Component component, String email, String description){
        return JOptionPane.showConfirmDialog(component, "<html><h1 style='font-size:18px;color:#cc8398;text-align:center'>¿Deseas eliminar esta contraseña?" + "</h1><br><h2 align='center'>Correo: " + email + "</h2><br><h2 align='center'>Descripción: " + description + "</h2></html>", "Eliminar contraseña", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
    }
    
    public static int handleCustomQuestionUserMessage(Component component, String user){
        return JOptionPane.showConfirmDialog(component, "<html><h1 style='font-size:18px;color:#cc8398;text-align:center'> ¿Deseas eliminar este usuario?" + "</h1><br><h2 align='center'>Usuario: " + user + "</h2></html>", "Eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, alert);
    }
    
    public static void handleNotCoincidenceSearchMessage(Component component, String title){
        JOptionPane.showMessageDialog(component, templateHtmlStart + "No hay coincidencias." + templateHtmlEnd, title, JOptionPane.PLAIN_MESSAGE, alert);
    }
}
