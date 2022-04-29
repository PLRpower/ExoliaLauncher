package fr.plrpower.exolia.launcher.auth.mineweb;

import java.io.IOException;

import fr.plrpower.exolia.launcher.auth.exception.DataEmptyException;
import fr.plrpower.exolia.launcher.auth.exception.DataWrongException;
import fr.plrpower.exolia.launcher.auth.exception.ServerNotFoundException;
import fr.plrpower.exolia.launcher.auth.mineweb.utils.*;
import fr.theshark34.openlauncherlib.util.LogUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthMineweb {

    public AuthMineweb() {
    }

    public static void start() throws DataWrongException, DataEmptyException, ServerNotFoundException, IOException {
        LogUtil.info("mineweb-enable");
        String strTpConnect = String.valueOf(Core.tpConnect);
        if (strTpConnect.equalsIgnoreCase("0")) {
            Core.log("Vous devez définir un type de connection. Référencez-vous à la documentation.");
        } else {
            if (strTpConnect.equalsIgnoreCase(String.valueOf(TypeConnection.launcher))) {
                Core.log("Démarrage de l'authentification vers " + fr.plrpower.exolia.launcher.auth.mineweb.utils.Get.getCore.getURLRoot());
                Core.log("Débug connect:" + Core.URLRoot + "/auth/start?username=" + Get.getUSERNAME() + "&password=" + Get.getPASSWORD());
                Core.log("Pass Param: USER{" + Get.getUSERNAME() + "} && PWD:{" + Get.getPASSWORD() + "}");
                String resultAuthServer = GetData.getData(Core.URLRoot + "/auth/start?username=" + Get.getUSERNAME() + "&password=" + Get.getPASSWORD());
                if (!resultAuthServer.equalsIgnoreCase("success_ok")) {
                    if (resultAuthServer.equalsIgnoreCase("error_password")) {
                        Core.log("Response Server: " + resultAuthServer);
                        throw new DataWrongException("error_data");
                    }

                    if (resultAuthServer.equalsIgnoreCase("Empty Get")) {
                        Core.log("Response Server: " + resultAuthServer);
                        throw new DataEmptyException("empty_get");
                    }

                    Core.log("Response Server: " + resultAuthServer);
                    throw new ServerNotFoundException("server_not_found");
                }

                Core.log("Response Server: " + resultAuthServer);
                Core.isConnected = true;
                RegisterData.StartRegisterSessionLauncher();
                Core.log("===============================================");
                Core.log("Données enregistrées:");
                Core.log("Pseudo: " + Get.getSession.getUsername());
                Core.log("UUID: " + Get.getSession.getUuid());
                Core.log("AccessToken: " + Get.getSession.getAccessToken());
                Core.log("ClientToken: " + Get.getSession.getClientToken());
                Core.log("===============================================");
            } else {
                try {
                    RegisterData.StartRegisterSessionIngame();
                    Core.log("===============================================");
                    Core.log("Données enregistrées:");
                    Core.log("Pseudo: " + fr.plrpower.exolia.launcher.auth.mineweb.utils.Get.getSession.getUsername());
                    Core.log("UUID: " + fr.plrpower.exolia.launcher.auth.mineweb.utils.Get.getSession.getUuid());
                    Core.log("AccessToken: " + fr.plrpower.exolia.launcher.auth.mineweb.utils.Get.getSession.getAccessToken());
                    Core.log("ClientToken: " + fr.plrpower.exolia.launcher.auth.mineweb.utils.Get.getSession.getClientToken());
                    Core.log("===============================================");
                } catch (Exception var2) {
                    throw new DataWrongException("error_data");
                }
            }
        }
    }

    public static void setUrlRoot(String URL) {Core.URLRoot = URL;}

    public static void setUsername(String Username) {Core.Username = Username;}

    public static void setTypeConnection(int typeConnection) {Core.tpConnect = typeConnection;}

    public static void setPassword(String Password) {
        Password = DigestUtils.sha256Hex(Password);
        Core.Password = Password;
    }

    public static boolean isConnected() {return Core.isConnected;}
}
