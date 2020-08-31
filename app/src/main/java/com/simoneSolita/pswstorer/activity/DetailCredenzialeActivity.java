package com.simonesolita.pswstorer.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.constants.IntentConstants;
import com.simonesolita.pswstorer.database.PSWStorerDBManager;
import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.ArrayList;

public class DetailCredenzialeActivity extends PswStorerbaseActivity {

    private Credenziale credenzialeToShow;
    private String uuid;
    private TextView textViewNomeSito;
    private TextView textViewDescrizione;
    private TextView textViewUtenza;
    private TextView textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_credenziale);
        if (getIntent().hasExtra(IntentConstants.CREDENZIALI_LIST_DETAIL_UUID) && !TextUtils.isEmpty(getIntent().getStringExtra(IntentConstants.CREDENZIALI_LIST_DETAIL_UUID))){
            uuid = getIntent().getStringExtra(IntentConstants.CREDENZIALI_LIST_DETAIL_UUID);
            setContent();
        }
        setActionbar();
    }

    private void setActionbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        TextView textToolbar = (TextView) findViewById(R.id.textViewBreadcrumb);
        textToolbar.setText(getString(R.string.credenziali_breadcrumb));
        ImageButton frecciaBack = (ImageButton) findViewById(R.id.back_toolbar);
        frecciaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setContent() {
        if (!TextUtils.isEmpty(uuid)){
            ArrayList<Credenziale> credenzialiToCheck = PSWStorerDBManager.getInstance().getCredenzialeByCursor(PSWStorerDBManager.getInstance().getCredenzialeByUUID(uuid));
            if (!credenzialiToCheck.isEmpty()){
                credenzialeToShow = credenzialiToCheck.get(0);
                bindTextViews();

                textViewNomeSito.setText(credenzialeToShow.getNome());
                textViewDescrizione.setText(credenzialeToShow.getDescrizione());
                textViewUtenza.setText(credenzialeToShow.getUtenza());
                textViewPassword.setText(credenzialeToShow.getValore());
            }
        }
    }

    private void bindTextViews(){
        textViewNomeSito = findViewById(R.id.credenziale_detail_nome);
        textViewDescrizione = findViewById(R.id.credenziale_detail_descrizione);
        textViewUtenza = findViewById(R.id.credenziale_detail_nome_utenza);
        textViewPassword = findViewById(R.id.credenziale_detail_password);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
