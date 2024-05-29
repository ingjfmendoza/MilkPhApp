package controller;

import model.User;

public class UserSession {
    private static UserSession instance;
    public User user;

    private UserSession(User user) {
        this.user = user;
    }

    public static UserSession getInstance(User user) {
        if (instance == null) {
            instance = new UserSession(user);
        }
        return instance;
    }
}
