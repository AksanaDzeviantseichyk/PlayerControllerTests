import controllers.PlayerController;
import enums.Gender;
import enums.Role;
import http.CommonResponse;
import models.Player;
import models.requests.UpdatePlayerRequest;
import models.responses.CreateGetPlayerResponse;
import models.responses.UpdatePlayerResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static extensions.CommonResponseExtension.throwIfNotTargetStatus;

public class UpdatePlayerTests {

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
    public void tc04_1_Update_SupervisorUpdatesAgeForExistingPlayerWithValidValue_StatusCodeIs200AndResponseBodyContainsCorrectData
            (int oldAge, int newAge){

        //Precondition
        Player validPlayer = Player.builder().age(oldAge).build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController
                .CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        int playerId = createdPlayer.getId();

        //Action
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest.builder().age(newAge).build();
        CommonResponse<UpdatePlayerResponse> updatePlayerResponse = playerController
                .UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        int acualStatusCode = updatePlayerResponse.getStatusCode();
        int actualAge = updatePlayerResponse.getBody().getAge();
        Assert.assertEquals(acualStatusCode, 200,
                String.format("Status code is %s, not 200", acualStatusCode));
        Assert.assertEquals(actualAge, newAge,
                String.format("Ages are not equal, age is %d, but should be %d", actualAge, newAge));
    }

    @Test(dataProvider = "genderData")
    public void tc04_1_Update_SupervisorUpdatesGenderForExistingPlayerWithInvalidValue_StatusCodeIs400
            (String oldGender, String newGender){

        //Precondition
        Player validPlayer = Player.builder().gender(oldGender).build();
        PlayerController playerController = new PlayerController();
        CommonResponse<CreateGetPlayerResponse> createPlayerResponse = playerController
                .CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        throwIfNotTargetStatus(createPlayerResponse, 200);
        CreateGetPlayerResponse createdPlayer = createPlayerResponse.getBody();
        int playerId = createdPlayer.getId();

        //Action
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest.builder().gender(newGender).build();
        CommonResponse<UpdatePlayerResponse> updatePlayerResponse = playerController
                .UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        int acualStatusCode = updatePlayerResponse.getStatusCode();
        Assert.assertEquals(acualStatusCode, 400,
                String.format("Status code is %s, not 400", acualStatusCode));
    }
}