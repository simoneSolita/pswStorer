package com.simonesolita.pswstorer.activity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.adapter.CredenzialeAdapter;
import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.interfaces.OnItemClickListener;
import com.simonesolita.pswstorer.viewmodel.CredenzialeViewModel;

import java.util.List;

public class CredenzialiListActivity extends PswStorerbaseActivity {

    private CredenzialeViewModel credenzialeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenziali_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_list_credenziali);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CredenzialeAdapter adapter = new CredenzialeAdapter();
        recyclerView.setAdapter(adapter);

        credenzialeViewModel = new ViewModelProvider(this).get(CredenzialeViewModel.class);
        credenzialeViewModel.getAllCredenzialis().observe(this, new Observer<List<Credenziale>>() {
            @Override
            public void onChanged(List<Credenziale> credenziales) {
                adapter.setCredenziali(credenziales);
            }
        });
    }
}
