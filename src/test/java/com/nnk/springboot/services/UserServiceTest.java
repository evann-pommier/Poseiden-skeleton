package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;

    private BCryptPasswordEncoder passwordEncoder;

    private UserService service;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        service = new UserService(repository, passwordEncoder);
    }

    @Test
    void findAllShouldReturnAllUsers() {
        User user = user("john", "Password1!", "John Doe", "USER");
        when(repository.findAll()).thenReturn(List.of(user));

        assertEquals(List.of(user), service.findAll());
    }

    @Test
    void findByIdShouldReturnUserWhenFound() {
        User user = user("john", "Password1!", "John Doe", "USER");
        when(repository.findById(1)).thenReturn(Optional.of(user));

        assertSame(user, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldEncodePasswordBeforePersisting() {
        User user = user("john", "Password1!", "John Doe", "USER");
        when(repository.save(user)).thenReturn(user);

        User result = service.save(user);

        assertSame(user, result);
        assertNotEquals("Password1!", user.getPassword());
        assertTrue(passwordEncoder.matches("Password1!", user.getPassword()));
        verify(repository).save(user);
    }

    @Test
    void updateShouldCheckExistenceSetIdEncodePasswordAndSave() {
        User user = user("john", "Password1!", "John Doe", "USER");
        when(repository.findById(1)).thenReturn(Optional.of(user("old", "Oldpass1!", "Old User", "USER")));
        when(repository.save(user)).thenReturn(user);

        User result = service.update(1, user);

        assertSame(user, result);
        assertEquals(1, user.getId());
        assertNotEquals("Password1!", user.getPassword());
        assertTrue(passwordEncoder.matches("Password1!", user.getPassword()));
        verify(repository).save(user);
    }

    @Test
    void deleteByIdShouldCheckExistenceAndDelete() {
        when(repository.findById(1)).thenReturn(Optional.of(user("john", "Password1!", "John Doe", "USER")));

        service.deleteById(1);

        verify(repository).deleteById(1);
    }

    private User user(String username, String password, String fullname, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setRole(role);
        return user;
    }
}