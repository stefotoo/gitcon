package com.sample.android.gitcon.models.eventbus;

import com.sample.android.gitcon.models.abstracts.AUser;

public class OnUserUpdatedEvent {

    // variables
    private AUser user;

    // constructor
    public OnUserUpdatedEvent(AUser user) {
        this.user = user;
    }

    // methods
    public AUser getUser() {
        return user;
    }

    public void setUser(AUser user) {
        this.user = user;
    }
}
