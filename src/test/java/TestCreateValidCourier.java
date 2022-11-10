import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCreateValidCourier {

    private CourierClient courierClient;
    private Courier courier;


    @Before
    @Step("Prepare data to creating new valid courier")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getNewValidCourier();
    }

    @Test
    @Step("Create new valid courier")
    public void courierCanBeCreated(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        boolean isOkInMessageTrueCreate = responseCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeCreate);
        assertTrue(isOkInMessageTrueCreate);
    }

    @After
    @Step("Login and delete courier")
    public void cleanUp() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        int id = responseLogin.extract().path("id");
        courierClient.delete(id);
    }

}
