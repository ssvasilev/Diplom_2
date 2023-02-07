import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.UserApi;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {

    @Before
    public void createTestData() {
        UserApi.createUser("autotestvasilevss@yandex.ru", "Сергей", "q1w2e3r4t5");
    }


    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка, что под пользователем можно авторизоваться")
    public  void restLoginUser() {
        Response response = UserApi.loginUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @After
    public void restDeleteUser(){UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");}

}
