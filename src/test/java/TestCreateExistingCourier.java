import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCreateExistingCourier {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    @Step("Prepare data to creating new valid courier")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getNewValidCourier();

    }

    @Test
    @Step("Create new valid courier and new one with same data")
    public void existingCourierCanNotBeCreated(){
        courier = CourierGenerator.getNewValidCourier();
        ValidatableResponse responseFirstCreate = courierClient.create(courier);
        int actualStatusCodeFirstCreate = responseFirstCreate.extract().statusCode();
        boolean isOkInMessageTrueFirstCreate = responseFirstCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeFirstCreate);
        assertTrue(isOkInMessageTrueFirstCreate);
        ValidatableResponse responseSecondCreate = courierClient.create(courier);
        int actualStatusCodeSecondCreate = responseSecondCreate.extract().statusCode();
        String messageOfSecondCreate = responseSecondCreate.extract().path("message");
        assertEquals(409, actualStatusCodeSecondCreate);
        assertEquals("Этот логин уже используется. Попробуйте другой.", messageOfSecondCreate);

    }

    @After
    @Step("Login and delete courier")
    public void cleanUp() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        int id = responseLogin.extract().path("id");
        courierClient.delete(id);
    }

}
