package com.simonesolita.pswstorer.utility;

import static androidx.security.crypto.MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE;
import static androidx.security.crypto.MasterKey.DEFAULT_MASTER_KEY_ALIAS;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.simonesolita.pswstorer.constants.LoginConstants;
import com.simonesolita.pswstorer.constants.MainConstants;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginUtility {

    public static SharedPreferences getEncryptedSharedPreferences(Context context) throws GeneralSecurityException, IOException {

        // this is equivalent to using deprecated MasterKeys.AES256_GCM_SPEC
        KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .build();

        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build();

        return EncryptedSharedPreferences.create(
                context,
                "pswStorer_preference",
                masterKey, // masterKey created above
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }

    public static boolean setLoginPrivateCode(Context context,String code) throws GeneralSecurityException, IOException {
        if (getEncryptedSharedPreferences(context).edit().putString(LoginConstants.SHAREDPREF_KEY_CODE,code).commit()){
            return getEncryptedSharedPreferences(context).edit().putBoolean(LoginConstants.SHAREDPREF_KEY_CODE_SAVED,true).commit();
        }
        return false;
    }


    public static boolean checkCode(String codeToCheck,Context context) throws GeneralSecurityException, IOException {
        SharedPreferences sp =getEncryptedSharedPreferences(context);
        if (!TextUtils.isEmpty(codeToCheck)) {
            return codeToCheck.equals(sp.getString(LoginConstants.SHAREDPREF_KEY_CODE, ""));
        }
        return false;
    }

}
