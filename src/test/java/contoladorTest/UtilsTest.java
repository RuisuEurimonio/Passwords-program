package contoladorTest;

import com.example.demo.controlador.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ruisu's
 */
public class UtilsTest {
    
    @Test
    public void testValidEmail(){
        Assertions.assertTrue(Utils.isValidEmail("test@test.com"));
        Assertions.assertTrue(Utils.isValidEmail("tt@ttt.co"));
        Assertions.assertTrue(Utils.isValidEmail("-t@ttt.co"));
        Assertions.assertTrue(Utils.isValidEmail("-t.t@4tt.co"));
        Assertions.assertTrue(Utils.isValidEmail("test.test@t3st.com"));
        Assertions.assertTrue(Utils.isValidEmail("test.test@t3st.com.co"));
        Assertions.assertTrue(Utils.isValidEmail("test.test@t3st.edu.co"));
        Assertions.assertTrue(Utils.isValidEmail("test1111@t3st.com.co"));
        Assertions.assertTrue(Utils.isValidEmail("1111test1@t3st.com.co"));
        Assertions.assertTrue(Utils.isValidEmail("abc123.def456@ghi789.jk98.lm"));
    }
    
    @Test
    public void testInvalidEmail(){
        Assertions.assertFalse(Utils.isValidEmail("t@t.c"));
        Assertions.assertFalse(Utils.isValidEmail("t@t.co"));
        Assertions.assertFalse(Utils.isValidEmail("tt@t.co"));
        Assertions.assertFalse(Utils.isValidEmail("tt@t.co"));
        Assertions.assertFalse(Utils.isValidEmail("привет@t3st.com.co"));
        Assertions.assertFalse(Utils.isValidEmail("te$_t1111@t3st.com.co"));
        Assertions.assertFalse(Utils.isValidEmail("te$t1111@t3st.com.co"));
        Assertions.assertFalse(Utils.isValidEmail("1111+test1@t3st.com.co"));
        Assertions.assertFalse(Utils.isValidEmail("abc123.def456@ghi789.jk98.lm76"));
    }
    
}
