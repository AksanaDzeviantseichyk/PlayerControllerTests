package apiTests;

import controllers.PlayerController;
import enums.Role;
import http.CommonResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import listeners.CustomTestListener;
import models.Player;
import models.responses.CreateGetPlayerResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static models.Player.builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Listeners(CustomTestListener.class)
@Feature("Verify GET operation on Create player endpoint")
public class CreatePlayerTests {
    private static final Logger logger = LoggerFactory.getLogger(CreatePlayerTests.class);

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
    @Description("Test Description : Verify the status code of creating a valid player by Supervisor or Admin")
    public void tc01_1_Create_SupervisorOrAdminCreatesPlayerWithValidData_StatusCodeIs200(String editor){

        logger.info("tc01_1 Started");
        //Precondition
        Player expectedPlayer = builder().build();
        PlayerController playerController = new PlayerController();

        //Action
        logger.info("tc01_1 Create a valid player");
        CommonResponse<CreateGetPlayerResponse> response = playerController.CreatePlayer(editor, expectedPlayer);

        //Assert
        logger.info("tc01_1 Verify status code");
        int acualStatusCode = response.getStatusCode();
        Assert.assertEquals(acualStatusCode, 200,
                String.format("Status code is %d, not 200 for role: %s", acualStatusCode, editor ));
        logger.info("tc01_1 Finished");
    }

    @Test(dataProvider =  "InvalidPassword")
    @Description("Test Description : Verify the status code of creating a player with an invaid password")
    public void tc01_2_Create_SupervisorCreatesPlayerWithInvalidPassword_StatusCodeIs400(String invalidPassword){

        logger.info("tc01_2 Started");
        //Precondition
        Player expectedPlayer = builder().password(invalidPassword).build();
        PlayerController playerController = new PlayerController();

        //Action
        logger.info("tc01_2 Trying to create player with invalid password");
        CommonResponse<CreateGetPlayerResponse> response = playerController.CreatePlayer(Role.SUPERVISOR.name(), expectedPlayer);

        //Assert
        logger.info("tc01_2 Verify status code");
        int acualStatusCode = response.getStatusCode();
        Assert.assertEquals(acualStatusCode, 400,
                String.format("Status code is %s, not 400, password is '%s'", acualStatusCode, invalidPassword));
        logger.info("tc01_2 Finished");
    }
}
