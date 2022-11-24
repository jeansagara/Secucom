package com.secucom.spring.login.security.services;


import com.secucom.spring.login.models.User;
import com.secucom.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudServiceImpl implements CrudService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repositoryUsers;

    @Override
    public String Supprimer(Long id_users) {
        repositoryUsers.deleteById(id_users);
        return "Supprimer avec succes";
    }

    @Override
    public String Modifier(User users) {
        return repositoryUsers.findById(users.getId()).map(
                use -> {
                    use.setEmail(users.getEmail());
                    use.setUsername(use.getUsername());
                    use.setPassword(passwordEncoder.encode(users.getPassword()));

                    repositoryUsers.save(use);
                    return "Modification reussie avec succès";
                }
        ).orElseThrow(() -> new RuntimeException("Cet utilisateur n'existe pas"));
    }

    @Override
    public List<User> Afficher() {
        return repositoryUsers.findAll();

}
}