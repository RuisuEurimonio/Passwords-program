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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
    
    @Mock
    private SecurityController securityC;
    
    private static String user;
    private static String password;
    
    @InjectMocks
    private HomeController homeC;
    
    @BeforeEach
    public void setUp(){
        user = "RuisuXaki";
        password = "291229122912";
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
