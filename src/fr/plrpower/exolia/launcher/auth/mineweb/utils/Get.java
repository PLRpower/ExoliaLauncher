package fr.plrpower.exolia.launcher.auth.mineweb.utils;

import org.json.JSONException;

public class Get {
    public Get() {
    }

    public static String getUSERNAME() {
        return Core.Username;
    }

    public static String getPASSWORD() {
        return Core.Password;
    }

    public static class getCore {
        public getCore() {
        }

        public static String getURLRoot() {
            return Core.URLRoot;
        }

        public static boolean isConnected() {
            return Core.isConnected;
        }

        public static boolean getDebug() {
            return Core.debug;
        }

        public static void Logger(String Message) {
            Core.log(Message);
        }
    }

    public static class getSession {
        public getSession() {
        }

        public static String getUsername() {
            return StockData.UsernameAuth;
        }

        public static String getUuid() {
            return StockData.UuidAuth;
        }

        public static String getAccessToken() {
            return StockData.accessTokenAuth;
        }

        public static String getClientToken() {
            return StockData.clientTokenAuth;
        }

        public static String getUser(String data) {
            String resultat = "Data Not Found";

            try {
                resultat = StockData.SessionUserRegister.get(data).toString();
            } catch (JSONException var3) {
                System.out.println("La donnée '" + data + "' n'a pas été trouvé");
            }

            return resultat;
        }
    }
}
