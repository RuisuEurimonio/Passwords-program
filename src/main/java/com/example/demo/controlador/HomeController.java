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

    private SecurityController securityController;
    private DefaultTableModel modelo;
    private Home home;
    private ValidationPassword validationPassword;
    private LoginRepository loginR;
    private PasswordRepository passwordR;
    private Utils validations;

    private String user;
    private String passwordLogin;

    public HomeController(Home home, PasswordRepository passwordR, LoginRepository loginR, String user, String password) {
        this.home = home;
        this.passwordR = passwordR;
        this.user = user;
        this.loginR = loginR;
        this.passwordLogin = password;
        this.securityController = new SecurityController();
        this.validationPassword = new ValidationPassword(home, true);
        this.modelo = new DefaultTableModel();
        
        initializeOtherResources();
    }
    
    private void initializeOtherResources(){
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
        Object source = event.getSource();
        if (source == home.getBtnSelectPassword()) {
            handleSelectPassword();
        } else if (source == home.getBtnSelectUsers()) {
            handleSelectUser();
        } else if (source == home.getBtnSelectConfig()) {
            handleSelectConfig();
        } else if (source == home.getBtnCleanPassword()) {
            cleanPasswords();
        } else if (source == home.getBtnSavePassword()) {
            savePassword();
        } else if (source == home.getBtnUpdatePassword()) {
            updatePassword();
        } else if (source == validationPassword.getBtnPasswordValidation()
                || source == validationPassword.getTxtPasswordValidation()) {
            validationPassword.dispose();
        } else if (source == home.getBtnDeletePassword()) {
            deletePassword();
        } else if (source == home.getBtnSearchPassword()
                || source == home.getTxtSearchPassword()) {
            handleSearchPassword();
        } else if (source == home.getBtnNewUser()) {
            saveUser();
        } else if (source == home.getBtnCleanUser()) {
            cleanUser();
        } else if (source == home.getBtnDeleteUser()) {
            deleteUser();
        } else if (source == home.getBtnSearchUser()
                || source == home.getTxtSearchUser()) {
            searchUser();
        }
    }

    public void handleSelectPassword() {
        home.getJtpInputs().setSelectedIndex(0);
        home.getJtpTables().setSelectedIndex(0);
        cleanTable();
        tablePasswords();
        cleanPasswords();
    }

    public void handleSelectUser() {
        home.getJtpInputs().setSelectedIndex(1);
        home.getJtpTables().setSelectedIndex(1);
        cleanTable();
        tableUser();
        cleanUser();
    }

    public void handleSelectConfig() {
        home.getJtpInputs().setSelectedIndex(2);
        home.getJtpTables().setSelectedIndex(2);
    }

    public void handleSearchPassword() {
        cleanTable();
        searchPassword();
    }

    //Password
    public void savePassword() {
        String email = home.getTxtEmailPassword().getText();
        String password = home.getTxtPasswordPassword().getText();
        String addition = home.getTxtNotePassword().getText();

        if ("".equals(email) && "".equals(password) && "".equals(addition)) {
            Utils.handleEmptyInputsMessage(home, "Guardar contraseña");
            return;
        }

        if (!validations.isValidEmail(email)) {
            Utils.handleWrongEmailFormatMessage(home);
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

            int answer = Utils.handleActionQuestionMessage(home, "¿Deseas guardar esta contraseña?", "Guardar contraseña.");
            if (answer != 0) {
                cleanPasswords();
                Utils.handleCancelActionMessage(home, "Contraseña no guardada.", "Guardar contraseña");
                return;
            }

            passwordR.save(passwordModel);
            cleanPasswords();
            cleanTable();
            tablePasswords();

            Utils.handleSuccessActionMessage(home, "Contraseña guardada.", "Guardar contraseña");

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
            Utils.handleEmptyInputMessage(home);
            return;
        }

        String passwordTypedEncrypted = securityController.encryptPass(passwordTyped);

        if (!(passwordTypedEncrypted.equals(passwordLogin))) {
            Utils.handleWrongPasswordMessage(home);
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
            Utils.handleNotRowSelectedMessage(home, "Selecciona una contraseña.", "Actualizar contraseña");
            return;
        }

        int rowSelected = home.getJtPasswords().getSelectedRow();

        PasswordModel passwordSelected = tableDatas(rowSelected);
        String passwordInputEncrypt = securityController.encryptPass(home.getTxtPasswordPassword().getText());

        if (passwordSelected.getEmail().equals(home.getTxtEmailPassword().getText())
                || passwordSelected.getPassword().equals(passwordInputEncrypt)
                || passwordSelected.getDescription().equals(home.getTxtNotePassword().getText())) {
            Utils.handleNotChangeDataMessage(home);
            home.getBtnSavePassword().setEnabled(true);
            cleanPasswords();
            return;
        }

        int answer = Utils.handleActionQuestionMessage(home, "¿Deseas actualizar esta contraseña?", "Actualizar contraseña");
        if (answer != 0) {
            Utils.handleCancelActionMessage(home, "No se ha actualizado la contraseña.", "Actualizar contraseña");
            home.getBtnSavePassword().setEnabled(true);
            cleanPasswords();
            return;
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

        Utils.handleSuccessActionMessage(home, "Contraseña actualizada.", "Actualizar contraseña");

        cleanPasswords();
        cleanTable();
        tablePasswords();
        home.getBtnSavePassword().setEnabled(true);
    }

    public void deletePassword() {
        int selecterPassword = home.getJtPasswords().getSelectedRow();
        if (selecterPassword == -1) {
            Utils.handleNotRowSelectedMessage(home, "Selecciona una contraseña.", "Eliminar contraseña");
        }

        String email = home.getJtPasswords().getValueAt(selecterPassword, 1).toString();
        String description = home.getJtPasswords().getValueAt(selecterPassword, 3).toString();

        int answer = Utils.handleCustomQuestionUserMessage(home, email, description);
        if (answer == 0) {
            int id = Integer.parseInt(home.getJtPasswords().getValueAt(selecterPassword, 0).toString());
            passwordR.deleteById(id);
            Utils.handleSuccessActionMessage(home, "Contraseña eliminada.", "Eliminar contraseña");
            cleanTable();
            tablePasswords();
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
            Utils.handleNotCoincidenceSearchMessage(home, "Buscar contraseña");
            cleanTable();
            tablePasswords();
            return;
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
        String titleMessage = "Guardar usuario.";
        
        String user = home.getTxtUserUser().getText();
        String password = home.getTxtPasswordUser().getText();
        String repeatPassword = home.getTxtRepeatPasswordUser().getText();

        if ("".equals(user) && "".equals(password) && "".equals(repeatPassword)) {
            Utils.handleEmptyInputsMessage(home, titleMessage);
            return;
        }

        if (user.length() < 3 && password.length() < 9) {
            Utils.handleNotValidDataMessage(home, titleMessage);
            return;
        }
        if (!(password.equals(repeatPassword))) {
            Utils.handleNotEqualsPasswordsMessage(home, titleMessage);
            return;
        }

        int asnwer = Utils.handleActionQuestionMessage(home, "¿Desea guardar este usuario?", titleMessage);
        if (asnwer != 0) {
            Utils.handleCancelActionMessage(home, "No se guardo el usuario.", titleMessage);
            return;
        }

        String passwordEncode = securityController.encryptPass(password);
        LoginModel newUser = new LoginModel();
        newUser.setName(user);
        newUser.setPassword(passwordEncode);
        loginR.save(newUser);
        cleanTable();
        tableUser();
        cleanUser();
        Utils.handleSuccessActionMessage(home, "Se guardo el usuario.", titleMessage);
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
        String titleMessage = "Eliminar usuario";
        
        int selectedUser = home.getJtUsers().getSelectedRow();
        if (selectedUser == -1) {
            Utils.handleNotRowSelectedMessage(home, "Selecciona un usuario.", titleMessage);
            return;
        }

        String user = home.getJtUsers().getValueAt(selectedUser, 1).toString();

        int answer = Utils.handleCustomQuestionUserMessage(home, user);
        if (answer != 0) {
            Utils.handleCustomQuestionUserMessage(home, user);
            return;
        }

        int id = Integer.parseInt(home.getJtUsers().getValueAt(selectedUser, 0).toString());
        loginR.deleteById(id);
        cleanTable();
        tableUser();
        Utils.handleSuccessActionMessage(home, "Se elimino el usuario.", titleMessage);
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
            Utils.handleNotCoincidenceSearchMessage(home, "Buscar contraseña");
            cleanTable();
            tableUser();
            return;
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
