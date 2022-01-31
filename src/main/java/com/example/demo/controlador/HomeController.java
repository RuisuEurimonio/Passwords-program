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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class HomeController implements ActionListener {

    Home home;
    PasswordRepository passwordRepository;
    SecurityController sc = new SecurityController();
    DefaultTableModel modelo = new DefaultTableModel();
    Validations validations;
    String user;
    String passwordLogin;

    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N

    String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    String templateHtmlEnd = "</h1></html>";

    public HomeController(Home home, PasswordRepository pr, String user, String password) {
        this.home = home;
        this.passwordRepository = pr;
        this.user = user;
        this.passwordLogin = password;
        home.getTxtNameInfo().setText(user);
        createEvents();
        tablePasswords();
        home.getJtpInputs().setSelectedIndex(0);
        home.getJtpTables().setSelectedIndex(0);
    }

    private void createEvents() {
        home.getBtnSelectPassword().addActionListener(this);
        home.getBtnSelectUsers().addActionListener(this);
        home.getBtnSelectConfig().addActionListener(this);
        //Passwords
        home.getBtnCleanPassword().addActionListener(this);
        home.getBtnDeletePassword().addActionListener(this);
        home.getBtnSavePassword().addActionListener(this);
        home.getBtnUpdatePassword().addActionListener(this);
        home.getBtnSearchPassword().addActionListener(this);
        home.getJtPasswords().addMouseListener(mouseListener);
        //Users
        home.getBtnDeleteUser().addActionListener(this);
        home.getBtnNewUser().addActionListener(this);
        home.getBtnSearchUser().addActionListener(this);
    }

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            loadPassword();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == home.getBtnSelectPassword()) {
            home.getJtpInputs().setSelectedIndex(0);
            home.getJtpTables().setSelectedIndex(0);
            cleanTable();
            tablePasswords();
            cleanPasswords();
        } else if (event.getSource() == home.getBtnSelectUsers()) {
            home.getJtpInputs().setSelectedIndex(1);
            home.getJtpTables().setSelectedIndex(1);
        } else if (event.getSource() == home.getBtnSelectConfig()) {
            home.getJtpInputs().setSelectedIndex(2);
            home.getJtpTables().setSelectedIndex(2);
        } else if (event.getSource() == home.getBtnCleanPassword()) {
            cleanPasswords();
        } else if (event.getSource() == home.getBtnSavePassword()) {
            savePassword();
        } else if (event.getSource() == home.getBtnUpdatePassword()) {
            updatePassword();
        }
    }

    public void savePassword() {
        String email = home.getTxtEmailPassword().getText();
        String password = home.getTxtPasswordPassword().getText();
        String addition = home.getTxtNotePassword().getText();
        if (!"".equals(email) && !"".equals(password) && !"".equals(addition)) {
            if (validations.validationEmail(email)) {
                try {
                    String passwordEncrypt = sc.encryptPass(password);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                    PasswordModel passwordModel = new PasswordModel();
                    passwordModel.setEmail(email);
                    passwordModel.setPassword(passwordEncrypt);
                    passwordModel.setDescription(addition);
                    passwordModel.setDate(sdf.format(date));
                    int answer = JOptionPane.showConfirmDialog(home, templateHtmlStart + "¿Desea guardar esta contraseña?" + templateHtmlEnd, "Guardar contraseña.", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
                    if (answer == 0) {
                        passwordRepository.save(passwordModel);
                        cleanPasswords();
                        cleanTable();
                        tablePasswords();
                        JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña guardada." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, ok);
                    } else {
                        cleanPasswords();
                        JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña no guardada." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.toString());
                }
            } else {
                JOptionPane.showMessageDialog(home, templateHtmlStart + "El correo no es valido." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            }
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Hay espacios vacios necesarios." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    public void loadPassword() {
        cleanPasswords();
        int row = home.getJtPasswords().getSelectedRow();
        String id = home.getJtPasswords().getValueAt(row, 0).toString();
        String email = home.getJtPasswords().getValueAt(row, 1).toString();
        String password = home.getJtPasswords().getValueAt(row, 2).toString();
        String description = home.getJtPasswords().getValueAt(row, 3).toString();
        String date = home.getJtPasswords().getValueAt(row, 4).toString();
        String answer = JOptionPane.showInputDialog(home, templateHtmlStart+"Repite la contraseña del usuario actual"+templateHtmlEnd, "Actualizar contraseña", 3);
        String passwordE = sc.encryptPass(answer);
        if(passwordE.equals(passwordLogin)){
            home.getTxtIdPassword().setText(id);
            home.getTxtEmailPassword().setText(email);
            home.getTxtPasswordPassword().setText(password);
            home.getTxtNotePassword().setText(description);
            home.getTxtDatePassword().setText(date);
        }else{
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña incorrecta." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, error);
        }
    }

    public void updatePassword() {
        if (!"".equals(home.getTxtIdPassword().getText())) {
            
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Seleccione una contraseña." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    public void tablePasswords() {
        modelo = (DefaultTableModel) home.getJtPasswords().getModel();
        passwordRepository.findAll().forEach(password -> {
            Object[] row = new Object[5];
            row[0] = password.getIdPassword();
            row[1] = password.getEmail();
            row[2] = password.getPassword();
            row[3] = password.getDescription();
            row[4] = password.getDate();
            modelo.addRow(row);
        });
        home.getJtPasswords().setModel(modelo);
    }

    public void cleanTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanPasswords() {
        home.getTxtEmailPassword().setText("");
        home.getTxtIdPassword().setText("");
        home.getTxtNotePassword().setText("");
        home.getTxtSearchPassword().setText("");
        home.getTxtPasswordPassword().setText("");
    }

}
