/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoladorTest;

import com.example.demo.controlador.HomeController;
import com.example.demo.controlador.SecurityController;
import com.example.demo.controlador.Validations;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Home;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author Ruisu's
 */
@RunWith(MockitoJUnitRunner.class)
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
    
    
}
