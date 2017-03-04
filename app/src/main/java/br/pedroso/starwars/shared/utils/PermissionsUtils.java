package br.pedroso.starwars.shared.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by felipe on 04/03/17.
 */

public class PermissionsUtils {
    public static boolean hasPermission(Context context, String permissionString) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, permissionString);

        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }
}
