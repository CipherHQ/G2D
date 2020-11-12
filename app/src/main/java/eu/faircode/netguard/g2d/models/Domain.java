package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

public class Domain {
    @SerializedName("id")
    public int id;
      @SerializedName("domain")
    public String domain;
      @SerializedName("created_at")
    public String createdAt;
      @SerializedName("updated_at")
    public String updatedAt;

}
