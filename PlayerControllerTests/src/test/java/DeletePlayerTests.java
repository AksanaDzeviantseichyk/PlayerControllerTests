import controllers.PlayerController;
import enums.Role;
import io.restassured.response.Response;
import models.requests.CreatePlayerRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static models.requests.CreatePlayerRequest.builder;

public class DeletePlayerTests {

    @Test
    public void tc02_1_Delete_SupervisorDeletesExistingUserPlayer_StatusCodeIs200(){

        //Precondition
        CreatePlayerRequest createdPlayer = builder().build();
        PlayerController playerController = new PlayerController();
        Response response = playerController.CreatePlayer(Role.USER.name(), createdPlayer);

        //Action


        //Assert
        int acualStatusCode = response.statusCode();
        Assert.assertEquals(acualStatusCode, 403, "Status code is {acualStatusCode}, not 403" );
    }
}
