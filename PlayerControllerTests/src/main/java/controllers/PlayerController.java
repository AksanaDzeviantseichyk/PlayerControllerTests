package controllers;

import http.CommonResponse;
import io.restassured.response.Response;
import models.Player;
import models.requests.GetDeletePlayerRequest;
import models.requests.UpdatePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import models.responses.UpdatePlayerResponse;
import utils.AppConfig;

import static io.restassured.RestAssured.given;

public class PlayerController {

    private String baseUrl = AppConfig.getProperty("api.baseUrl");

    public CommonResponse<CreateGetPlayerResponse> CreatePlayer (String editor, Player newPlayer)
    {
        String endpoint = AppConfig.getProperty("api.createPlayerEndpoint");
        Response response =  given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .pathParam("age", newPlayer.getAge())
                    .pathParam("gender", newPlayer.getGender())
                    .pathParam("login", newPlayer.getLogin())
                    .pathParam("password", newPlayer.getPassword())
                    .pathParam("role", newPlayer.getRole())
                    .pathParam("screenName", newPlayer.getScreenName())
                    .log()
                    .ifValidationFails()
                .when()
                    .get(endpoint);

        return new CommonResponse<>(response, CreateGetPlayerResponse.class);
    }
    public CommonResponse<Void> DeletePlayer (String editor, GetDeletePlayerRequest playerDeleteRequest)
    {
        String endpoint = AppConfig.getProperty("api.deletePlayerEndpoint");;
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .body(playerDeleteRequest)
                    .log()
                    .ifValidationFails()
                .when()
                    .delete(endpoint);

        return new CommonResponse<>(response, Void.class);
    }
    public CommonResponse<CreateGetPlayerResponse> GetPlayerInfoById (GetDeletePlayerRequest getPlayerByIdRequest)
    {
        String endpoint = AppConfig.getProperty("api.getPlayerEndpoint");;
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .body(getPlayerByIdRequest)
                    .log()
                    .ifValidationFails()
                .when()
                    .post(endpoint);

        return new CommonResponse<>(response, CreateGetPlayerResponse.class);
    }
    public CommonResponse<UpdatePlayerResponse> UpdatePlayer (String editor, int id, UpdatePlayerRequest updatePlayerFields)
    {
        String endpoint = AppConfig.getProperty("api.updatePlayerEndpoint");;
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .pathParam("id", id)
                    .body(updatePlayerFields)
                    .log()
                    .ifValidationFails()
                .when()
                    .patch(endpoint);

        return new CommonResponse<>(response, UpdatePlayerResponse.class);
    }
}
