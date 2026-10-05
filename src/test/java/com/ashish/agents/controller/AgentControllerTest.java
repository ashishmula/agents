package com.ashish.agents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createThenFetchAgent() throws Exception {
        mockMvc.perform(post("/api/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ravi\",\"role\":\"support\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ravi"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        mockMvc.perform(get("/api/agents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("support"));
    }

    @Test
    void unknownAgentReturns404() throws Exception {
        mockMvc.perform(get("/api/agents/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Agent not found: 999"));

        mockMvc.perform(delete("/api/agents/999"))
                .andExpect(status().isNotFound());
    }
}
