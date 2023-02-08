import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import requests.BaseTest;
import requests.UserApi;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BaseTest {


        @Test
        @DisplayName("Создание уникального пользователя") // имя теста
        @Description("успешный запрос возвращает success: true") // описание теста
        public void restCreateUser() {
            Response response = UserApi.createUser("autotestvasilevss@yandex.ru","Сергей","q1w2e3r4t5");
            response.then().assertThat().body("success", equalTo(true))
                    .and()
                    .statusCode(SC_OK);
            UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
        }

        @Test
        @DisplayName("Создать пользователя, который уже зарегистрирован") // имя теста
        @Description("Проверка, что нельзя создать пользователей с двумя одинаковыми email") // описание теста
        public void restDoubleCreateUser() {
            UserApi.createUser("autotestvasilevss@yandex.ru","Сергей","q1w2e3r4t5");
            Response responseSecondUser = UserApi.createUser("autotestvasilevss@yandex.ru","Сергей","q1w2e3r4t5");
            responseSecondUser.then().assertThat().body("success", equalTo(false))
                    .and()
                    .assertThat().body("message", equalTo("User already exists"))
                    .and()
                    .statusCode(SC_FORBIDDEN);
            UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
        }

        @Test
        @DisplayName("Создать пользователя и не заполнить одно из обязательных полей") // имя теста
        @Description("проверка, что для создания пользователя все поля обязательны") // описание теста
        public void restAllFieldsAreRequired() {
            Response response = UserApi.createUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
            response.then().assertThat().body("success", equalTo(false))
                    .and()
                    .assertThat().body("message", equalTo("Email, password and name are required fields"))
                    .and()
                    .statusCode(SC_FORBIDDEN);
        }


    }

