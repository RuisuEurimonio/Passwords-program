/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelo.crud;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.modelo.LoginModel;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface LoginRepository extends CrudRepository<LoginModel, Integer> {

    Optional<LoginModel> findByNameAndPassword(String user, String password);
    
    Iterable<LoginModel> findByNameContainingIgnoreCase(String name);
}
