package br.com.sae.iot.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Product implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private String problem;
    private String problemArea;

    public Product() {
    }

    @Ignore
    public Product(int id, String name, String problem, String problemArea) {
        this.id = id;
        this.name = name;
        this.problem = problem;
        this.problemArea = problemArea;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", problem='" + problem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(problem, product.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, problem);
    }
}
