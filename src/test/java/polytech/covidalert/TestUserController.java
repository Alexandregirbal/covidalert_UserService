package polytech.covidalert;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import polytech.covidalert.controllers.UserController;
import polytech.covidalert.models.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class)
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;


    @Test
    @DisplayName("Test Create")
    public void userControllerShouldReturnUser() throws Exception{

       User user1= new User("jean","Neymar","20/08/2010","jean@gmail.com","0645789654","azerty");
       doReturn(user1).when(userController).getUserByEmail(user1.getEmail());
       String result = mockMvc.perform(get("/covidalert/api/users/{id}",user1.getEmail()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email", is(user1.getEmail())))
               .andExpect(jsonPath("$.first_name", is(user1.getFirst_name())))
               .andExpect(jsonPath("$.last_name", is(user1.getLast_name())))
               .andReturn().getResponse().getContentAsString();
        System.out.println("\nResult:");
       System.out.println(result);

    }

}
