package br.com.sae.iot.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.sae.iot.model.Industry;

/**
 * @author cassiopaixao
 */

@Dao
public interface IndustryDAO {

    @Insert
    void save(Industry industry);

    @Query("SELECT * FROM industry")
    List<Industry> all();

    @Delete
    void remove(Industry industry);

    @Update
    void update(Industry industry);
}
