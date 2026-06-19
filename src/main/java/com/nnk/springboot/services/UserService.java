package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link User}.
 *
 * <p>Les mots de passe sont systématiquement encodés en BCrypt avant persistance,
 * que ce soit à la création ou à la mise à jour.</p>
 */
@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * @throws EntityNotFoundException si aucun {@link User} n'existe pour cet identifiant
     */
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    /** Encode le mot de passe avant de persister l'utilisateur. */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    /**
     * Met à jour un {@link User} existant en forçant son identifiant et en réencodant son mot de passe.
     *
     * @throws EntityNotFoundException si aucun {@link User} n'existe pour cet identifiant
     */
    public User update(Integer id, User user) {
        findById(id);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    /**
     * Vérifie l'existence de l'entité avant suppression.
     *
     * @throws EntityNotFoundException si aucun {@link User} n'existe pour cet identifiant
     */
    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}