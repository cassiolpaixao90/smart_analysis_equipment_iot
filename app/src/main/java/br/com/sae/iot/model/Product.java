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
//@Entity(foreignKeys = @ForeignKey(entity = IndustryArea.class,
//        parentColumns = "id",
//        childColumns = "industryAreaId",
//        onDelete = CASCADE))

@Entity
public class Product implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;

    public Product() {
    }

    @Ignore
    public Product(String name) {
        this.name = name;
    }

    @Ignore
    public Product(int id, String name) {
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
