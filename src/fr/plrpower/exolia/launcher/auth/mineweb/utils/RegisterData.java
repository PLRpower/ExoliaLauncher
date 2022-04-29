package fr.plrpower.exolia.launcher.auth.mineweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import fr.plrpower.exolia.launcher.auth.exception.DataWrongException;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterData {
    public RegisterData() {
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URLConnection connection = (new URL(url)).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = readAll(rd);
        JSONObject json = new JSONObject(jsonText);
        return json;
    }

    public static void StartRegisterSessionLauncher() {
        try {
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister = readJsonFromUrl(Get.getCore.getURLRoot() + "/auth/getDataLauncher?username=" + fr.plrpower.exolia.launcher.auth.mineweb.utils.Core.Username + "&password=" + fr.plrpower.exolia.launcher.auth.mineweb.utils.Core.Password);
        } catch (IOException var2) {
            var2.printStackTrace();
        } catch (JSONException var3) {
            var3.printStackTrace();
        }

        try {
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.UsernameAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("pseudo").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.UuidAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("auth-uuid").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.accessTokenAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("auth-accessToken").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.clientTokenAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("auth-clientToken").toString();
        } catch (JSONException var1) {
            var1.printStackTrace();
        }

    }

    public static void StartRegisterSessionIngame() throws DataWrongException {
        try {
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister = readJsonFromUrl(Get.getCore.getURLRoot() + "/auth/getDataIngame?accessToken=" + Core.accessToken);
        } catch (IOException var2) {
            var2.printStackTrace();
        } catch (JSONException var3) {
            throw new DataWrongException("error_data");
        }

        try {
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.UsernameAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("pseudo").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.UuidAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("auth-uuid").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.accessTokenAuth = fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.SessionUserRegister.get("auth-accessToken").toString();
            fr.plrpower.exolia.launcher.auth.mineweb.utils.StockData.clientTokenAuth = StockData.SessionUserRegister.get("auth-clientToken").toString();
        } catch (JSONException var1) {
            var1.printStackTrace();
        }

    }
}
