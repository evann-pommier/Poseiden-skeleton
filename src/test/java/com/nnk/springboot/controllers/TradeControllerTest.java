package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService service;

    @Test
    @WithMockUser
    void homeShouldDisplayList() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(new Trade("Account", "Type"))
        );

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser
    void addFormShouldDisplayForm() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).save(any(Trade.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(new Trade("Account", "Type"));

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .with(csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).update(eq(1), any(Trade.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).deleteById(1);
    }
}