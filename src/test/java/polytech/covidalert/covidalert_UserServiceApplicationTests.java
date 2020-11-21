package polytech.covidalert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import polytech.covidalert.controllers.UserController;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
//@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/app-contest.xml"})
class covidalert_UserServiceApplicationTests {

	@Autowired
	private UserController controller;

	@Test
	public void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}
// test unitaire sur chaque partie controller, model , repo avec mock BDD fictive
	//test integration avec toutes couches chacune methodes
}
