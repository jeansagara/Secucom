package com.secucom.spring.login.controllers;


import com.secucom.spring.login.models.User;
import com.secucom.spring.login.security.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class CrudController {
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private CrudService crudService;


    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/afficher")
    public  List<User> AfficherUsers(){
        return crudService.Afficher();
    }

    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ   MODIFIER
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier"})
    public String ModierUser(@RequestBody User users){

        crudService.Modifier(users);
        return "Modification reussie avec succès";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{id_users}")
    public String Supprimer(@PathVariable("id_users") Long id_users){
        crudService.Supprimer(id_users);
        return "Suppression reussie";
    }
}
