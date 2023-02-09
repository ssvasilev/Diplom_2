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


public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Так же покрывает кейс создания заказа с инридиентами")
    public void createOrder(){
        //Создаём пользователя
        UserApi.createUser(testUserEmail, testUserName, testUserPassword);
        //Создаём заказ под пользователем
        Response response = OrderApi.createOrder(testUserEmail, testUserPassword ,new String[]{"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75"});
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK)
                .and()
                .body("order.ingredients", notNullValue())
                .and()
                .body("name", equalTo("Антарианский бессмертный флюоресцентный бургер"))
                .and()
                .body("order.owner.email", equalTo(testUserEmail));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorization(){
        Response response = OrderApi.createOrderWithoutAuthorization(new String[]{"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75"});
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK)
                .and()
                .body("name", equalTo("Антарианский бессмертный флюоресцентный бургер"));
    }

    @Test
    @DisplayName("Создание заказа без инридиентов")
    public void createOrderWithoutIngredients(){
        Response response = OrderApi.createOrderWithoutAuthorization(new String[]{});
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWitchErrorId(){
        Response response = OrderApi.createOrderWithoutAuthorization(new String[]{"123","321","fff"});
        response.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteTestData(){
        UserApi.deleteUser(testUserEmail,testUserPassword);
    }

}
