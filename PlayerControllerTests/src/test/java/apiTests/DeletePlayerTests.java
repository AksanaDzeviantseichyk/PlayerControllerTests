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
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;
@Listeners(CustomTestListener.class)
@Feature("Verify DELETE operation on Delete player endpoint")
public class DeletePlayerTests{
    private static final Logger logger = LoggerFactory.getLogger(DeletePlayerTests.class);

    @Test
    @Description("Test Description : Verify the status code of deleting of the existing player")
    public void tc02_1_Delete_SupervisorDeletesExistingPlayer_StatusCodeIs200(){

        logger.info("tc02_1 Started");
        //Precondition
        logger.info("tc02_1 Create a valid player");
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        logger.info("tc02_1 Delete an existing player");
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.SUPERVISOR.name(), getDeletePlayerRequest);

        //Assert
        logger.info("tc02_1 Verify status code");
        int acualStatusCode = deletePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 200, String.format("Status code is %s, not 200", acualStatusCode));
        logger.info("tc02_1 Finished");
    }

    @Test
    @Description("Test Description : Verify the status code when player with user role deletes himself")
    public void tc02_2_Delete_UserDeletesHimself_StatusCodeIs403(){

        logger.info("tc02_2 Started");
        //Precondition
        logger.info("tc02_2 Create a valid player");
        Player validPlayer = Player.builder().build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();

        //Action
        logger.info("tc02_2 Player with user role tries to delete himself");
        GetDeletePlayerRequest getDeletePlayerRequest = GetDeletePlayerRequest.builder().playerId(createdPlayer.getId()).build();
        CommonResponse<Void> deletePlayerResponse = playerController.DeletePlayer(Role.USER.name(), getDeletePlayerRequest);

        //Assert
        logger.info("tc02_2 Verify status code");
        int acualStatusCode = deletePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 403, String.format("Status code is %s, not 403", acualStatusCode));
        logger.info("tc02_2 Finished");
    }
}
