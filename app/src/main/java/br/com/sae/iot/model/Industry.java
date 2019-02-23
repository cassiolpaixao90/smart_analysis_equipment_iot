package br.com.sae.iot.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Industry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private String address;
    private List<IndustryArea> industryArea;

    public Industry() {
    }

    @Ignore
    public Industry(String name, String address, List<IndustryArea> industryArea) {
        this.name = name;
        this.industryArea = industryArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndustryArea> getIndustryArea() {
        return industryArea;
    }

    public void setIndustryArea(List<IndustryArea> industryArea) {
        this.industryArea = industryArea;
    }

    public boolean hasIdValid() {
        return id > 0;
    }
}
