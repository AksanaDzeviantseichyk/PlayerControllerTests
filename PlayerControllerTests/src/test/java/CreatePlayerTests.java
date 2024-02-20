import controllers.PlayerController;
import enums.Role;
import http.CommonResponse;
import models.Player;
import models.responses.CreateGetPlayerResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static models.Player.builder;

public class CreatePlayerTests {

    @DataProvider(name = "Editor")
    public Object[][] editors() {
        return new Object[][]
                {
                        {Role.SUPERVISOR.name()},
                        {Role.ADMIN.name()}
                };
    }

    @DataProvider(name = "InvalidPassword")
    public Object[][] invalidPasswords() {
        return new Object[][]
                {
                        {"1"},
                        {"1234567890qwertyuio"},
                        {"абвгдежз1"}
                };
    }

    @Test(dataProvider = "Editor")
    public void tc01_1_Create_SupervisorOrAdminCreatesPlayerWithValidData_StatusCodeIs200(String editor){

        //Precondition
        Player expectedPlayer = builder().build();
        PlayerController playerController = new PlayerController();

        //Action
        CommonResponse<CreateGetPlayerResponse> response = playerController.CreatePlayer(editor, expectedPlayer);

        //Assert
        int acualStatusCode = response.getStatusCode();
        Assert.assertEquals(acualStatusCode, 200,
                String.format("Status code is %d, not 200 for role: %s", acualStatusCode, editor ));
    }

    @Test(dataProvider =  "InvalidPassword")
    public void tc01_2_Create_SupervisorCreatesPlayerWithInvalidPassword_StatusCodeIs400(String invalidPassword){

        //Precondition
        Player expectedPlayer = builder().password(invalidPassword).build();
        PlayerController playerController = new PlayerController();

        //Action
        CommonResponse<CreateGetPlayerResponse> response = playerController.CreatePlayer(Role.SUPERVISOR.name(), expectedPlayer);

        //Assert
        int acualStatusCode = response.getStatusCode();
        Assert.assertEquals(acualStatusCode, 400,
                String.format("Status code is %s, not 400, password is '%s'", acualStatusCode, invalidPassword));
    }
}
