package fr.plrpower.exolia.launcher.auth.mineweb.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadProfile {
    private static BufferedReader br;
    private static FileReader fr;
    static JSONObject RegisterSession;
    private static ArrayList<String> wholeList;

    public ReadProfile() {
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(File gameDir) throws IOException, JSONException {
        fr = new FileReader(new File(gameDir + "/launcher_profiles.json"));
        br = new BufferedReader(fr);
        String jsonText = readAll(br);
        JSONObject json = new JSONObject(jsonText);
        return json;
    }

    public static JSONArray LoadProfiles(File gameDir) throws JSONException {
        wholeList = new ArrayList();

        try {
            RegisterSession = readJsonFromUrl(gameDir);
        } catch (JSONException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        JSONObject Players = RegisterSession.getJSONObject("Users");
        Iterator<?> x = Players.keys();
        JSONArray jsonArray = new JSONArray();

        while(x.hasNext()) {
            String key = (String)x.next();
            jsonArray.put(Players.get(key));
        }

        return jsonArray;
    }
}
