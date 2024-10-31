package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Optional;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.Users;

@Service
public class AuthService {

    @Autowired
    private UserRepository UserRepository;

    public boolean authenticate(String email, String password) throws NoSuchAlgorithmException {
        Optional<Users> UserssOpt = UserRepository.findByEmail(email);

        if (UserssOpt.isPresent()) {
            Users Userss = UserssOpt.get();

            // Comparar la contraseña cifrada
            return Userss.getPassword().equals(password);
        }
        return false;
    }



    public void registerUser(String email, String password,String name, Integer ci, Integer age, String surname, String role) throws NoSuchAlgorithmException {

        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setCI(ci);
        newUser.setRole(role);
        newUser.setAge(age);
        newUser.setSurname(surname);
        UserRepository.save(newUser);
    }
}
