package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

public class Subscription {

    @SerializedName("status")
    public String status;
    @SerializedName("end_date")
    public String endDate;
    @SerializedName("subscribed")
    public String subscribed;
}
