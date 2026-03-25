package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;
import utils.TokenManager;

public class BaseApiClient {

    protected RequestSpecification request;

    public BaseApiClient() {

        request = RestAssured.given()
                .baseUri(ConfigReader.getProperty("base.url"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
    protected RequestSpecification withAuth() {
        return request.header("Authorization", "Bearer " + TokenManager.getToken());
    }
}
