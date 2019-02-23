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


    public Industry() {
    }

    @Ignore
    public Industry(String name) {
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

    public boolean hasIdValid() {
        return id > 0;
    }
}
