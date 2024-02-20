import controllers.PlayerController;
import enums.Role;
import http.CommonResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import models.Player;
import models.requests.GetDeletePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;

@Feature("Verify DELETE operation on Delete player endpoint")
public class DeletePlayerTests {

    @Test
    @Description("Test Description : Verify the status code of deleting of the existing player")
    public void tc02_1_Delete_SupervisorDeletesExistingPlayer_StatusCodeIs200(){

        //Precondition
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.SUPERVISOR.name(), getDeletePlayerRequest);

        //Assert
        int acualStatusCode = deletePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
    }

    @Test
    @Description("Test Description : Verify the status code when player with user role deletes himself")
    public void tc02_2_Delete_UserDeletesHimself_StatusCodeIs403(){

        //Precondition
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.USER.name(), getDeletePlayerRequest);

        //Assert
        int acualStatusCode = deletePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 403, String.format("Status code is %s, not 403", acualStatusCode));
    }
}
