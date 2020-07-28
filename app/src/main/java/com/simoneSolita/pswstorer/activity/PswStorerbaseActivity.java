package com.simonesolita.pswstorer.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.utility.Utility;

public class PswStorerbaseActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.askRuntimePermission(PswStorerApplication.getCurrentActivity());
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 200:
                Boolean[] permissonGranted = new Boolean[permissions.length];
                boolean allPermissionsGranted = true;

                for (int i = 0; i < permissions.length; i++) {
                    permissonGranted[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                    if (!permissonGranted[i]) {
                        allPermissionsGranted = false;
                    }
                }

                if (!allPermissionsGranted) {
                    Utility.askRuntimePermission(PswStorerApplication.getCurrentActivity());
                }

                break;
        }
    }
}
