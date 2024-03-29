package models.responses;

import lombok.Data;

@Data
public class CreateGetPlayerResponse {
    private int age;
    private String gender;
    private int id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
