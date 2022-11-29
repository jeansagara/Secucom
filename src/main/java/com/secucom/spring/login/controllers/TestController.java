package com.secucom.spring.login.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('COLLABORATEUR') or hasRole('ADMIN')")
  public String userAccess() {
    return "Bienvenu utilisateur.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('COLLABORATEUR')")
  public String moderatorAccess() {
    return "Bienvenu collaborateur.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Bienvenu Administrateur.";
  }
}
