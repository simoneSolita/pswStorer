package com.simonesolita.pswstorer.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.viewmodel.CredenzialeViewModel;

import java.util.List;

public class CredenzialiListActivity extends AppCompatActivity {

    private CredenzialeViewModel credenzialeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenziali_list);

        credenzialeViewModel = new ViewModelProvider(this).get(CredenzialeViewModel.class);
        credenzialeViewModel.getAllCredenzialis().observe(this, new Observer<List<Credenziale>>() {
            @Override
            public void onChanged(List<Credenziale> credenziales) {
                Toast.makeText(CredenzialiListActivity.this,"DING DANG DONG",Toast.LENGTH_LONG).show();
            }
        });
    }
}
