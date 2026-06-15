package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc(addFilters = false)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService service;

    @Test
    @WithMockUser
    void homeShouldDisplayList() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(new BidList("Account", "Type", 10d))
        );

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));
    }

    @Test
    @WithMockUser
    void addFormShouldDisplayForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).save(any(BidList.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(new BidList("Account", "Type", 10d));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).update(eq(1), any(BidList.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).deleteById(1);
    }
}