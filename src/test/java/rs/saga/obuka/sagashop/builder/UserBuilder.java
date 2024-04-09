package rs.saga.obuka.sagashop.builder;

import rs.saga.obuka.sagashop.domain.User;

public class UserBuilder {

    public static User userAna() {
        return User.builder()
                .username("ana")
                .password("ana")
                .name("Ana")
                .surname("Dedović")
                .build();
    }

    public static User genericUser(){
        return new User("user",
                "pass",
                "name","lastname",null,null, null);
    }

}
