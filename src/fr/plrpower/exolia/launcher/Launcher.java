package fr.plrpower.exolia.launcher;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.azuriom.azauth.AuthenticationException;
import com.azuriom.azauth.AzAuthenticator;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Launcher {

    public static final GameVersion EX_VERSION = new GameVersion("1.16.5", GameType.V1_13_HIGHER_FORGE.setNFVD(new NewForgeVersionDiscriminator("36.2.34", "1.16.5", "20210115.111550")));
    public static final GameInfos EX_INFOS = new GameInfos("Osalys", GameDirGenerator.createGameDir("Osalys", true), EX_VERSION, null);
    public static final Path EX_DIR = EX_INFOS.getGameDir();
    public static final File EX_RAM_FILE = new File("ram.txt");
    public static AuthInfos authInfos;

    private static Thread updateThread;
    public static void interruptThread() {
        updateThread.interrupt();
    }

    public static void auth(String username, String password) throws AuthenticationException, IOException {
        AzAuthenticator authenticator = new AzAuthenticator("https://gracious-joliot.135-125-34-212.plesk.page");
        authInfos = authenticator.authenticate(username, password, AuthInfos.class);
    }

    private static final Launcher instance = new Launcher();
    public static Launcher getInstance() {
        return instance;
    }

    public static void updateMinecraftForge() {
        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

            public void init(ILogger logger) {}

            public void step(Step step) {
                LauncherFrame.getInstance().getLauncherPanel().setStepText(Launcher.StepInfo.valueOf(step.name()).getDetails());
            }

            public void update(long downloaded, long max) {
                LauncherFrame.getInstance().getLauncherPanel().setPercentText(this.decimalFormat.format(downloaded * 100.0D / max) + "%");
                LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum((int)max);
                LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue((int)downloaded);
            }
        };
        try {
            VanillaVersion version = (new VanillaVersion.VanillaVersionBuilder())
                    .withName("1.16.5")
                    .build();
            Logger logger = new Logger("[Osalys]", null);
            AbstractForgeVersion forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                    .withForgeVersion("36.2.35")
                    //.withMods(Mod.getModsFromJson("https://exolia.site/app/webroot/mods.json"))
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();
            FlowUpdater updater = (new FlowUpdater.FlowUpdaterBuilder())
                    .withVanillaVersion(version)
                    .withLogger(logger)
                    .withProgressCallback(callback)
                    .withModLoaderVersion(forge)
                    .withExternalFiles(ExternalFile.getExternalFilesFromJson("https://exolia.site/app/webroot/externalfiles.json"))
                    .build();
            updater.update(EX_DIR);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void launch() throws LaunchException {
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(EX_INFOS, GameFolder.FLOW_UPDATER, authInfos);
        profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        Process p = launcher.launch();
        try {
            Thread.sleep(5000L);
            LauncherFrame.getInstance().setVisible(false);
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public enum StepInfo {
        INTEGRATION("Installation des integrations..."),
        READ("Lecture du json..."),
        DL_LIBS("Téléchargement des libraries..."),
        DL_ASSETS("Téléchargement des assets..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        FORGE("Installation de forge..."),
        MODS("Téléchargement des mods..."),
        EXTERNAL_FILES("Téléchargement des fichiers externes..."),
        POST_EXECUTIONS("Vérification des fichiers..."),
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
