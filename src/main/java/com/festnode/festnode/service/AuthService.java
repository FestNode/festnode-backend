package com.festnode.festnode.service;

import com.festnode.festnode.model.AppUser;
import com.festnode.festnode.repository.AppUserRepository;
import com.festnode.festnode.util.EmailSenderService;
import com.festnode.festnode.util.PasswordGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSender;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordGenerator passwordGenerator;
    public Object registerNewUser(AppUser userData) {
        AppUser newUser = new AppUser();
        AppUser checkUser = appUserRepository.findByEmail(userData.getEmail());
        AppUser checkUserByPhone = appUserRepository.findByPhone(userData.getPhone());
        if(checkUser != null || checkUserByPhone != null){
            return "Email or Phone number already exist!";
        }
        else{
            newUser.setName(userData.getName());
            newUser.setEmail(userData.getEmail());
            newUser.setPhone(userData.getPhone());
            newUser.setPassword(passwordEncoder.encode(userData.getPassword()));
            newUser.setRole(userData.getRole());
            newUser.setDesignation(userData.getDesignation()==null ? "User" : userData.getDesignation());
            appUserRepository.save(newUser);

            return newUser;
        }
    }

    public Object createNewUser(AppUser userData) {
        AppUser newUser = new AppUser();
        AppUser checkUser = appUserRepository.findByEmail(userData.getEmail());
        AppUser checkUserByPhone = appUserRepository.findByPhone(userData.getPhone());
        if(checkUser != null || checkUserByPhone != null){
            return "Email or Phone number already exist!";
        }
        else{
            String rawPassword = passwordGenerator.generateStrongPassword();
            System.out.println("Generated password is : "+ rawPassword);
            newUser.setName(userData.getName());
            newUser.setEmail(userData.getEmail());
            newUser.setPhone(userData.getPhone());
            newUser.setPassword(passwordEncoder.encode(rawPassword));
            newUser.setRole(userData.getRole());
            newUser.setDesignation(userData.getDesignation()==null ? "User" : userData.getDesignation());
            appUserRepository.save(newUser);
            try {
                String templatePath = "src/main/resources/templates/EmailTemaplates/accountCreation.html";

                // Read the file content
                String emailTemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

                // Replace placeholders with actual values
                String emailBody = emailTemplate
                        .replace("{{name}}", userData.getName())
                        .replace("{{email}}", userData.getEmail())
                        .replace("{{password}}", rawPassword)
                        .replace("{{loginUrl}}", "https://festnode.cit.ac.in/login");

                // Send the email
                emailSender.sendHtmlEmail(userData.getEmail(), "Account Created Successfully", emailBody);
            } catch (MessagingException e) {
                System.err.println("Error sending email: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return newUser;
        }
    }


    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }
}
