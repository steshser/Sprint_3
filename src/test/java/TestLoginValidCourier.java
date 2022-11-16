import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoginValidCourier {

    private CourierClient courierClient;
    private Courier courier;
    private ValidatableResponse responseLogin;


    @Before
    @Step("Prepare data to creating new valid courier")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getNewValidCourier();
    }

    @Test
    @Step("Create new valid courier and login")
    public void validCourierCanLogin(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        boolean isOkInMessageTrueCreate = responseCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeCreate);
        assertTrue(isOkInMessageTrueCreate);
        responseLogin = courierClient.login(CourierCredentials.from(courier));
        int actualStatusCodeLogin = responseLogin.extract().statusCode();
        int messageLogin = responseLogin.extract().path("id");
        assertEquals(200, actualStatusCodeLogin);
        assertNotNull(messageLogin);

    }

    @After
    @Step("Delete courier")
    public void cleanUp() {
        int id = responseLogin.extract().path("id");
        courierClient.delete(id);
    }
}
