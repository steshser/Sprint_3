import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private static final String CREATE_PATH = "/api/v1/orders";
    private static final String CANCEL_PATH = "/api/v1/orders/cancel";

    public ValidatableResponse create(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(CREATE_PATH)
                .then();

    }

    public ValidatableResponse cancel(int track) {
        Gson cancelGson = new GsonBuilder().setPrettyPrinting().create();
        OrderTrackNumber orderTrackNumber = new OrderTrackNumber(track);
        String cancelJson = cancelGson.toJson(orderTrackNumber);
        return given()
                .spec(getSpec())
                .body(cancelJson)
                .when()
                .post(CANCEL_PATH)
                .then();
    }

    public ValidatableResponse get() {
        return given()
                .spec(getSpec())
                .when()
                .get(CREATE_PATH)
                .then();
    }
}
