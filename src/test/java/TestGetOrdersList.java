import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGetOrdersList {
    private OrderClient orderClient;


    @Before
    @Step("Prepare data to get orders list")
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Step("Get orders list without data")
    public void getOrdersList() {
        ValidatableResponse responseCreate = orderClient.get();
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        List<HashMap> orderBody = responseCreate.extract().path("orders");
        assertEquals(200, actualStatusCodeCreate);
        assertNotNull(orderBody);

    }
}
