package org.unh.i_vote.data;

import org.unh.i_vote.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

/*
 *
 * @author lordyhas (Hassan K.)
 *
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String email, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser sUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username, email, password);
            return new Result.Success<>(sUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> login(String email, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser sUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(), null, email, password);
            return new Result.Success<>(sUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}