/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelo.crud;


import com.example.demo.modelo.PasswordModel;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Usuario
 */
public interface PasswordRepository extends CrudRepository<PasswordModel, Integer>  {
    
    Iterable<PasswordModel> findByDescriptionContainingIgnoreCase(String word);
    
}
