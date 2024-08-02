/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoladorTest;

import com.example.demo.controlador.LoginController;
import com.example.demo.controlador.SecurityController;
import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Login;
import com.example.demo.vista.RegisterNewUser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Ruisu's
 */
@ExtendWith(MockitoExtension.class)
public class LoginTest {
    
    @Mock
    private LoginRepository loginR;
    
    @Mock
    private PasswordRepository passwordR;
    
    @Mock
    private Login login;
    
    @Mock
    private RegisterNewUser registerNewUser;
    
    @Mock
    private SecurityController securityC;
    
    @InjectMocks
    private LoginController loginC;
    
    
}
