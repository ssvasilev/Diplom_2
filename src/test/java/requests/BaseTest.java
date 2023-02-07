package requests;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUp() {
        //Устанавливаем адрес сайта
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
    }

}
