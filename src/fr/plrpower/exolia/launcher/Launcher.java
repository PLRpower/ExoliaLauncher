package fr.plrpower.exolia.launcher;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.VersionType;
import fr.plrpower.exolia.launcher.auth.mineweb.mc.MinewebGameTweak;
import fr.plrpower.exolia.launcher.auth.mineweb.mc.MinewebGameType;
import fr.plrpower.exolia.launcher.auth.mineweb.utils.Get;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Launcher {
    public static final GameVersion EX_VERSION = new GameVersion("1.12.2", MinewebGameType.V1_8_HIGHER);
    public static GameInfos EX_INFOS = new GameInfos("ExoliaV2", EX_VERSION, new GameTweak[] {MinewebGameTweak.FORGE });
    public static final Path EX_DIR = EX_INFOS.getGameDir();
    public static final File EX_RAM_FILE = new File("ram.txt");
    private static Thread updateThread;
    public static AuthInfos authInfos = new AuthInfos(Get.getSession.getUsername(), Get.getSession.getUuid(), Get.getSession.getClientToken());

    public static void updateMinecraftForge() {
        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

            public void init(ILogger logger) {}

            public void step(Step step) {
                LauncherFrame.getinstance().getLauncherPanel().setStepText(Launcher.StepInfo.valueOf(step.name()).getDetails());
            }

            public void update(long downloaded, long max) {
                LauncherFrame.getinstance().getLauncherPanel().setPercentText(this.decimalFormat.format(downloaded * 100.0D / max) + "%");
                LauncherFrame.getinstance().getLauncherPanel().getProgressBar().setMaximum((int)max);
                LauncherFrame.getinstance().getLauncherPanel().getProgressBar().setValue((int)downloaded);
            }
        };
        try {
            VanillaVersion version = (new VanillaVersion.VanillaVersionBuilder())
                    .withName("1.12.2")
                    .withVersionType(VersionType.FORGE)
                    .build();
            Logger logger = new Logger("[Exolia]", null);
            AbstractForgeVersion forge = (new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW))
                    .withForgeVersion("1.12.2-14.23.5.2860")
                    .withMods(Mod.getModsFromJson("https://exolia.site/app/webroot/mods.json"))
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();
            FlowUpdater updater = (new FlowUpdater.FlowUpdaterBuilder())
                    .withVanillaVersion(version)
                    .withLogger(logger)
                    .withProgressCallback(callback)
                    .withForgeVersion(forge)
                    .withExternalFiles(ExternalFile.getExternalFilesFromJson("https://exolia.site/app/webroot/externalfiles.json"))
                    .build();
            updater.update(EX_DIR);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void launch() throws LaunchException {
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(EX_INFOS, GameFolder.FLOW_UPDATER, authInfos);
        profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getinstance().getLauncherPanel().getRamSelector().getRamArguments()));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        Process p = launcher.launch();
        try {
            Thread.sleep(5000L);
            LauncherFrame.getinstance().setVisible(false);
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void interruptThread() {
        updateThread.interrupt();
    }

    private static final Launcher instance = new Launcher();

    public static Launcher getInstance() {
        return instance;
    }

    public enum StepInfo {
        INTEGRATION("Installation des integrations..."),
        READ("Lecture du json..."),
        DL_LIBS("Tdes libraries..."),
        DL_ASSETS("Tdes assets..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        FORGE("Installation de forge..."),
        FABRIC("Installing fabric..."),
        MODS("Tdes mods..."),
        EXTERNAL_FILES("Tdes fichiers externes..."),
        POST_EXECUTIONS("Vdes fichiers..."),
        END("Fini !");

        final String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return this.details;
        }
    }

    public void discord() {
        discordRPC();
    }

    public void discordRPC() {
        DiscordRPC discord = DiscordRPC.INSTANCE;
        discord.Discord_Initialize("886223409015570432", new DiscordEventHandlers(), true, "");
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.largeImageKey = "image";
        presence.largeImageText = "exolia.site";
        presence.details = "Serveur Minecraft PVP-Faction";
        presence.state = "Rejoins-Nous ! exolia.site";
        discord.Discord_UpdatePresence(presence);
    }
}
