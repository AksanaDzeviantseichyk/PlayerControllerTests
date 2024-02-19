import controllers.PlayerController;
import enums.Role;
import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import models.requests.PlayerIdRequest;
import models.responses.CreatePlayerResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetPlayerTests {

    @Test
    public void tc03_1_Get_GettingPlayerInfoForExistingUser_StatusCodeIs200AndResponseBodyContainsCorrectData(){

        //Precondition
        CreatePlayerRequest validPlayer = CreatePlayerRequest
                .builder()
                .build();
        PlayerController playerController = new PlayerController();
        Response cretePlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        cretePlayerResponse
                .then()
                .statusCode(200);
        CreatePlayerResponse createdPlayer = cretePlayerResponse.as(CreatePlayerResponse.class);
        PlayerIdRequest playerIdRequest = PlayerIdRequest
                .builder()
                .playerId(createdPlayer.getId())
                .build();

        //Action
        Response getPlayerResponse = playerController.GetPlayerInfoById(playerIdRequest);

        //Assert
        int acualStatusCode = getPlayerResponse.statusCode();
        CreatePlayerResponse actualPlayerInfo = getPlayerResponse.as(CreatePlayerResponse.class);
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
        Assert.assertEquals(actualPlayerInfo.getId(), createdPlayer.getId(), "Ids are not equal");
        List<String> diffFields = validPlayer.compareFields(actualPlayerInfo);
        Assert.assertTrue(diffFields.isEmpty(), "Next fields are not equal:" + String.join(", ", diffFields));
    }

    @Test
    public void tc03_2_Get_GettingPlayerInfoForNotExistingPlayer_StatusCodeIs404(){

        //Precondition
        CreatePlayerRequest validPlayer = CreatePlayerRequest
                .builder()
                .build();
        PlayerController playerController = new PlayerController();
        Response cretePlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        cretePlayerResponse
                .then()
                .statusCode(200);
        CreatePlayerResponse createdPlayer = cretePlayerResponse.as(CreatePlayerResponse.class);
        PlayerIdRequest playerIdRequest = PlayerIdRequest
                .builder()
                .playerId(createdPlayer.getId())
                .build();
        playerController.DeletePlayer(Role.SUPERVISOR.name(), playerIdRequest);

        //Action
        Response getPlayerInfoResponse = playerController.GetPlayerInfoById(playerIdRequest);

        //Assert
        int acualStatusCode = getPlayerInfoResponse.statusCode();
        Assert.assertEquals(acualStatusCode, 404, String.format("Status code is %s, not 404", acualStatusCode));
    }
}
