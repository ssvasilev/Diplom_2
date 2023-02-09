import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import requests.BaseTest;
import requests.OrderApi;
import requests.UserApi;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetUserOrdersTest extends BaseTest {

    //Получения заказов авторизованного пользователя
    @Test
    @DisplayName("Получение заказов конкретного авторизованного пользователя")
    public  void getUserOrders(){
        //Создаём пользователя
        UserApi.createUser(testUserEmail, testUserName, testUserPassword);
        OrderApi.createOrder(testUserEmail, testUserPassword ,new String[]{"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75"});
        Response response = OrderApi.getOrder(testUserEmail,testUserPassword);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());
        //Удаляем пользователя
        UserApi.deleteUser(testUserEmail,testUserPassword);
    }

    //Попытка получения заказов неавторизованного пользователя
    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void getUserOrdersWithoutAuth(){
    Response response = OrderApi.getOrderWithoutAuth();
    response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteTestData(){
        UserApi.deleteUser(testUserEmail,testUserPassword);
    }
}
