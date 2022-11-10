import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoginCourierWithoutData {
    private CourierClient courierClient;
    private Courier courier;
    private ValidatableResponse responseLoginForId;


    @Before
    @Step("Prepare data to creating new courier")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getNewValidCourier();
    }

    @Test
    @Description("при запросе без пароля сервер ответа не дает и падает по таймауту через 1 минуту (504Gateway time out)")
    @Step("Create courier without password")
    public void courierWithoutPasswordCanNotLogin(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        boolean isOkInMessageTrueCreate = responseCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeCreate);
        assertTrue(isOkInMessageTrueCreate);
        responseLoginForId = courierClient.login(CourierCredentials.from(courier));
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), null));
        int actualStatusCodeLogin = responseLogin.extract().statusCode();
        // при запросе без пароля сервер ответа не дает и падает по таймауту через 1 минуту (504Gateway time out)
        String messageLogin = responseLogin.extract().path("message");
        assertEquals(400, actualStatusCodeLogin);
        assertEquals("Недостаточно данных для входа",messageLogin);

    }

    @Test
    @Step("Create courier without login")
    public void courierWithoutLoginCanNotLogin(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        boolean isOkInMessageTrueCreate = responseCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeCreate);
        assertTrue(isOkInMessageTrueCreate);
        responseLoginForId = courierClient.login(CourierCredentials.from(courier));
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(null, courier.getPassword()));
        int actualStatusCodeLogin = responseLogin.extract().statusCode();
        String messageLogin = responseLogin.extract().path("message");
        assertEquals(400, actualStatusCodeLogin);
        assertEquals("Недостаточно данных для входа",messageLogin);

    }

    @Test
    @Step("Create with non-exciting pair of login and password")
    public void courierWithNotExistingLoginAndPasswordCanNotLogin(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        boolean isOkInMessageTrueCreate = responseCreate.extract().path("ok");
        assertEquals(201, actualStatusCodeCreate);
        assertTrue(isOkInMessageTrueCreate);
        responseLoginForId = courierClient.login(CourierCredentials.from(courier));
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin()+"1", courier.getPassword()+"1"));
        int actualStatusCodeLogin = responseLogin.extract().statusCode();
        String messageLogin = responseLogin.extract().path("message");
        assertEquals(404, actualStatusCodeLogin);
        assertEquals("Учетная запись не найдена",messageLogin);

    }

    @After
    @Step("Login and delete courier")
    public void cleanUp() {
        int id = responseLoginForId.extract().path("id");
        courierClient.delete(id);
    }
}
