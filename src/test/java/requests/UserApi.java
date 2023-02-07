package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

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
                given().log().all()
                        .header("Content-type", "application/json")
                        .body(user)
                        .post(USER_REGISTER_ENDPOINT);
        return  response;
    }

    @Step("Отправляем POST-запрос создания пользователя без обязательного поля \"Имя\"")
    public static Response createUser(String email, String password) {
        User user = new User(email, password);
        Response response =
                given().log().all()
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
                given().log().all()
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
        return userResponce.accessToken;
    }

    @Step("Отправляет DELETE-запрос удаления пользователя")
    public static void  deleteUser(String email, String password){
        String userAccessToken = loginUserAccessToken(email,password);
            given()
                    .header("Authorization", userAccessToken)
                    .delete(USER_ENDPOINT);
    }



    public static class UserToken {
        public String accessToken;
        public String getAccessToken(){
            return  accessToken;
        }
        public  void setAccessToken(){
            this.accessToken = accessToken;
        }
        public String refreshToken;
        public String getRefreshToken(){
            return  refreshToken;
        }
        public  void setRefreshToken(){
            this.refreshToken = refreshToken;
        }

    }


}