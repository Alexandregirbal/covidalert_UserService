package polytech.covidalert;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import polytech.covidalert.controllers.UserController;
import polytech.covidalert.models.User;
import polytech.covidalert.models.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@WebMvcTest(UserController.class)
public class TestUserController {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @MockBean
    private UserRepository userRepository;


    @Test
    @DisplayName("Test Create")
    public void userControllershouldReturnUser() throws Exception{

       User user1= new User("jean","Neymar","20/08/2010","jeanmrlzefzefezf@gmail.com","0645789654","azerty");
       doReturn(user1).when(userRepository).save(any());

       User returnedUser = userController.create(user1);
        System.out.println(user1);
        System.out.println(returnedUser);

       userController.create(user1);
       Assertions.assertEquals(user1, returnedUser, "Create should return the user");
    }

}
