package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static requests.UserApi.loginUserAccessToken;

public class OrderApi {
    private static final  String ORDER_ENDPOINT = "/api/orders/";

    @Step("Отправляем POST-запрос создания заказа с авторизацией")
    public static Response createOrder(String email, String password, String[]ingredients){
        String userAccessToken = loginUserAccessToken(email,password);
        Order order = new Order(ingredients);
        Response response=
                given()
                        .header("Authorization", userAccessToken)
                        .header("Content-type", "application/json")
                        .body(order)
                        .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Отправляем POST-запрос создания заказа без авторизации")
    public static Response createOrderWithoutAuthorization(String[]ingredients){
        Order order = new Order(ingredients);
        Response response=
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Отправляем GET-запрос получения заказов пользователя")
    public static Response getOrder(String email, String password){
        String userAccessToken = loginUserAccessToken(email,password);
        Response response=
        given()
                .header("Authorization", userAccessToken)
                .header("Content-type", "application/json")
                .get(ORDER_ENDPOINT);
        return response;
    }

    @Step("Отправляем GET-запрос получения заказов неавторизованного")
    public static Response getOrderWithoutAuth(){
        Response response=
                given()
                        .header("Content-type", "application/json")
                        .get(ORDER_ENDPOINT);
        return response;
    }
}
