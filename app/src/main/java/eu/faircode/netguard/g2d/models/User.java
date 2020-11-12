package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("email_verified_at")
    public String emailVerfiedAt;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("c_password")
    public String c_password;
    @SerializedName("update_domain")
    public int updateDomain;
    @SerializedName("subscription")
    public Subscription subscription;

    public User() {}

    public User(String name, String email, String password, String c_password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.c_password = c_password;
    }
}
