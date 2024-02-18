import controllers.PlayerController;
import enums.Role;
import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import models.responses.CreatePlayerResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static models.requests.CreatePlayerRequest.builder;

public class CreatePlayerTests {

    @Test
    public void tc01_1_Create_SupervisorCreatesUserPlayerWithValidData_StatusCodeIs200AndResponseContainsCorrectData(){

        //Precondition
        CreatePlayerRequest expectedPlayer = builder().build();
        PlayerController playerController = new PlayerController();

        //Action
        Response response = playerController.CreatePlayer(Role.SUPERVISOR.name(), expectedPlayer);

        //Assert
        int acualStatusCode = response.statusCode();
        CreatePlayerResponse actualPlayerResult = response.as(CreatePlayerResponse.class);
        Assert.assertEquals(acualStatusCode, 200, "Status code is {acualStatusCode}, not 200" );
        Assert.assertFalse(actualPlayerResult.getId() == 0, "Player's id is 0");
        Assert.assertEquals(actualPlayerResult.getAge(), expectedPlayer.getAge(), "Ages are not equal");
        Assert.assertEquals(actualPlayerResult.getGender(), expectedPlayer.getGender(), "Genders are not equal");
        Assert.assertEquals(actualPlayerResult.getLogin(), expectedPlayer.getLogin(), "Logins are not equal");
        Assert.assertEquals(actualPlayerResult.getPassword(), expectedPlayer.getPassword(), "Passwords are not equal");
        Assert.assertEquals(actualPlayerResult.getRole(), expectedPlayer.getRole(), "Roles are not equal");
        Assert.assertEquals(actualPlayerResult.getScreenName(), expectedPlayer.getScreenName(), "Screen names are not equal");
    }

    @Test
    public void tc01_2_Create_UserCreatesUserPlayerWithValidData_StatusCodeIs403(){

        //Precondition
        CreatePlayerRequest expectedPlayer = builder().build();
        PlayerController playerController = new PlayerController();

        //Action
        Response response = playerController.CreatePlayer(Role.USER.name(), expectedPlayer);

        //Assert
        int acualStatusCode = response.statusCode();
        Assert.assertEquals(acualStatusCode, 403, "Status code is {acualStatusCode}, not 403" );
    }
}
