package models.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePlayerResponse {
    private int age;
    private String gender;
    private int id;
    private String login;
    private String password;
    private String role;
    private String screenName;

}
