package com.simonesolita.pswstorer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.activity.dialogs.DialogCreateCodeActivity;
import com.simonesolita.pswstorer.adapter.GridCodeInputAdapter;
import com.simonesolita.pswstorer.constants.LoginConstants;
import com.simonesolita.pswstorer.utility.LoginUtility;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginActivity extends PswStorerbaseActivity {

    GridView gridCodeInput;
    TextInputLayout codeTextField;
    int[] digit = {R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_delete_digit, R.drawable.ic_0, R.drawable.ic_confirm_digit};
    //listener for set Code
    ActivityResultLauncher<Intent> setCodeResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    showInsertCodeLayout();
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    finish();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                try {
                    if (LoginUtility.getEncryptedSharedPreferences(getApplicationContext()).getBoolean(LoginConstants.SHAREDPREF_KEY_CODE_SAVED, false)) {
                        //if there is a code stored, we ask it to the user
                        showInsertCodeLayout();

                    } else {
                        //if no code has been stored, we ask user to set one
                        launchSetCodeDialog();
                    }
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                goToApp();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), getString(R.string.autenticazione_fallita),
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_auth_title))
                .setSubtitle(getString(R.string.biometric_auth_subtitle))
                .setNegativeButtonText(getString(R.string.biometric_auth_use_code))
                .build();
        biometricPrompt.authenticate(promptInfo);

        setContent();
    }

    private void setContent() {

        gridCodeInput = findViewById(R.id.login_gridview_code_input);
        codeTextField = findViewById(R.id.login_activity_textfield_code);
    }

    private void showInsertCodeLayout() {
        gridCodeInput.setVisibility(View.VISIBLE);
        codeTextField.setVisibility(View.VISIBLE);

        // Create an object of CustomAdapter and set Adapter to GirdView
        GridCodeInputAdapter gridAdapter = new GridCodeInputAdapter(this, digit);
        gridCodeInput.setAdapter(gridAdapter);
        // implement setOnItemClickListener event on GridView
        gridCodeInput.setOnItemClickListener((parent, view, position, id) -> {
            if (Objects.requireNonNull(codeTextField.getEditText()).getText() != null) {
                //if position is not 9(delete digit) or 11(confirm code) , concat the code with the digit
                String code = "";
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        codeTextField.setErrorEnabled(false);
                        code = codeTextField.getEditText().getText().toString().concat("" + (position + 1));
                        codeTextField.getEditText().setText(code);
                        break;
                    case 9:
                        codeTextField.setErrorEnabled(false);
                        // delete
                        if (!TextUtils.isEmpty(codeTextField.getEditText().getText().toString())) {
                            code = codeTextField.getEditText().getText().toString().substring(0, codeTextField.getEditText().getText().toString().length() - 1);
                            codeTextField.getEditText().setText(code);
                        }
                        break;
                    case 10:
                        codeTextField.setErrorEnabled(false);
                        //0
                        code = codeTextField.getEditText().getText().toString().concat("0");
                        codeTextField.getEditText().setText(code);
                        break;
                    case 11:
                        //confirm
                        if (codeTextField.getEditText().getText().toString().length() == 4) {
                            try {
                                if (LoginUtility.checkCode(codeTextField.getEditText().getText().toString(), getApplicationContext())) {
                                    //if code is ok, go to app
                                    goToApp();
                                } else {
                                    codeTextField.setErrorEnabled(true);
                                    codeTextField.setError(getString(R.string.error_verify_code_not_correct));
                                }
                            } catch (GeneralSecurityException | IOException e) {
                                codeTextField.setErrorEnabled(true);
                                codeTextField.setError(getString(R.string.error_verify_code_generic_error));
                            }
                        } else {
                            codeTextField.setErrorEnabled(true);
                            codeTextField.setError(getString(R.string.error_verify_code_not_4_digit));
                        }
                        break;
                }
            }
        });
    }

    private void launchSetCodeDialog() {
        //faccio vedere il dialog di errore invio
        Intent intent = new Intent(this, DialogCreateCodeActivity.class);
        setCodeResultLauncher.launch(intent);
    }

    private void goToApp() {
        Intent i = new Intent(this, CredenzialiListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}