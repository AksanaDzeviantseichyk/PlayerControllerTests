package models;

import com.github.javafaker.Faker;
import enums.Gender;
import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.responses.CreateGetPlayerResponse;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private static Faker faker = new Faker();
    @Builder.Default
    private int age = faker.number().numberBetween(17, 61);
    @Builder.Default
    private String gender = faker.number().numberBetween(0, 2) == 0 ? Gender.MALE.name() : Gender.FEMALE.name();
    @Builder.Default
    private String login = UUID.randomUUID().toString();
    @Builder.Default
    private String password = faker.regexify("[a-zA-Z0-9]{7,15}");
    @Builder.Default
    private String role = faker.number().numberBetween(1, 3) == 1 ? Role.ADMIN.name() : Role.USER.name();
    @Builder.Default
    private String screenName = UUID.randomUUID().toString();

    public List<String> compareFields(CreateGetPlayerResponse other) {
        List<String> diffFields = new ArrayList<>();

        if (this.age != other.getAge()) diffFields.add("age");
        if (!this.gender.equals(other.getGender())) diffFields.add("gender");
        if (!this.login.equals(other.getLogin())) diffFields.add("login");
        if (!this.password.equals(other.getPassword())) diffFields.add("password");
        if (!this.role.equals(other.getRole())) diffFields.add("role");
        if (!this.screenName.equals(other.getScreenName())) diffFields.add("screenName");

        return diffFields;
    }
}
