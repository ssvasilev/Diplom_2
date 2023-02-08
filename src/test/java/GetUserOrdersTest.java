import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
        UserApi.createUser("autotestvasilevss@yandex.ru", "Сергей", "q1w2e3r4t5");
        OrderApi.createOrder("autotestvasilevss@yandex.ru", "q1w2e3r4t5" ,new String[]{"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75"});
        Response response = OrderApi.getOrder("autotestvasilevss@yandex.ru","q1w2e3r4t5");
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());
        //Удаляем пользователя
        UserApi.deleteUser("autotestvasilevss@yandex.ru","q1w2e3r4t5");
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
}
