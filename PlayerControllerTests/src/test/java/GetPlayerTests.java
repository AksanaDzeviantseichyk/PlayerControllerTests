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
import java.util.List;
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;

@Feature("Verify POST operation on Get player endpoint")
public class GetPlayerTests {

    @Test
    @Description("Test Description : Verify the status code and response data of getting player info for existing player")
    public void tc03_1_Get_GettingPlayerInfoForExistingPlayer_StatusCodeIs200AndResponseBodyContainsCorrectData(){

        //Precondition
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        GetDeletePlayerRequest playerIdRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<CreateGetPlayerResponse> getPlayerResponse = playerController.GetPlayerInfoById(playerIdRequest);

        //Assert
        int acualStatusCode = getPlayerResponse.getStatusCode();
        CreateGetPlayerResponse actualPlayerInfo = getPlayerResponse.getBody();
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
        Assert.assertEquals(actualPlayerInfo.getId(), createdPlayer.getId(), "Ids are not equal");
        List<String> diffFields = validPlayer.compareFields(actualPlayerInfo);
        Assert.assertTrue(diffFields.isEmpty(), "Next fields are not equal:" + String.join(", ", diffFields));
    }

    @Test
    @Description("Test Description : Verify the status code of getting player info for not existing player")
    public void tc03_2_Get_GettingPlayerInfoForNotExistingPlayer_StatusCodeIs404(){

        //Precondition
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.SUPERVISOR.name(), getDeletePlayerRequest);
        throwIfNotTargetStatus(deletePlayerResponse, 204);

        //Action
        CommonResponse<CreateGetPlayerResponse> getPlayerInfoResponse = playerController.GetPlayerInfoById(getDeletePlayerRequest);

        //Assert
        int acualStatusCode = getPlayerInfoResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 404, String.format("Status code is %s, not 404", acualStatusCode));
    }
}