package youngtalents;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SaveSharedPreference { //DIese Klasse dient dazu, die Userdaten abzuspeichern falls der User eingeloggt bleiben will. Des weiteren wird hier zur Laufzeit der App der Username abgespeichert um diesen an mehreren Stellen anzuzeigen und zu verwenden
    static final String PREF_USER_NAME = "username";
    static final String PREF_USER_ID = "userid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserID(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, userName);
        editor.commit();
    }

    public static String getUserID(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }
}
