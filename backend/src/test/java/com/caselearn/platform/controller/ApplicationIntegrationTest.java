package com.caselearn.platform.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    @Test
    void publicCourseCatalogueIsSeeded() throws Exception {
        mvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].title", not(emptyString())))
                .andExpect(jsonPath("$[0].questions", hasSize(0)));
    }

    @Test
    void studentCanLoginEnrollAndSeeDashboard() throws Exception {
        String login = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"student@learnsphere.demo\",\"password\":\"Demo@123\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token", not(emptyString())))
                .andReturn().getResponse().getContentAsString();
        String token = json.readTree(login).get("token").asText();

        String courses = mvc.perform(get("/api/courses").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JsonNode list = json.readTree(courses);
        long courseId = list.get(1).get("id").asLong();

        mvc.perform(post("/api/courses/{id}/enroll", courseId).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.enrolled").value(true));
        mvc.perform(get("/api/dashboard").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.enrollments", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void protectedDashboardRejectsAnonymousRequest() throws Exception {
        mvc.perform(get("/api/dashboard")).andExpect(status().isForbidden());
    }
}
