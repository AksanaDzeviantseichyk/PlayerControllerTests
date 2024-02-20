package apiTests;

import controllers.PlayerController;
import enums.Role;
import http.CommonResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import listeners.CustomTestListener;
import models.Player;
import models.requests.GetDeletePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;
@Listeners(CustomTestListener.class)
@Feature("Verify POST operation on Get player endpoint")
public class GetPlayerTests {
    private static final Logger logger = LoggerFactory.getLogger(GetPlayerTests.class);

    @Test
    @Description("Test Description : Verify the status code and response data of getting player info for existing player")
    public void tc03_1_Get_GettingPlayerInfoForExistingPlayer_StatusCodeIs200AndResponseBodyContainsCorrectData(){

        //Precondition
        logger.info("Create a valid player");
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        logger.info("Get player info for the created player");
        GetDeletePlayerRequest playerIdRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<CreateGetPlayerResponse> getPlayerResponse = playerController.GetPlayerInfoById(playerIdRequest);

        //Assert
        logger.info("Verify a status code");
        int acualStatusCode = getPlayerResponse.getStatusCode();
        CreateGetPlayerResponse actualPlayerInfo = getPlayerResponse.getBody();
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
        logger.info("Verify player id");
        Assert.assertEquals(actualPlayerInfo.getId(), createdPlayer.getId(), "Ids are not equal");
        logger.info("Verify player data");
        List<String> diffFields = validPlayer.compareFields(actualPlayerInfo);
        Assert.assertTrue(diffFields.isEmpty(), "Next fields are not equal:" + String.join(", ", diffFields));
    }

    @Test
    @Description("Test Description : Verify the status code of getting player info for not existing player")
    public void tc03_2_Get_GettingPlayerInfoForNotExistingPlayer_StatusCodeIs404(){

        //Precondition
        logger.info("Create a valid player");
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        logger.info("Delete the created player");
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.SUPERVISOR.name(), getDeletePlayerRequest);
        throwIfNotTargetStatus(deletePlayerResponse, 204);

        //Action
        logger.info("Get player info for not existing player");
        CommonResponse<CreateGetPlayerResponse> getPlayerInfoResponse = playerController.GetPlayerInfoById(getDeletePlayerRequest);

        //Assert
        logger.info("Verify a status code");
        int acualStatusCode = getPlayerInfoResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 404, String.format("Status code is %s, not 404", acualStatusCode));
    }
}