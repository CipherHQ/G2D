package eu.faircode.netguard.g2d.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BlockAppList {

    private String name = BlockAppList.class.getName();
    private List<AppModelJson> list = new ArrayList<>();

    public BlockAppList() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppModelJson> getList() {
        return list;
    }

    public void setList(List<AppModelJson> list) {
        this.list = list;
    }
}
