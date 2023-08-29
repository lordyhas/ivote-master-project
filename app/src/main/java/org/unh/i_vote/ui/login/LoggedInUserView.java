package org.unh.i_vote.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */

/*
 *
 * @author lordyhas (Hassan K.)
 *
 */
class LoggedInUserView {
    private final String displayName;
    private final String displayEmail;

    private final String password;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayEmail, String displayName, String password) {
        this.displayName = displayName;
        this.displayEmail = displayEmail;
        this.password = password;
    }

    String getDisplayName() {
        return displayName;
    }

    String getDisplayEmail(){
        return displayEmail;
    }

    public String getPassword() {
        return password;
    }

    Boolean isLoggingIn(){
        return displayName == null;
    }

    Boolean isSigningIn(){
        return displayName != null;
    }
}