/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelo.crud;

import com.example.demo.modelo.Account;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Usuario
 */
public interface AccountRepository extends CrudRepository<Account, Long>{
    
}