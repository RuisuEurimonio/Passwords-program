/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoladorTest;

import com.example.demo.controlador.HomeController;
import com.example.demo.controlador.SecurityController;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Home;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Ruisu's
 */
@ExtendWith(MockitoExtension.class)
public class HomeTest {
      
    @Mock
    private Home home;
    
    @Mock
    private PasswordRepository passwordR;
    
    @Mock
    private LoginRepository loginR;

    private static String user;
    private static String password;
    
    private HomeController homeC;
    
    @BeforeEach
    public void setUp() {
        
        user = "RuisuXaki";
        password = "291229122912";
        
        // Initialize HomeController after mocks are set up
        homeC = new HomeController(home, passwordR, loginR, user, password);
    }
    
    @Test
    public void testSavePasswordEmptyFields(){
        when(home.getTxtEmailPassword().getText()).thenReturn("");
        when(home.getTxtPasswordPassword().getText()).thenReturn("");
        when(home.getTxtNotePassword().getText()).thenReturn("");
        
        homeC.savePassword();
        
        verify(home, times(1)).getTxtEmailPassword().getText();
        verify(home, times(1)).getTxtPasswordPassword().getText();
        verify(home, times(1)).getTxtNotePassword().getText();
    }
    
}
