package com.simonesolita.pswstorer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.constants.IntentConstants;
import com.simonesolita.pswstorer.database.PSWStorerDBManager;
import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.utility.Utility;

import java.util.UUID;

public class AddCredenzialeActivity extends PswStorerbaseActivity {

    private EditText editTextNome;
    private EditText editTextDescrizione;
    private EditText editTextPassword;
    private EditText editTextUtenza;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credenziale);
        setContent();
        setActionbar();
        createUUID();

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
        editTextNome = (EditText) findViewById(R.id.edittext_nome_sito_add_credenziale);
        editTextDescrizione = (EditText) findViewById(R.id.edittext_descrizione_sito_add_credenziale);
        editTextPassword = (EditText) findViewById(R.id.edittext_password_sito_add_credenziale);
        editTextUtenza = (EditText) findViewById(R.id.edittext_utenza_sito_add_credenziale);

        Button salva = (Button) findViewById(R.id.button_salva_credenziale);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCredenziale();
            }
        });
    }

    private void createUUID(){
        uuid = UUID.randomUUID().toString();
    }

    private void inserisciCredenziale(){
        String nomeSito = editTextNome.getText().toString();
        String descrizioneSito = editTextDescrizione.getText().toString();
        String passwordSito = editTextPassword.getText().toString();
        String utenzaSito = editTextUtenza.getText().toString();
        Credenziale credenzialeToAdd = new Credenziale();
        credenzialeToAdd.setUuid(uuid);
        credenzialeToAdd.setNome(nomeSito);
        credenzialeToAdd.setDescrizione(descrizioneSito);
        credenzialeToAdd.setValore(passwordSito);
        credenzialeToAdd.setUtenza(utenzaSito);
        if(Utility.validaCredenziale(credenzialeToAdd)){
            try {
                PSWStorerDBManager.getInstance().addCredenziale(credenzialeToAdd);
                setResult(IntentConstants.CREDENZIALI_LIST_RESULT_CODE_ADD_OK);
            }catch (Exception e){
                setResult(IntentConstants.CREDENZIALI_LIST_RESULT_CODE_ADD_KO);
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
