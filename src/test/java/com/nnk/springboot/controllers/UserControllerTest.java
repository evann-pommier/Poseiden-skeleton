package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void homeShouldDisplayList() throws Exception {

        User user = new User();
        user.setUsername("john");

        when(service.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void addFormShouldDisplayForm() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "Password1!")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service).save(any(User.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(user());

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "Password1!")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service).update(eq(1), any(User.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service).deleteById(1);
    }

    private User user() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("Password1!");
        user.setFullname("John Doe");
        user.setRole("USER");
        return user;
    }
}