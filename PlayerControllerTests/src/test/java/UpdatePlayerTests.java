import controllers.PlayerController;
import enums.Gender;
import enums.Role;
import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import models.requests.UpdatePlayerRequest;
import models.responses.CreatePlayerResponse;
import models.responses.UpdatePlayerResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
        CreatePlayerRequest validPlayer = CreatePlayerRequest
                .builder()
                .age(oldAge)
                .build();
        PlayerController playerController = new PlayerController();
        Response cretePlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        cretePlayerResponse
                .then()
                .statusCode(200);
        CreatePlayerResponse createdPlayer = cretePlayerResponse.as(CreatePlayerResponse.class);
        int playerId = createdPlayer.getId();
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest
                .builder()
                .age(newAge)
                .build();

        //Action
        Response updatePlayerResponse = playerController.UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        int acualStatusCode = updatePlayerResponse.statusCode();
        UpdatePlayerResponse actualPlayerInfo = updatePlayerResponse.as(UpdatePlayerResponse.class);
        int actualAge = actualPlayerInfo.getAge();
        Assert.assertEquals(acualStatusCode, 200,
                String.format("Status code is %s, not 200", acualStatusCode));
        Assert.assertEquals(actualAge, newAge,
                String.format("Ages are not equal, age is %d, but should be %d", actualAge, newAge));
    }

    @Test(dataProvider = "genderData")
    public void tc04_1_Update_SupervisorUpdatesGenderForExistingPlayerWithInvalidValue_StatusCodeIs400
            (String oldGender, String newGender){

        //Precondition
        CreatePlayerRequest validPlayer = CreatePlayerRequest
                .builder()
                .gender(oldGender)
                .build();
        PlayerController playerController = new PlayerController();
        Response cretePlayerResponse = playerController.CreatePlayer(Role.SUPERVISOR.name(), validPlayer);
        cretePlayerResponse
                .then()
                .statusCode(200);
        CreatePlayerResponse createdPlayer = cretePlayerResponse.as(CreatePlayerResponse.class);
        int playerId = createdPlayer.getId();
        UpdatePlayerRequest updatePlayerRequest = UpdatePlayerRequest
                .builder()
                .gender(newGender)
                .build();

        //Action
        Response updatePlayerResponse = playerController.UpdatePlayer(Role.SUPERVISOR.name(), playerId, updatePlayerRequest);

        //Assert
        int acualStatusCode = updatePlayerResponse.statusCode();
        Assert.assertEquals(acualStatusCode, 400,
                String.format("Status code is %s, not 400", acualStatusCode));
    }
}
