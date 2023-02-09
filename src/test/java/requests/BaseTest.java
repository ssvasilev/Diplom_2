package requests;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUp() {
        //Устанавливаем адрес сайта
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
    }

    public String testUserEmail = "autotestvasilevss@yandex.ru";
    public String testUserPassword = "q1w2e3r4t5";
    public String testUserName = "Сергей";

    public String changeTestUserEmail = "changeautotestvasilevss@yandex.ru";
    public String changeTestUserPassword = "12345";

}
