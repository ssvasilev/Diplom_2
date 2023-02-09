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
        UserApi.createUser(testUserEmail, testUserName, testUserPassword);
    }


    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Проверка, что под пользователем можно авторизоваться")
    public  void restLoginUser() {
        Response response = UserApi.loginUser(testUserEmail,testUserPassword);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
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
    public void restDeleteUser(){UserApi.deleteUser(testUserEmail,testUserPassword);}

}
