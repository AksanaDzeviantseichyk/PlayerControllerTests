package controllers;

import http.CommonResponse;
import io.restassured.response.Response;
import models.Player;
import models.requests.GetDeletePlayerRequest;
import models.requests.UpdatePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import models.responses.UpdatePlayerResponse;

import static io.restassured.RestAssured.given;

public class PlayerController {

    private String baseUrl = "http://3.68.165.45/";

    public CommonResponse<CreateGetPlayerResponse> CreatePlayer (String editor, Player newPlayer)
    {
        String endpoint = "/player/create/{editor}?age={age}&gender={gender}&login={login}&password={password}&role={role}&screenName={screenName}";
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
                .when()
                    .get(endpoint);

        return new CommonResponse<>(response, CreateGetPlayerResponse.class);
    }
    public CommonResponse<Void> DeletePlayer (String editor, GetDeletePlayerRequest playerDeleteRequest)
    {
        String endpoint = "/player/delete/{editor}";
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .body(playerDeleteRequest)
                .when()
                    .delete(endpoint);

        return new CommonResponse<>(response, Void.class);
    }
    public CommonResponse<CreateGetPlayerResponse> GetPlayerInfoById (GetDeletePlayerRequest getPlayerByIdRequest)
    {
        String endpoint = "/player/get";
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .body(getPlayerByIdRequest)
                .when()
                    .post(endpoint);

        return new CommonResponse<>(response, CreateGetPlayerResponse.class);
    }
    public CommonResponse<UpdatePlayerResponse> UpdatePlayer (String editor, int id, UpdatePlayerRequest updatePlayerFields)
    {
        String endpoint = "/player/update/{editor}/{id}";
        Response response = given()
                    .baseUri(baseUrl)
                    .contentType("application/json")
                    .pathParam("editor", editor)
                    .pathParam("id", id)
                    .body(updatePlayerFields)
                .when()
                    .patch(endpoint);

        return new CommonResponse<>(response, UpdatePlayerResponse.class);
    }
}
