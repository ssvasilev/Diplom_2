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

public class LoginUserTest extends BaseTest {

    @Before
    public void createTestData() {
        UserApi.createUser("autotestvasilevss@yandex.ru", "Сергей", "q1w2e3r4t5");
    }


    @Test
    @DisplayName("логин под существующим пользователем")
    @Description("Проверка, что под пользователем можно авторизоваться")
    public  void restLoginUser() {
        Response response = UserApi.loginUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    @Description("Проверка, нельзя авторизоваться под неверным логином и паролем")
    public  void restErrorPasswordFailLogin() {
        Response response = UserApi.loginUser("12345@yandex.ru","54321");
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void restDeleteUser(){UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");}

}
