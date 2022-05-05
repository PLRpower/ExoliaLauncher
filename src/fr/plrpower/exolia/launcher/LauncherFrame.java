package fr.plrpower.exolia.launcher;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;

public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;
    private final LauncherPanel launcherPanel;

    public static LauncherFrame getInstance() {
        return instance;
    }
    public LauncherPanel getLauncherPanel() {
        return this.launcherPanel;
    }

    public LauncherFrame() {
        setTitle("Exolia Launcher");
        setSize(1280, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(Swinger.getResource("icon.png"));
        setContentPane(this.launcherPanel = new LauncherPanel());
        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        setVisible(true);
    }

    public static void main(String[] args) {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/fr/plrpower/exolia/launcher/resources/");
        instance = new LauncherFrame();
    }
}
