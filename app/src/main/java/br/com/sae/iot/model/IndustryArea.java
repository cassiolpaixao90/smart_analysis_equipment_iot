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
//@Entity(foreignKeys = @ForeignKey(entity = Industry.class,
//        parentColumns = "id",
//        childColumns = "industryId",
//        onDelete = CASCADE))
@Entity
public class IndustryArea implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;

    public IndustryArea() {
    }

    @Ignore
    public IndustryArea(String name) {
        this.name = name;
    }

    @Ignore
    public IndustryArea(int id, String name) {
        this.id = id;
        this.name = name;
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

}
