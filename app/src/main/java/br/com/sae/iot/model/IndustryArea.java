package br.com.sae.iot.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author cassiopaixao
 */
@Entity(foreignKeys = @ForeignKey(entity = Industry.class,
        parentColumns = "id",
        childColumns = "industryId",
        onDelete = CASCADE))
public class IndustryArea implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private int industryId;


    public IndustryArea() {
    }

    @Ignore
    public IndustryArea(int id, String name, int industryId) {
        this.id = id;
        this.name = name;
        this.industryId = industryId;
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

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }
}
