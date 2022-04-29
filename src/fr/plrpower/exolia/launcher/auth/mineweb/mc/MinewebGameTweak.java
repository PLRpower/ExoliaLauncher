package fr.plrpower.exolia.launcher.auth.mineweb.mc;

import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;

public abstract class MinewebGameTweak {
    public static final String LAUNCHWRAPPER_MAIN_CLASS = "net.minecraft.launchwrapper.Launch";
    public static final GameTweak FORGE = new GameTweak() {
        public String getName() {
            return "FML Tweaker";
        }

        public String getTweakClass(GameInfos infos) {
            return infos.getGameVersion().getGameType().equals(MinewebGameType.V1_8_HIGHER) ? "net.minecraftforge.fml.common.launcher.FMLTweaker" : "cpw.mods.fml.common.launcher.FMLTweaker";
        }
    };
    public static final GameTweak OPTIFINE = new GameTweak() {
        public String getName() {
            return "Optifine Tweaker";
        }

        public String getTweakClass(GameInfos infos) {
            return "optifine.OptiFineTweaker";
        }
    };
    public static final GameTweak SHADER = new GameTweak() {
        public String getName() {
            return "Shader Tweaker";
        }

        public String getTweakClass(GameInfos infos) {
            return infos.getGameVersion().getName().contains("1.8") ? "shadersmod.launch.SMCTweaker" : "shadersmodcore.loading.SMCTweaker";
        }
    };

    public MinewebGameTweak() {
    }

    public abstract String getName();

    public abstract String getTweakClass(GameInfos var1);
}
