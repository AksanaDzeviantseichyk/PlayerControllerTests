import controllers.PlayerController;
import enums.Role;
import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import models.requests.PlayerIdRequest;
import models.responses.CreatePlayerResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeletePlayerTests {

    @Test
    public void tc02_1_Delete_SupervisorDeletesExistingUserPlayer_StatusCodeIs200(){

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
        Response deletePlayerResponse = playerController.DeletePlayer(Role.SUPERVISOR.name(), playerIdRequest);

        //Assert
        int acualStatusCode = deletePlayerResponse.statusCode();
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
    }

    @Test
    public void tc02_2_Delete_UserDeletesHimself_StatusCodeIs403(){

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
        Response deletePlayerResponse = playerController.DeletePlayer(Role.USER.name(), playerIdRequest);

        //Assert
        int acualStatusCode = deletePlayerResponse.statusCode();
        Assert.assertEquals(acualStatusCode, 403, String.format("Status code is %s, not 403", acualStatusCode));
    }
}
