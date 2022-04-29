package fr.plrpower.exolia.launcher.auth.mineweb.utils;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckConnectionIG {
    private static JSONObject json_AccessTokenGetWeb;

    public CheckConnectionIG() {
    }

    public static boolean isConnect() {
        boolean isConnect = false;
        String str_AccessTokenRegister = Get.getSession.getAccessToken();
        String str_AccessTokenGetWeb = null;

        try {
            json_AccessTokenGetWeb = RegisterData.readJsonFromUrl(Get.getCore.getURLRoot() + "/auth/getDataIngame?accessToken=" + Get.getSession.getAccessToken());
        } catch (IOException var5) {
            var5.printStackTrace();
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        try {
            str_AccessTokenGetWeb = json_AccessTokenGetWeb.get("auth-accessToken").toString();
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

        if (str_AccessTokenRegister.equalsIgnoreCase(str_AccessTokenGetWeb)) {
            isConnect = true;
        }

        return isConnect;
    }
}