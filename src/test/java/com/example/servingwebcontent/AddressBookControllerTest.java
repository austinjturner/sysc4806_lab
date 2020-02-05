package com.example.servingwebcontent;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void getAddressBookEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/address-book")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    @Order(2)
    public void createAddressBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/address-book")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"buddyInfos\":[]}")));
    }

    @Test
    @Order(3)
    public void getAddressBookNotEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/address-book/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"buddyInfos\":[]}")));
    }

    @Test
    @Order(4)
    public void addBuddyToAddressBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/address-book/1/buddy")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Steve\",\"phonenumber\":\"12345\",\"address\":\"1 big street\",\"age\": 50,\"ranking\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(
                        "{\"name\":\"Steve\",\"phonenumber\":\"12345\",\"address\":\"1 big street\",\"age\":50,\"id\":2,\"ranking\":1,\"over18\":true}")));
    }

    @Test
    @Order(5)
    public void getAddressBookWithBuddy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/address-book/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(
                        "{\"id\":1,\"buddyInfos\":[{\"name\":\"Steve\",\"phonenumber\":\"12345\",\"address\":\"1 big street\",\"age\":50,\"id\":2,\"ranking\":1,\"over18\":true}]}"
                )));
    }

}
