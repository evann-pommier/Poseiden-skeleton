package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.services.CurveService;
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

@WebMvcTest(CurveController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurveService service;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void homeShouldDisplayList() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(new CurvePoint(10, 2d, 30d))
        );

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    void addFormShouldDisplayForm() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .param("curveId", "10")
                        .param("term", "2")
                        .param("value", "30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).save(any(CurvePoint.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(new CurvePoint(10, 2d, 30d));

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf())
                        .param("curveId", "10")
                        .param("term", "2")
                        .param("value", "30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).update(eq(1), any(CurvePoint.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).deleteById(1);
    }
}