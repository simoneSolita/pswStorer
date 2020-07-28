package com.simonesolita.pswstorer.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.simonesolita.pswstorer.constants.DbConstants;
import com.simonesolita.pswstorer.dao.CredenzialeDao;
import com.simonesolita.pswstorer.entities.Credenziale;

@Database(entities = {Credenziale.class}, version = DbConstants.DATABASE_VERSION)
public abstract class PswStorerDatabase extends RoomDatabase {

    private static PswStorerDatabase instance;

    public abstract CredenzialeDao credenzialeDao();

    public static synchronized PswStorerDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PswStorerDatabase.class,DbConstants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private CredenzialeDao credenzialeDao;

        private PopulateDBAsyncTask (PswStorerDatabase pswStorerDatabase){
            this.credenzialeDao = pswStorerDatabase.credenzialeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            credenzialeDao.insert(new Credenziale("sito per compere","Amazon","125424"));
            credenzialeDao.insert(new Credenziale("sito per cibo","JustEat","fn98745hw"));
            credenzialeDao.insert(new Credenziale("sito per mail","GMAIL","fw45ht&&&"));
            return null;
        }
    }
}
