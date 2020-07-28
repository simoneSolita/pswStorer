package com.simonesolita.pswstorer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.repository.CredenzialeRepository;

import java.util.List;

public class CredenzialeViewModel extends AndroidViewModel {

    private CredenzialeRepository credenzialeRepository;
    private LiveData<List<Credenziale>> allCredenzialis;

    public CredenzialeViewModel(@NonNull Application application) {
        super(application);
        credenzialeRepository = new CredenzialeRepository(application);
        allCredenzialis = credenzialeRepository.getAllCredenziali();
    }

    public void insertCredenziale (Credenziale credenziale){
        credenzialeRepository.insert(credenziale);
    }

    public void updateCredenziale (Credenziale credenziale){
        credenzialeRepository.update(credenziale);
    }

    public void deleteCredenziale (Credenziale credenziale){
        credenzialeRepository.delete(credenziale);
    }

    public void deleteAllCredenziali (){
        credenzialeRepository.deleteAll();
    }

    public LiveData<List<Credenziale>> getAllCredenzialis(){
        return allCredenzialis;
    }

}
