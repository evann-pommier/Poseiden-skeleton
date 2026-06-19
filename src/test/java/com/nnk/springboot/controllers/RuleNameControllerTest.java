package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.services.RuleNameService;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;
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


@WebMvcTest(RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService service;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void homeShouldDisplayList() throws Exception {
        when(service.findAll()).thenReturn(List.of(new RuleName("Name","Description","{}","Template","SQL","Part")));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    void addFormShouldDisplayForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void validateShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .param("name", "Rule")
                        .param("description", "Description")
                        .param("json", "{}")
                        .param("template", "Template")
                        .param("sqlStr", "SELECT")
                        .param("sqlPart", "WHERE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).save(any(RuleName.class));
    }

    @Test
    void showUpdateFormShouldDisplayForm() throws Exception {
        when(service.findById(1)).thenReturn(ruleName());

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void updateShouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf())
                        .param("name", "Rule")
                        .param("description", "Description")
                        .param("json", "{}")
                        .param("template", "Template")
                        .param("sqlStr", "SELECT")
                        .param("sqlPart", "WHERE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).update(eq(1), any(RuleName.class));
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).deleteById(1);
    }

    @NonNull
    @Contract(value = " -> new", pure = true)
    private RuleName ruleName() {
        return new RuleName("Rule", "Description", "{}", "Template", "SELECT", "WHERE");
    }
}