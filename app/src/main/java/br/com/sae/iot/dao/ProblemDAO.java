package br.com.sae.iot.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.sae.iot.model.Problem;

@Dao
public interface ProblemDAO {

    @Insert
    void save(Problem problem);

    @Query("SELECT * FROM problem")
    List<Problem> all();

    @Delete
    void remove(Problem problem);

    @Update
    void update(Problem problem);

    @Query("SELECT * FROM problem WHERE industryId=:industryId")
    List<Problem> problemByIndustry(final int industryId);
}
