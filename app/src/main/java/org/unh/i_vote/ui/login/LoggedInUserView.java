package org.unh.i_vote.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private final String displayName;
    private final String displayEmail;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayEmail, String displayName) {
        this.displayName = displayName;
        this.displayEmail = displayEmail;
    }

    String getDisplayName() {
        return displayName;
    }

    String getDisplayEmail(){
        return displayEmail;
    }
}