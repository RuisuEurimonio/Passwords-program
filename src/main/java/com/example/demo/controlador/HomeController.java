package com.example.demo.controlador;

import com.example.demo.modelo.LoginModel;
import com.example.demo.modelo.PasswordModel;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Home;
import com.example.demo.vista.ValidationPassword;
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

    SecurityController securityController = new SecurityController();
    DefaultTableModel modelo = new DefaultTableModel();
    Home home;
    ValidationPassword validationPassword = new ValidationPassword(home, true);
    LoginRepository loginR;
    PasswordRepository passwordR;
    Validations validations;

    String user;
    String passwordLogin;

    ImageIcon ok = new javax.swing.ImageIcon(getClass().getResource("/ok.png")); // NOI18N
    ImageIcon error = new javax.swing.ImageIcon(getClass().getResource("/error.png")); //NOI18N
    ImageIcon question = new javax.swing.ImageIcon(getClass().getResource("/question.png")); // NOI18N
    ImageIcon alert = new javax.swing.ImageIcon(getClass().getResource("/alert.png")); //NOI18N

    String templateHtmlStart = "<html><h1 style='font-size:18px;color:#cc8398'>";
    String templateHtmlEnd = "</h1></html>";

    public HomeController(Home home, PasswordRepository passwordR, LoginRepository loginR, String user, String password) {
        this.home = home;
        this.passwordR = passwordR;
        this.user = user;
        this.loginR = loginR;
        this.passwordLogin = password;
        home.getTxtNameInfo().setText(user);
        createEvents();
        tablePasswords();
        home.getJtpInputs().setSelectedIndex(0);
        home.getJtpTables().setSelectedIndex(0);
        home.setIconImage(new ImageIcon(getClass().getResource("/lockIcon.png")).getImage());
        validationPassword.setIconImage(new ImageIcon(getClass().getResource("/lockIcon.png")).getImage());
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
        home.getTxtSearchPassword().addActionListener(this);
        home.getJtPasswords().addMouseListener(mouseListener);
        //Users
        home.getBtnNewUser().addActionListener(this);
        home.getBtnDeleteUser().addActionListener(this);
        home.getBtnCleanUser().addActionListener(this);
        home.getBtnSearchUser().addActionListener(this);
        home.getTxtSearchUser().addActionListener(this);
        //ValidationPassword
        validationPassword.getBtnPasswordValidation().addActionListener(this);
        validationPassword.getTxtPasswordValidation().addActionListener(this);
    }

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == home.getJtPasswords()) {
                if (e.getClickCount() % 2 == 0) {
                    loadPassword();
                }
            }
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
            cleanTable();
            tableUser();
            cleanUser();
        } else if (event.getSource() == home.getBtnSelectConfig()) {
            home.getJtpInputs().setSelectedIndex(2);
            home.getJtpTables().setSelectedIndex(2);
        } else if (event.getSource() == home.getBtnCleanPassword()) {
            cleanPasswords();
        } else if (event.getSource() == home.getBtnSavePassword()) {
            savePassword();
        } else if (event.getSource() == home.getBtnUpdatePassword()) {
            updatePassword();
        } else if (event.getSource() == validationPassword.getBtnPasswordValidation()
                || event.getSource() == validationPassword.getTxtPasswordValidation()) {
            //vp = validation password
            validationPassword.dispose();
        } else if (event.getSource() == home.getBtnDeletePassword()) {
            deletePassword();
        } else if (event.getSource() == home.getBtnSearchPassword()
                || event.getSource() == home.getTxtSearchPassword()) {
            cleanTable();
            searchPassword();
        } else if (event.getSource() == home.getBtnNewUser()) {
            saveUser();
        } else if (event.getSource() == home.getBtnCleanUser()) {
            cleanUser();
        } else if (event.getSource() == home.getBtnDeleteUser()) {
            deleteUser();
        } else if (event.getSource() == home.getBtnSearchUser()
                || event.getSource() == home.getTxtSearchUser()) {
            searchUser();
        }
    }

    //Password
    public void savePassword() {
        String email = home.getTxtEmailPassword().getText();
        String password = home.getTxtPasswordPassword().getText();
        String addition = home.getTxtNotePassword().getText();

        if ("".equals(email) && "".equals(password) && "".equals(addition)) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + " Ups!!, llena todas las casillas </h1></html>\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. </h1></html>", "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
            return;
        }

        if (!validations.isValidEmail(email)) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "El correo no es valido." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            return;
        }

        try {
            String passwordEncrypt = securityController.encryptPass(password);

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

            PasswordModel passwordModel = new PasswordModel();
            passwordModel.setEmail(email);
            passwordModel.setPassword(passwordEncrypt);
            passwordModel.setDescription(addition);
            passwordModel.setDate(simpleDateFormat.format(date));

            int answer = JOptionPane.showConfirmDialog(home, templateHtmlStart + "¿Deseas guardar esta contraseña?" + templateHtmlEnd, "Guardar contraseña.", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
            if (answer != 0) {
                cleanPasswords();
                JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña no guardada." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
                return;
            }

            passwordR.save(passwordModel);
            cleanPasswords();
            cleanTable();
            tablePasswords();

            JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña guardada." + templateHtmlEnd, "Guardar contraseña", JOptionPane.PLAIN_MESSAGE, ok);

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public PasswordModel tableDatas(int row) {
        PasswordModel passwordM = new PasswordModel();
        passwordM.setIdPassword(Integer.parseInt(home.getJtPasswords().getValueAt(row, 0).toString()));
        passwordM.setEmail(home.getJtPasswords().getValueAt(row, 1).toString());
        passwordM.setPassword(home.getJtPasswords().getValueAt(row, 2).toString());
        passwordM.setDescription(home.getJtPasswords().getValueAt(row, 3).toString());
        passwordM.setDate(home.getJtPasswords().getValueAt(row, 4).toString());
        return passwordM;
    }

    public void loadPassword() {
        int rowSelected = home.getJtPasswords().getSelectedRow();

        PasswordModel passwordSelected = tableDatas(rowSelected);
        cleanPasswords();
        validationPassword.setVisible(true);
        String passwordTyped = new String(validationPassword.getTxtPasswordValidation().getPassword());

        if ("".equals(passwordTyped)) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "El campo esta vacío." + templateHtmlEnd, "Ver contraseña", JOptionPane.PLAIN_MESSAGE, error);
            return;
        }

        String passwordTypedEncrypted = securityController.encryptPass(passwordTyped);

        if (!(passwordTypedEncrypted.equals(passwordLogin))) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Ups!!! Contraseña incorrecta." + templateHtmlEnd, "Ver contraseña", JOptionPane.PLAIN_MESSAGE, error);
            validationPassword.getTxtPasswordValidation().setText("");
            return;
        }

        String passwordDecoded = securityController.decryptPass(passwordSelected.getPassword());

        home.getTxtIdPassword().setText("" + passwordSelected.getIdPassword());
        home.getTxtEmailPassword().setText(passwordSelected.getEmail());
        home.getTxtPasswordPassword().setText(passwordDecoded);
        home.getTxtNotePassword().setText(passwordSelected.getDescription());
        home.getTxtDatePassword().setText(passwordSelected.getDate());

        home.getBtnSavePassword().setEnabled(false);

        validationPassword.getTxtPasswordValidation().setText("");
    }

    public void updatePassword() {
        if ("".equals(home.getTxtIdPassword().getText())) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Selecciona una contraseña." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            return;
        }

        int rowSelected = home.getJtPasswords().getSelectedRow();

        PasswordModel passwordSelected = tableDatas(rowSelected);
        String passwordInputEncrypt = securityController.encryptPass(home.getTxtPasswordPassword().getText());

        if (passwordSelected.getEmail().equals(home.getTxtEmailPassword().getText())
                || passwordSelected.getPassword().equals(passwordInputEncrypt)
                || passwordSelected.getDescription().equals(home.getTxtNotePassword().getText())) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "No hay cambio en los datos." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            home.getBtnSavePassword().setEnabled(true);
            cleanPasswords();
            return;
        }

        int answer = JOptionPane.showConfirmDialog(home, templateHtmlStart + "¿Deseas actualizar esta contraseña?" + templateHtmlEnd, "Actualizar contraseña", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
        if (answer != 0) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "No se ha actualizado la contraseña." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            home.getBtnSavePassword().setEnabled(true);
            cleanPasswords();
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        
        String newPasswordEncode = securityController.encryptPass(home.getTxtPasswordPassword().getText());
        
        PasswordModel passwordUpdate = new PasswordModel();
        passwordUpdate.setIdPassword(Integer.parseInt(home.getTxtIdPassword().getText()));
        passwordUpdate.setEmail(home.getTxtEmailPassword().getText());
        passwordUpdate.setPassword(newPasswordEncode);
        passwordUpdate.setDescription(home.getTxtNotePassword().getText());
        passwordUpdate.setDate(simpleDateFormat.format(date));
        passwordR.save(passwordUpdate);
        
        JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña actualizada." + templateHtmlEnd, "Actualizar contraseña", JOptionPane.PLAIN_MESSAGE, ok);
        
        cleanPasswords();
        cleanTable();
        tablePasswords();
        home.getBtnSavePassword().setEnabled(true);
    }

    public void deletePassword() {
        int row = home.getJtPasswords().getSelectedRow();
        if (row != -1) {
            String email = home.getJtPasswords().getValueAt(row, 1).toString();
            String description = home.getJtPasswords().getValueAt(row, 3).toString();
            int answer = JOptionPane.showConfirmDialog(home, "<html><h1 style='font-size:18px;color:#cc8398;text-align:center'>¿Deseas eliminar esta contraseña?" + "</h1><br><h2 align='center'>Correo: " + email + "</h2><br><h2 align='center'>Descripción: " + description + "</h2></html>", "Eliminar contraseña", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
            if (answer == 0) {
                int id = Integer.parseInt(home.getJtPasswords().getValueAt(row, 0).toString());
                passwordR.deleteById(id);
                JOptionPane.showMessageDialog(home, templateHtmlStart + "Contraseña eliminada." + templateHtmlEnd, "Eliminar contraseña", JOptionPane.PLAIN_MESSAGE, ok);
                cleanTable();
                tablePasswords();
            }
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Selecciona una contraseña." + templateHtmlEnd, "Eliminar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    public void tablePasswords() {
        modelo = (DefaultTableModel) home.getJtPasswords().getModel();
        passwordR.findAll().forEach(password -> {
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

    public void searchPassword() {
        String wordText = home.getTxtSearchPassword().getText();
        String word = wordText.trim();
        modelo = (DefaultTableModel) home.getJtPasswords().getModel();
        passwordR.findByDescriptionContainingIgnoreCase(word).forEach(passwords -> {
            Object[] item = new Object[5];
            item[0] = passwords.getIdPassword();
            item[1] = passwords.getEmail();
            item[2] = passwords.getPassword();
            item[3] = passwords.getDescription();
            item[4] = passwords.getDate();
            modelo.addRow(item);
        });
        home.getJtPasswords().setModel(modelo);
        home.getTxtSearchPassword().setText("");
        int rows = home.getJtPasswords().getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "No hay coincidencias." + templateHtmlEnd, "Buscar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            cleanTable();
            tablePasswords();
        }
    }

    public void cleanPasswords() {
        home.getTxtEmailPassword().setText("");
        home.getTxtIdPassword().setText("");
        home.getTxtNotePassword().setText("");
        home.getTxtSearchPassword().setText("");
        home.getTxtPasswordPassword().setText("");
        home.getBtnSavePassword().setEnabled(true);
    }

    //User
    public void saveUser() {
        String user = home.getTxtUserUser().getText();
        String password = home.getTxtPasswordUser().getText();
        String repeatPassword = home.getTxtRepeatPasswordUser().getText();
        if (!"".equals(user) && !"".equals(password) && !"".equals(repeatPassword)) {
            if (user.length() >= 3 && password.length() >= 9) {
                if (password.equals(repeatPassword)) {
                    int asnwer = JOptionPane.showConfirmDialog(home, templateHtmlStart + "¿Desea guardar este usuario?" + templateHtmlEnd, "Guardar usuario.", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
                    if (asnwer == 0) {
                        String passwordE = securityController.encryptPass(password);
                        LoginModel newUser = new LoginModel();
                        newUser.setName(user);
                        newUser.setPassword(passwordE);
                        loginR.save(newUser);
                        cleanTable();
                        tableUser();
                        cleanUser();
                        JOptionPane.showMessageDialog(home, templateHtmlStart + "Se guardo el usuario." + templateHtmlEnd, "Guardar usuario", JOptionPane.PLAIN_MESSAGE, ok);
                    } else {
                        JOptionPane.showMessageDialog(home, templateHtmlStart + "No se guardo el usuario." + templateHtmlEnd, "Guardar usuario", JOptionPane.PLAIN_MESSAGE, alert);
                    }
                } else {
                    JOptionPane.showMessageDialog(home, templateHtmlStart + "Las contraseñas son diferentes." + templateHtmlEnd, "Guardar usuario", JOptionPane.PLAIN_MESSAGE, alert);
                }
            } else {
                JOptionPane.showMessageDialog(home, templateHtmlStart + "Ups!!, Los campos deben tener:</h1><br><h2 style='color:#FFC6FF'>Usuario: 3 caracteres.</h2><br><h2 style='color:#FFC6FF'>Contraseña: 9 caracteres.</h2></html>", "Guardar usuario.", JOptionPane.PLAIN_MESSAGE, alert);
            }
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart + " Ups!!, llena todas las casillas </h1></html>\n" + templateHtmlStart + "~~Onegaishimasu Oniichan. </h1></html>", "Registrarse", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    public void tableUser() {
        modelo = (DefaultTableModel) home.getJtUsers().getModel();
        loginR.findAll().forEach(user -> {
            Object[] ob = new Object[2];
            ob[0] = user.getId();
            ob[1] = user.getName();
            modelo.addRow(ob);
        });
        home.getJtUsers().setModel(modelo);
    }

    public void deleteUser() {
        int row = home.getJtUsers().getSelectedRow();
        if (row != -1) {
            String user = home.getJtUsers().getValueAt(row, 1).toString();
            int answer = JOptionPane.showConfirmDialog(home, "<html><h1 style='font-size:18px;color:#cc8398;text-align:center'> ¿Deseas eliminar este usuario?" + "</h1><br><h2 align='center'>Usuario: " + user + "</h2></html>", "Eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, question);
            if (answer == 0) {
                int id = Integer.parseInt(home.getJtUsers().getValueAt(row, 0).toString());
                loginR.deleteById(id);
                cleanTable();
                tableUser();
                JOptionPane.showMessageDialog(home, templateHtmlStart + "Se elimino el usuario." + templateHtmlEnd, "Eliminar usuario", JOptionPane.PLAIN_MESSAGE, ok);
            } else {
                JOptionPane.showMessageDialog(home, templateHtmlStart + "No se elimino al usuario." + templateHtmlEnd, "Eliminar usuario", JOptionPane.PLAIN_MESSAGE, alert);
            }
        } else {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "Selecciona un usuario." + templateHtmlEnd, "Eliminar usuario", JOptionPane.PLAIN_MESSAGE, alert);
        }
    }

    public void searchUser() {
        cleanTable();
        String wordTxt = home.getTxtSearchUser().getText();
        String word = wordTxt.trim();
        loginR.findByNameContainingIgnoreCase(word).forEach(user -> {
            Object[] ob = new Object[2];
            ob[0] = user.getId();
            ob[1] = user.getName();
            modelo.addRow(ob);
        });
        home.getJtUsers().setModel(modelo);
        int rows = home.getJtUsers().getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(home, templateHtmlStart + "No hay coincidencias." + templateHtmlEnd, "Buscar contraseña", JOptionPane.PLAIN_MESSAGE, alert);
            cleanTable();
            tableUser();
        }
        home.getTxtSearchUser().setText("");
    }

    public void cleanUser() {
        home.getTxtUserUser().setText("");
        home.getTxtPasswordUser().setText("");
        home.getTxtRepeatPasswordUser().setText("");
    }

    //General
    public void cleanTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
