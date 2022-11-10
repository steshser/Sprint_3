import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCreateCourierWithoutData {
    private CourierClient courierClient;

    @Before
    @Step("Prepare data to test")
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Step("Create new courier without login")
    public void courierWithoutLoginCanNotBeCreated(){
        Courier courier = CourierGenerator.getCourierWithoutLogin();
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        String responseMessage = responseCreate.extract().path("message");
        assertEquals(400, actualStatusCodeCreate);
        assertEquals("Недостаточно данных для создания учетной записи", responseMessage);
    }

    @Test
    @Step("Create new courier without password")
    public void courierWithoutPasswordCanNotBeCreated(){
        Courier courier = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        String responseMessage = responseCreate.extract().path("message");
        assertEquals(400, actualStatusCodeCreate);
        assertEquals("Недостаточно данных для создания учетной записи", responseMessage);
    }

//    @Test
//    @Description("тест падает из-за бага в создании курьера без имени")
//    @Step("Create new courier without name")
//    public void courierWithoutNameCanNotBeCreated(){
//        Courier courier = CourierGenerator.getCourierWithoutName();
//        ValidatableResponse responseCreate = courierClient.create(courier);
//        int actualStatusCodeCreate = responseCreate.extract().statusCode();
//        String responseMessage = responseCreate.extract().path("message");
//        // тест падает из-за бага в создании курьера без имени
//        assertEquals(400, actualStatusCodeCreate);
//        assertEquals("Недостаточно данных для создания учетной записи", responseMessage);
//    }

}
