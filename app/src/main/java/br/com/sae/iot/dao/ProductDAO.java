package br.com.sae.iot.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.sae.iot.model.Product;

@Dao
public interface ProductDAO {

    @Insert
    void save(Product product);

    @Query("SELECT * FROM product")
    List<Product> all();

    @Delete
    void remove(Product product);

    @Update
    void update(Product product);

}
