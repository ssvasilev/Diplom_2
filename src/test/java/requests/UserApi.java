package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class UserApi {

    private final static String USER_REGISTER_ENDPOINT = "/api/auth/register/";
    private final static String USER_LOGIN_ENDPOINT = "/api/auth/login/";
    private final static String USER_ENDPOINT = "/api/auth/user/";


    //Создание Пользователя
    @Step("Отправляем POST-запрос создания пользователя")
    public static Response createUser(String email, String name, String password) {
        User user = new User(email,name, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(user)
                        .post(USER_REGISTER_ENDPOINT);
        return  response;
    }

    @Step("Отправляем POST-запрос создания пользователя без обязательного поля \"Имя\"")
    public static Response createUser(String email, String password) {
        User user = new User(email, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(user)
                        .post(USER_REGISTER_ENDPOINT);
        return  response;
    }

    //Авторизация пользователем
    @Step("Отправляем POST-запрос авторизации пользователя")
    public static Response loginUser(String email, String password) {
        User user = new User(email,password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(user)
                        .post(USER_LOGIN_ENDPOINT);
        return  response;
    }


    //Логин пользователем для получения токена
    @Step("Отправляем POST-запрос авторизации пользователя для получения ID")
    public static String loginUserAccessToken(String email, String password) {
        User user = new User(email, password);
        UserToken userResponce =
                //Логин курьером, что бы получить его id
                given()
                        .header("Content-type", "application/json")
                        .body(user)
                        .post(USER_LOGIN_ENDPOINT)
                        .as(UserToken.class);
        return Objects.requireNonNullElse(userResponce.accessToken, "Empty token, fail authorization");
    }

    //Изменение данных о пользователе
    @Step("Отправляем PATCH-запрос для изменения данных о пользователе")
    public static Response changeUserInfo(String email, String password, String newEmail, String newPassword){
        String userAccessToken = loginUserAccessToken(email,password);
        User user = new User (newEmail, newPassword);
        Response responce =
        given()
                .header("Content-type", "application/json")
                .header("Authorization", userAccessToken)
                .body(user)
                .patch(USER_ENDPOINT);
        return responce;
    }

    //Изменение данных о пользователе
    @Step("Отправляем PATCH-запрос для изменения данных о пользователе")
    public static Response changeUserInfoWithoutAuth(String newEmail, String newPassword){
        User user = new User (newEmail, newPassword);
        Response responce =
                given()
                        .header("Content-type", "application/json")
                        .body(user)
                        .patch(USER_ENDPOINT);
        return responce;
    }

    @Step("Отправляет DELETE-запрос удаления пользователя")
    public static void  deleteUser(String email, String password){
        String userAccessToken = loginUserAccessToken(email,password);
            given()
                    .header("Authorization", userAccessToken)
                    .delete(USER_ENDPOINT);
    }

}