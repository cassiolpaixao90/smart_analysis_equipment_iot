package br.com.sae.iot.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author cassiopaixao
 */
@Entity
public class Industry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;

    private String descProblem;
    private String areaProblem;
    private String productProblem;


    public Industry() {
    }

    @Ignore
    public Industry(String name) {
        this.name = name;
    }

    @Ignore
    public Industry(String name, String descProblem, String areaProblem, String productProblem) {
        this.name = name;
        this.descProblem = descProblem;
        this.areaProblem = areaProblem;
        this.productProblem = productProblem;
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

    public boolean hasIdValid() {
        return id > 0;
    }

    public String getDescProblem() {
        return descProblem;
    }

    public void setDescProblem(String descProblem) {
        this.descProblem = descProblem;
    }

    public String getAreaProblem() {
        return areaProblem;
    }

    public void setAreaProblem(String areaProblem) {
        this.areaProblem = areaProblem;
    }

    public String getProductProblem() {
        return productProblem;
    }

    public void setProductProblem(String productProblem) {
        this.productProblem = productProblem;
    }
}
