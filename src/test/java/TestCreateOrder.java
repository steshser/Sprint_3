import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    private OrderClient orderClient;
    private Order order;
    private int actualTruckNumber;

    private final List<String> color;

    public TestCreateOrder(List<String> color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }


    @Before
    @Step("Prepare data to creating order")
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getNewOrder(color);
    }

    @Test
    @Step("Create new order")
    public void orderCanBeCreated(){
        ValidatableResponse responseCreate = orderClient.create(order);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        actualTruckNumber = responseCreate.extract().path("track");
        assertEquals(201, actualStatusCodeCreate);
        assertNotNull(actualTruckNumber);
    }

    @After
    @Step("Cancel order")
    public void cleanUp() {
        orderClient.cancel(actualTruckNumber);
    }
}
