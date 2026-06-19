package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void unauthenticatedUserShouldBeRedirectedToLogin() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    void loginShouldSucceedWithValidCredentials() throws Exception {
        User user = new User();
        user.setUsername("authuser");
        user.setPassword(passwordEncoder.encode("Password1!"));
        user.setFullname("Auth User");
        user.setRole("USER");
        userRepository.save(user);

        mockMvc.perform(post("/app/login")
                        .with(csrf())
                        .param("username", "authuser")
                        .param("password", "Password1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    void loginShouldFailWithInvalidCredentials() throws Exception {
        mockMvc.perform(post("/app/login")
                        .with(csrf())
                        .param("username", "unknown")
                        .param("password", "WrongPassword1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error=true"));
    }
}