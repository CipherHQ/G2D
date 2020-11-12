package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    public User user;
    @SerializedName("subscription")
    public Subscription subscription;
}
