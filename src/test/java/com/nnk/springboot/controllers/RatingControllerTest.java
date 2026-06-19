package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.services.RatingService;
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

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RatingController.class)
@WithMockUser
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService service;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void homeShouldDisplayList() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(new Rating("A", "A", "A", 1))
        );

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test

    void addFormShouldDisplayForm() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .param("moodysRating", "A")
                        .param("sandPRating", "A")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).save(any(Rating.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(new Rating("A", "A", "A", 1));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .with(csrf())
                        .param("moodysRating", "A")
                        .param("sandPRating", "A")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).update(eq(1), any(Rating.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).deleteById(1);
    }
}