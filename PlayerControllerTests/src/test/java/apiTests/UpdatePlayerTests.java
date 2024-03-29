package apiTests;

import controllers.PlayerController;
import enums.Gender;
import enums.Role;
import http.CommonResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import listeners.CustomTestListener;
import models.Player;
import models.requests.UpdatePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import models.responses.UpdatePlayerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;
@Listeners(CustomTestListener.class)
@Feature("Verify PATCH operation on Update player endpoint")
public class UpdatePlayerTests {
    private static final Logger logger = LoggerFactory.getLogger(UpdatePlayerTests.class);

    @DataProvider(name = "ageData")
    public Object[][] ageData() {
        return new Object[][] {
                {17, 60}
        };
    }

    @DataProvider(name = "genderData")
    public Object[][] genderData() {
        return new Object[][] {
                {Gender.FEMALE.name(), "feminine"}
        };
    }

    @Test(dataProvider = "ageData")
    @Description("Test Description : Verify the status code and response data of updating age for existing player with valid value")
    public void tc04_1_Update_SupervisorUpdatesAgeForExistingPlayerWithValidValue_StatusCodeIs200AndResponseBodyContainsCorrectData
            (int oldAge, int newAge){

        logger.info("tc04_1 Started");
        //Precondition
        logger.info("tc04_1 Create a valid player");
        Player validPlayer = Player.builder().age(oldAge).build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController
                .CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        int playerId = createdPlayer.getId();

        //Action
        logger.info("tc04_1 Update the created player age with valid value by Supervisor");
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest.builder().age(newAge).build();
        CommonResponse<UpdatePlayerResponse> updatePlayerResponse = playerController
                .UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        int acualStatusCode = updatePlayerResponse.getStatusCode();
        int actualAge = updatePlayerResponse.getBody().getAge();
        logger.info("tc04_1 Verify status code");
        Assert.assertEquals(acualStatusCode, 200,
                String.format("Status code is %s, not 200", acualStatusCode));
        logger.info("tc04_1 Verify actual age");
        Assert.assertEquals(actualAge, newAge,
                String.format("Ages are not equal, age is %d, but should be %d", actualAge, newAge));
        logger.info("tc04_1 Finished");
    }

    @Test(dataProvider = "genderData")
    @Description("Test Description : Verify the status code of updating gender for existing player with invalid value")
    public void tc04_1_Update_SupervisorUpdatesGenderForExistingPlayerWithInvalidValue_StatusCodeIs400
            (String oldGender, String newGender){

        logger.info("tc04_2 Started");
        logger.info("tc04_2 Create a valid player");
        //Precondition
        Player validPlayer = Player.builder().gender(oldGender).build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController
                .CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        int playerId = createdPlayer.getId();

        //Action
        logger.info("tc04_2 Update the created player gender with invalid value by Supervisor");
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest.builder().gender(newGender).build();
        CommonResponse<UpdatePlayerResponse> updatePlayerResponse = playerController
                .UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        logger.info("tc04_2 Verify status code");
        int acualStatusCode = updatePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 400,
                String.format("Status code is %s, not 400", acualStatusCode));
        logger.info("tc04_2 Finished");
    }
}