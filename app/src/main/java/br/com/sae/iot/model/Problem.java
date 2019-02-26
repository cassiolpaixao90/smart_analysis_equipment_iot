package br.com.sae.iot.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
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
public class Problem implements Serializable {

    @PrimaryKey
    public int id;
    private String titleProblem;
    private String descProblem;
    private String areaProblem;
    private String productProblem;
    private int industryId;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Problem() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getTitleProblem() {
        return titleProblem;
    }

    public void setTitleProblem(String titleProblem) {
        this.titleProblem = titleProblem;
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
