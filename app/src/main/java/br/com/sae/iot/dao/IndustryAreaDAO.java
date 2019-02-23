package br.com.sae.iot.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.sae.iot.model.IndustryArea;

/**
 * @author cassiopaixao
 */
@Dao
public interface IndustryAreaDAO {

    @Insert
    void save(IndustryArea industryArea);

    @Query("SELECT * FROM industryArea")
    List<IndustryArea> all();

    @Delete
    void remove(IndustryArea industryArea);

    @Update
    void update(IndustryArea industryArea);

}

