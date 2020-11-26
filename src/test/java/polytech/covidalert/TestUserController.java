package polytech.covidalert;



import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import polytech.covidalert.controllers.UserController;
import polytech.covidalert.models.User;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers = UserController.class)
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;



    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Test Get - User by email")
    public void userControllerShouldReturnUser() throws Exception{

        User user1= new User("jean","Neymar","20/08/2010",
                "jean@gmail.com","0645789654","azerty");
        doReturn(user1).when(userController).getUserByEmail(user1.getEmail());
        String result = mockMvc.perform(get("/covidalert/api/users/{id}",user1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.first_name", is(user1.getFirst_name())))
                .andExpect(jsonPath("$.last_name", is(user1.getLast_name())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }



    @Test
    @DisplayName("Test Create")
    public void testControllerCreateUser() throws Exception {

        User postUser = new User("jean", "Neymar", "20/08/2010", "jean@gmail.com", "0645789654", "azerty");
        //User mockUser= new User(18,"jean","Neymar","20/08/2010","jean23@gmail.com","0645789654","azerty");
        String result = mockMvc.perform(post("/covidalert/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postUser)))
                .andExpect(status().isCreated())

                // .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                //.andExpect(jsonPath("$.first_name", is(mockUser.getFirst_name())))
                //.andExpect(jsonPath("$.last_name", is(mockUser.getLast_name())))
                .andReturn().getResponse().getContentAsString();

        System.out.println("RESULTAT :" + result);
    }


    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");


    @Test
    @DisplayName("Test Unitaire la date doit avoir un format correct : YYYY-MM-DD, mois compris entre 01 et 12" +
            "jour compris entre 01 et 30-31, 28 pour février et 29 pour les années bissextile")
    public void testModelDateUserMoreThan1900() throws Exception{
        Matcher matcher = DATE_PATTERN.matcher("2020-02-29");
        assertTrue(matcher.matches());
    }


    @Test
    @DisplayName("Test Unitaire - le numéro de téléphone avec +33 ou 0 ")
    public void whenMatchesPhoneNumber_thenCorrect() {
        String patterns
                = "^((\\+)33|0)[1-9](\\d{2}){4}$";

        String[] validPhoneNumbers
                = {"0466498547", "+33652411452"};

        Pattern pattern = Pattern.compile(patterns);

        for(String phoneNumber : validPhoneNumbers) {
            Matcher matcher = pattern.matcher(phoneNumber);
            assertTrue(matcher.matches());
        }
    }



}
