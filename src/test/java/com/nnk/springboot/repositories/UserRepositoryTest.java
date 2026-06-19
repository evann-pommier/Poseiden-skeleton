package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsernameShouldReturnUserWhenExists() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("Password1!");
        user.setFullname("John Doe");
        user.setRole("USER");
        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("john");

        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
    }

    @Test
    void findByUsernameShouldReturnEmptyWhenNotFound() {
        Optional<User> result = userRepository.findByUsername("unknown");

        assertTrue(result.isEmpty());
    }
}