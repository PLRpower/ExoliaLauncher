package fr.plrpower.exolia.launcher.auth.mineweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class GetData {
    static URL oracle = null;
    static URLConnection yc = null;
    static BufferedReader in = null;
    static String inputLine;
    public static String Result;

    public GetData() {
    }

    public static String getData(String URL) throws IOException {
        URLConnection connection = (new URL(URL)).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        InputStream is = connection.getInputStream();

        BufferedReader in;
        for(in = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))); (inputLine = in.readLine()) != null; Result = inputLine) {
        }

        in.close();
        return Result;
    }
}
