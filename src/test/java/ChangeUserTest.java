import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.BaseTest;
import requests.UserApi;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserTest extends BaseTest {

    @Before
    public void createTestData() {
        UserApi.createUser("autotestvasilevss@yandex.ru", "Сергей", "q1w2e3r4t5");
    }


    @Test
    @DisplayName("Изменение email и пароля у пользователя с авторизацией")
    @Description("Позитивный кейс")
    public void changeEmailAndPasswordUser(){
        Response response = UserApi.changeUserInfo("autotestvasilevss@yandex.ru","q1w2e3r4t5", "changeautotestvasilevss@yandex.ru", "12345");
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
        UserApi.deleteUser("changeautotestvasilevss@yandex.ru","12345");
    }

    @Test
    @DisplayName("Изменение email и пароля у пользователя без авторизации")
    @Description("Негативный кейс")
    public void changeEmailAndPasswordUserWithoutAuth(){
        Response response = UserApi.changeUserInfoWithoutAuth("changeautotestvasilevss@yandex.ru", "12345");
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
        UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
    }

}
