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
@Entity(foreignKeys = @ForeignKey(entity = IndustryArea.class,
        parentColumns = "id",
        childColumns = "industryAreaId",
        onDelete = CASCADE))

public class Product implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private String problem;
    private String problemArea;
    private int industryAreaId;

    public Product() {
    }

    @Ignore
    public Product(int id, String name, String problem, String problemArea, int industryAreaId) {
        this.id = id;
        this.name = name;
        this.problem = problem;
        this.problemArea = problemArea;
        this.industryAreaId = industryAreaId;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblemArea() {
        return problemArea;
    }

    public void setProblemArea(String problemArea) {
        this.problemArea = problemArea;
    }

    public int getIndustryAreaId() {
        return industryAreaId;
    }

    public void setIndustryAreaId(int industryAreaId) {
        this.industryAreaId = industryAreaId;
    }
}
