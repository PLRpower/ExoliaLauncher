package fr.plrpower.exolia.launcher.auth.mineweb.mc;

import java.util.ArrayList;
import java.util.List;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.plrpower.exolia.launcher.Launcher;
import fr.plrpower.exolia.launcher.auth.mineweb.utils.Get;
import fr.theshark34.openlauncherlib.minecraft.*;

public abstract class MinewebGameType {
    public static final GameType V1_8_HIGHER = new GameType() {
        public String getName() {
            return "1.8 or higher";
        }

        public String getMainClass(GameInfos infos) {return "net.minecraft.client.main.Main";}

        public List<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos) {
            List<String> arguments = new ArrayList();
            arguments.add("--username=" + Get.getSession.getUsername());
            arguments.add("--accessToken");
            arguments.add(Get.getSession.getAccessToken());
            if (Get.getSession.getClientToken() != null) {
                arguments.add("--clientToken");
                arguments.add(Get.getSession.getClientToken());
            }
            arguments.add("--version");
            arguments.add(Launcher.EX_VERSION.getName());
            arguments.add("--gameDir");
            arguments.add(Launcher.EX_DIR.toString());
            arguments.add("--assetsDir");
            arguments.add(Launcher.EX_DIR.resolve(GameFolder.FLOW_UPDATER.getAssetsFolder()).toString());
            arguments.add("--assetIndex");
            arguments.add(getAssetIndex(this, Launcher.EX_VERSION));
            arguments.add("--userProperties");
            arguments.add("{}");
            arguments.add("--uuid");
            arguments.add(Get.getSession.getUuid());
            arguments.add("--userType");
            arguments.add("legacy");
            return arguments;
        }
    };

    private static String getAssetIndex(GameType type, GameVersion gameVersion) {
        String version = Launcher.EX_VERSION.getName();
        int first = version.indexOf(46);
        int second = version.lastIndexOf(46);
        if (first != second) {
            version = version.substring(0, version.lastIndexOf(46));
        }
        return version;
    }

    public MinewebGameType() {
    }

    public abstract String getName();

    public abstract String getMainClass(GameInfos var1);

    public abstract List<String> getLaunchArgs(GameInfos var1, GameFolder var2, AuthInfos var3);


}


