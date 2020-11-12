package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

public class RequestResponse {

    @SerializedName("error")
    public String error;
    @SerializedName("success")
    public Success success;
}
