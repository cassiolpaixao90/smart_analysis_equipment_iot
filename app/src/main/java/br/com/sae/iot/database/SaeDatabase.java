package br.com.sae.iot.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.sae.iot.dao.IndustryAreaDAO;
import br.com.sae.iot.dao.IndustryDAO;
import br.com.sae.iot.dao.ProductDAO;
import br.com.sae.iot.model.Industry;
import br.com.sae.iot.model.IndustryArea;
import br.com.sae.iot.model.Product;

/**
 * @author cassiopaixao
 */
@Database(entities = {Industry.class, IndustryArea.class, Product.class}, version = 1, exportSchema = false)
public abstract class SaeDatabase extends RoomDatabase {

    private static final String NAME_DB = "industry.db";

    public abstract IndustryDAO getIndustryDao();
    public abstract IndustryAreaDAO getIndustryAreaDao();
    public abstract ProductDAO getProductDao();

    public static SaeDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, SaeDatabase.class, NAME_DB)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }
}

//.addMigrations(new Migration(1, 2) {
//@Override
//public void migrate(@NonNull SupportSQLiteDatabase database) {
//        database.execSQL("ALTER TABLE alumo ADD COLUMN sobrenome TEXT");
//        }
//        }, new Migration(2, 3) {
//@Override
//public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//        })
