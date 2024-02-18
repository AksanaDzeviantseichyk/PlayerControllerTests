package controllers;

import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import models.requests.PlayerIdRequest;

import static io.restassured.RestAssured.given;

public class PlayerController {

    private String baseUrl = "http://3.68.165.45/";

    public Response CreatePlayer (String editor, CreatePlayerRequest newPlayer)
    {
        String endpoint = "/player/create/{editor}?age={age}&gender={gender}&login={login}&password={password}&role={role}&screenName={screenName}";
        return given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .pathParam("age", newPlayer.getAge())
                    .pathParam("gender", newPlayer.getGender())
                    .pathParam("login", newPlayer.getLogin())
                    .pathParam("password", newPlayer.getPassword())
                    .pathParam("role", newPlayer.getRole())
                    .pathParam("screenName", newPlayer.getScreenName())
                .when()
                    .get(endpoint)
                .then()
                    .extract()
                    .response();
    }
    public Response DeletePlayer (String editor, PlayerIdRequest playerDeleteRequest)
    {
        String endpoint = "/player/delete/{editor}";
        return given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .body(playerDeleteRequest)
                .when()
                    .delete(endpoint)
                .then()
                    .extract()
                    .response();
    }

    public Response GetPlayerInfoById (PlayerIdRequest getPlayerByIdRequest)
    {
        String endpoint = "/player/get";
        return given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .body(getPlayerByIdRequest)
                .when()
                    .post(endpoint)
                .then()
                    .extract()
                    .response();
    }

}
