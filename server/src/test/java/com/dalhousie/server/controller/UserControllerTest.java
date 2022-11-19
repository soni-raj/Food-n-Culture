package com.dalhousie.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dalhousie.server.AbstractTest;
import com.dalhousie.server.model.User;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest extends AbstractTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    private User getUser() {
        User user = new User();
        user.setUserName(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setVerified(false);
        user.setStatus("created");
        user.setUpdatedAt("2022-11-17 00:00:00");
        user.setCreatedAt("2022-11-17 00:00:00");
        return user;
    }

    @Test
    @Order(1)
    public void createUserTest() throws Exception {
        String uri = "/api/users/";
        User user = getUser();
        user.setId(99);
        String inputJson = super.mapToJson(user);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(201, status);
        String content = result.getResponse().getContentAsString();
        assertEquals("User created successfully", content);
    }

    @Test
    @Order(2)
    public void getAllUsersTest() throws Exception {
        String uri = "/api/users/";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        User[] userlist = super.mapFromJson(content, User[].class);
        assertTrue(userlist.length >= 0);
    }

    @Test
    @Order(3)
    public void getUserTest() throws Exception {
        String uri = "/api/users/99";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Order(4)
    public void getUserNotFoundTest() throws Exception {
        String uri = "/api/users/999";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    @Order(5)
    public void updateUserTest() throws Exception {
        String createUri = "/api/users/";
        User createUser = getUser();
        createUser.setId(88);
        String inputCreateJson = super.mapToJson(createUser);
        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post(createUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputCreateJson)).andReturn();
        int createStatus = createResult.getResponse().getStatus();
        assertEquals(201, createStatus);

        String uri = "/api/users/88";
        User user = getUser();
        user.setId(88);
        String inputJson = super.mapToJson(user);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        assertEquals("User updated successfully", content);
    }

    @Test
    @Order(6)
    public void updateUserWhenNotFoundTest() throws Exception {
        String uri = "/api/users/999";
        User user = getUser();
        String inputJson = super.mapToJson(user);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    @Order(7)
    @AfterTestClass
    public void deleteUserTest() throws Exception {
        String uri = "/api/users/99";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        assertEquals("User deleted successfully", content);

        String deleteUri = "/api/users/88";
        result = mvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
        status = result.getResponse().getStatus();
        assertEquals(200, status);
        content = result.getResponse().getContentAsString();
        assertEquals("User deleted successfully", content);
    }

}
