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
        UserApi.createUser(testUserEmail, testUserName, testUserPassword);
    }


    @Test
    @DisplayName("Изменение email и пароля у пользователя с авторизацией")
    @Description("Позитивный кейс")
    public void changeEmailAndPasswordUser(){
        Response response = UserApi.changeUserInfo(testUserEmail,testUserPassword, changeTestUserEmail, changeTestUserPassword);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Изменение email и пароля у пользователя без авторизации")
    @Description("Негативный кейс")
    public void changeEmailAndPasswordUserWithoutAuth(){
        Response response = UserApi.changeUserInfoWithoutAuth(changeTestUserEmail, changeTestUserPassword);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteTestData(){
        UserApi.deleteUser(testUserEmail,testUserPassword);
        UserApi.deleteUser(changeTestUserEmail,changeTestUserPassword);
    }

}
