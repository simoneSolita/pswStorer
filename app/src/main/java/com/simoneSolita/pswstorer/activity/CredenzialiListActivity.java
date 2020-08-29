package com.simonesolita.pswstorer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.adapter.CredenzialeAdapter;
import com.simonesolita.pswstorer.constants.IntentConstants;
import com.simonesolita.pswstorer.database.PSWStorerDBManager;

public class CredenzialiListActivity extends PswStorerbaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenziali_list);
        setContent();
    }

    private void setContent() {
        FloatingActionButton floatingButton = findViewById(R.id.add_credenziale_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CredenzialiListActivity.this, AddCredenzialeActivity.class);
                startActivityForResult(intent, IntentConstants.CREDENZIALI_LIST_REQUEST_CODE_ADD);
            }
        });

        recyclerView = findViewById(R.id.recyclerView_list_credenziali);
        calculateAdapter();
    }

    private void calculateAdapter() {
        CredenzialeAdapter adapter = new CredenzialeAdapter(PSWStorerDBManager.getInstance().getCredenzialeByCursor(PSWStorerDBManager.getInstance().getAllCredenzialis()), PswStorerApplication.getCurrentActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentConstants.CREDENZIALI_LIST_REQUEST_CODE_ADD:
                if (resultCode == IntentConstants.CREDENZIALI_LIST_RESULT_CODE_ADD_OK) {
                    calculateAdapter();
                } else if (resultCode == IntentConstants.CREDENZIALI_LIST_RESULT_CODE_ADD_KO) {
                    Toast.makeText(PswStorerApplication.getCurrentActivity(), PswStorerApplication.getCurrentActivity().getString(R.string.aggiunta_credenziale_ko), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(PswStorerApplication.getCurrentActivity(), PswStorerApplication.getCurrentActivity().getString(R.string.nessun_requestcode_censito), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
