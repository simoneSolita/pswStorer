package com.simonesolita.pswstorer.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.simonesolita.pswstorer.dao.CredenzialeDao;
import com.simonesolita.pswstorer.database.PswStorerDatabase;
import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.List;

public class CredenzialeRepository {

    private CredenzialeDao credenzialeDao;
    private LiveData<List<Credenziale>> allCredenziali;

    public CredenzialeRepository (Application app){
        PswStorerDatabase database = PswStorerDatabase.getInstance(app);
        credenzialeDao = database.credenzialeDao();
        allCredenziali = credenzialeDao.getAllCredenziali();
    }

    public void insert(Credenziale credenziale){
        new InsertCredenzialeAsyncTask(credenzialeDao).execute(credenziale);
    }

    public void update(Credenziale credenziale){
        new UpdateCredenzialeAsyncTask(credenzialeDao).execute(credenziale);
    }

    public void delete(Credenziale credenziale){
        new DeleteCredenzialeAsyncTask(credenzialeDao).execute(credenziale);
    }

    public void deleteAll(){
        new DeleteAllCredenzialeAsyncTask(credenzialeDao).execute();
    }

    public LiveData<List<Credenziale>> getAllCredenziali(){
        return allCredenziali;
    }

    private static class InsertCredenzialeAsyncTask extends AsyncTask<Credenziale,Void,Void>{
        private CredenzialeDao credenzialeDao;

        private InsertCredenzialeAsyncTask (CredenzialeDao credenzialeDao){
            this.credenzialeDao = credenzialeDao;
        }

        @Override
        protected Void doInBackground(Credenziale... credenziales) {
            credenzialeDao.insert(credenziales[0]);
            return null;
        }
    }

    private static class UpdateCredenzialeAsyncTask extends AsyncTask<Credenziale,Void,Void>{
        private CredenzialeDao credenzialeDao;

        private UpdateCredenzialeAsyncTask (CredenzialeDao credenzialeDao){
            this.credenzialeDao = credenzialeDao;
        }

        @Override
        protected Void doInBackground(Credenziale... credenziales) {
            credenzialeDao.update(credenziales[0]);
            return null;
        }
    }

    private static class DeleteCredenzialeAsyncTask extends AsyncTask<Credenziale,Void,Void>{
        private CredenzialeDao credenzialeDao;

        private DeleteCredenzialeAsyncTask (CredenzialeDao credenzialeDao){
            this.credenzialeDao = credenzialeDao;
        }

        @Override
        protected Void doInBackground(Credenziale... credenziales) {
            credenzialeDao.delete(credenziales[0]);
            return null;
        }
    }

    private static class DeleteAllCredenzialeAsyncTask extends AsyncTask<Void,Void,Void>{
        private CredenzialeDao credenzialeDao;

        private DeleteAllCredenzialeAsyncTask (CredenzialeDao credenzialeDao){
            this.credenzialeDao = credenzialeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            credenzialeDao.deleteAllCredenziali();
            return null;
        }
    }
}
