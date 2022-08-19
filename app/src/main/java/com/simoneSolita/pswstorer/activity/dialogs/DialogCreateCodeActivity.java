package com.simonesolita.pswstorer.activity.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.activity.PswStorerbaseActivity;
import com.simonesolita.pswstorer.utility.LoginUtility;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class DialogCreateCodeActivity extends PswStorerbaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_code);
        setFinishOnTouchOutside(false);

        setContent();
    }

    private void setContent() {
        TextInputLayout textInputLayoutCode1 = findViewById(R.id.dialog_create_code_code1);
        TextInputLayout textInputLayoutCode2 = findViewById(R.id.dialog_create_code_code2);
        MaterialButton buttonConferma = findViewById(R.id.dialog_create_code_confirm_code);

        buttonConferma.setOnClickListener(view -> {
            String code1 = Objects.requireNonNull(textInputLayoutCode1.getEditText()).getText().toString();
            String code2 = Objects.requireNonNull(textInputLayoutCode2.getEditText()).getText().toString();
            if (code1.equals(code2)) {
                //store code into shared
                try {
                    if (LoginUtility.setLoginPrivateCode(getApplicationContext(), code1)) {
                        finishDialogWithEsito(true);
                    }
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                    finishDialogWithEsito(false);
                }
            } else {
                textInputLayoutCode2.setErrorEnabled(true);
                textInputLayoutCode2.setError(getString(R.string.error_create_code_not_match));
            }
        });


        //the watcher handles the errors
        Objects.requireNonNull(textInputLayoutCode1.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutCode1.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable edit) {
                //if code is not 4 digit, we show an error
                if (!(edit.toString().length()==4)) {
                    textInputLayoutCode1.setErrorEnabled(true);
                    textInputLayoutCode1.setError(getString(R.string.error_create_code_not_4_digit));

                    //disable the confirmation code
                    textInputLayoutCode2.setEnabled(false);
                }else{
                    textInputLayoutCode1.setErrorEnabled(false);
                    //we enable the confirm code edittext
                    textInputLayoutCode2.setEnabled(true);
                }
            }
        });

        Objects.requireNonNull(textInputLayoutCode2.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutCode2.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable edit) {
                //if code is not 4 digit, we show an error
                if (!(edit.toString().length()==4)) {
                    textInputLayoutCode2.setErrorEnabled(true);
                    textInputLayoutCode2.setError(getString(R.string.error_create_code_not_4_digit));
                }else{
                    textInputLayoutCode2.setErrorEnabled(false);
                }

                //if code is long 4 chars, we enable the button
                buttonConferma.setVisibility(edit.toString().length()==4 ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void finishDialogWithEsito(boolean esito) {
        setResult(esito ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishDialogWithEsito(false);
    }
}
