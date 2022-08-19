package com.simonesolita.pswstorer.utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.simonesolita.pswstorer.constants.MainConstants;
import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.ArrayList;

public class Utility {

    public static void askRuntimePermission(Activity activity) {

        int permsRequestCode = 200;
        ArrayList<String> perms = findUnAskedPermissions(MainConstants.permissions, activity);

        String[] stockArr = new String[perms.size()];
        stockArr = perms.toArray(stockArr);

        if (stockArr.length > 0) {
            activity.requestPermissions(stockArr, permsRequestCode);
        }
    }

    private static ArrayList<String> findUnAskedPermissions(String[] wanted, Activity activity) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm, activity) /*&& shouldWeAsk(perm)*/) {
                result.add(perm);
            }
        }

        return result;

    }

    private static boolean hasPermission(String permission, Activity activity) {
        return (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean validaCredenziale(Credenziale credenzialeToValidate) {
        return !TextUtils.isEmpty(credenzialeToValidate.getNome()) &&
                !TextUtils.isEmpty(credenzialeToValidate.getValore());
    }
}
