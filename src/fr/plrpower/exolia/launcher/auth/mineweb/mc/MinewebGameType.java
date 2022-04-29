package fr.plrpower.exolia.launcher.auth.mineweb.mc;


import java.io.File;
import java.util.ArrayList;

import fr.plrpower.exolia.launcher.auth.mineweb.utils.Get;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameType;

public abstract class MinewebGameType {
    public static final GameType V1_8_HIGHER = new GameType() {
        public String getName() {
            return "1.8 or higher";
        }

        public String getMainClass(GameInfos infos) {
            return "net.minecraft.client.main.Main";
        }

        public ArrayList<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos) {
            ArrayList<String> arguments = new ArrayList();
            arguments.add("--username=" + Get.getSession.getUsername());
            arguments.add("--accessToken");
            arguments.add(Get.getSession.getAccessToken());
            arguments.add("--clientToken");
            arguments.add(Get.getSession.getClientToken());
            arguments.add("--version");
            arguments.add(infos.getGameVersion().getName());
            arguments.add("--gameDir");
            arguments.add(String.valueOf(infos.getGameDir()));
            arguments.add("--assetsDir");
            File assetsDir = new File(String.valueOf(infos.getGameDir()), folder.getAssetsFolder());
            arguments.add(assetsDir.getAbsolutePath());
            arguments.add("--assetIndex");
            String version = infos.getGameVersion().getName();
            int first = version.indexOf(46);
            int second = version.lastIndexOf(46);
            if (first != second) {
                version = version.substring(0, version.lastIndexOf(46));
            }

            arguments.add(version);
            arguments.add("--userProperties");
            arguments.add("{}");
            arguments.add("--uuid");
            arguments.add(Get.getSession.getUuid());
            arguments.add("--userType");
            arguments.add("legacy");
            return arguments;
        }
    };

    public MinewebGameType() {
    }

    public abstract String getName();

    public abstract String getMainClass(GameInfos var1);

    public abstract ArrayList<String> getLaunchArgs(GameInfos var1, GameFolder var2, AuthInfos var3);
}
