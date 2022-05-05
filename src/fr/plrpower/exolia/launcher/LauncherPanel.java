package fr.plrpower.exolia.launcher;

import fr.exolia.auth.exception.DataEmptyException;
import fr.exolia.auth.exception.DataWrongException;
import fr.exolia.auth.exception.ServerNotFoundException;
import fr.exolia.auth.mineweb.AuthMineweb;
import fr.exolia.auth.mineweb.utils.TypeConnection;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.swing.*;

public class LauncherPanel extends JPanel implements SwingerEventListener {

    private final Image background = Swinger.getResource("background.png");
    private final JPasswordField passwordField = new JPasswordField();
    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"), Swinger.getResource("playhover.png"), Swinger.getResource("playhover.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"), Swinger.getResource("quithover.png"), Swinger.getResource("quithover.png"));
    private final STexturedButton hideButton = new STexturedButton(Swinger.getResource("hide.png"));
    private final STexturedButton signinButton = new STexturedButton(Swinger.getResource("signin.png"), Swinger.getResource("signinhover.png"), Swinger.getResource("signinhover.png"));
    private final STexturedButton youtubeButton = new STexturedButton(Swinger.getResource("youtube.png"), Swinger.getResource("youtubehover.png"), Swinger.getResource("youtubehover.png"));
    private final STexturedButton twitterButton = new STexturedButton(Swinger.getResource("twitter.png"), Swinger.getResource("twitterhover.png"), Swinger.getResource("twitterhover.png"));
    private final STexturedButton instagramButton = new STexturedButton(Swinger.getResource("instagram.png"), Swinger.getResource("instagramhover.png"), Swinger.getResource("instagramhover.png"));
    private final STexturedButton tiktokButton = new STexturedButton(Swinger.getResource("tiktok.png"), Swinger.getResource("tiktokhover.png"), Swinger.getResource("tiktokhover.png"));
    private final STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"), Swinger.getResource("discordhover.png"), Swinger.getResource("discordhover.png"));
    private final STexturedButton siteButton = new STexturedButton(Swinger.getResource("site.png"), Swinger.getResource("sitehover.png"), Swinger.getResource("sitehover.png"));
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("RAM.png"));

    private final RamSelector ramSelector = new RamSelector(Launcher.EX_RAM_FILE);
    private final Saver saver = new Saver(new File(String.valueOf(Launcher.EX_DIR), "launcher.properties"));
    JTextField usernameField = new JTextField(this.saver.get("username"));

    public SColoredBar progressBar = new SColoredBar(new Color(0, 0, 0, 0), new Color(44, 209, 115, 255));
    public static JLabel percentLabel = new JLabel("", SwingConstants.CENTER);
    public static JLabel stepLabel = new JLabel("", SwingConstants.CENTER);

    public LauncherPanel() {
        setLayout(null);

        this.usernameField.setForeground(Color.BLACK);
        this.usernameField.setFont(this.usernameField.getFont().deriveFont(20.0F));
        this.usernameField.setCaretColor(Color.BLACK);
        this.usernameField.setOpaque(false);
        this.usernameField.setBorder(null);
        this.usernameField.setBounds(895, 339, 242, 42);
        add(this.usernameField);

        this.passwordField.setForeground(Color.BLACK);
        this.passwordField.setFont(this.passwordField.getFont().deriveFont(20.0F));
        this.passwordField.setCaretColor(Color.BLACK);
        this.passwordField.setOpaque(false);
        this.passwordField.setBorder(null);
        this.passwordField.setBounds(895, 401, 242, 42);
        add(this.passwordField);

        this.playButton.setBounds(921, 503);
        this.playButton.addEventListener(this);
        add(this.playButton);

        this.quitButton.setBounds(1257, 0);
        this.quitButton.addEventListener(this);
        add(this.quitButton);

        this.hideButton.setBounds(1234, 0);
        this.hideButton.addEventListener(this);
        add(this.hideButton);

        STexturedButton loginButton = new STexturedButton(Swinger.getResource("login.png"), Swinger.getResource("loginhover.png"), Swinger.getResource("login.png"));
        loginButton.setBounds(748, 230);
        loginButton.addEventListener(this);
        add(loginButton);

        this.signinButton.setBounds(927, 230);
        this.signinButton.addEventListener(this);
        add(this.signinButton);

        this.youtubeButton.setBounds(1221, 660);
        this.youtubeButton.addEventListener(this);
        add(this.youtubeButton);

        this.twitterButton.setBounds(1169, 660);
        this.twitterButton.addEventListener(this);
        add(this.twitterButton);

        this.instagramButton.setBounds(1112, 660);
        this.instagramButton.addEventListener(this);
        add(this.instagramButton);

        this.tiktokButton.setBounds(1057, 660);
        this.tiktokButton.addEventListener(this);
        add(this.tiktokButton);

        this.discordButton.setBounds(1002, 660);
        this.discordButton.addEventListener(this);
        add(this.discordButton);

        this.siteButton.setBounds(947, 660);
        this.siteButton.addEventListener(this);
        add(this.siteButton);

        stepLabel.setBounds(95, 630, 321, 29);
        stepLabel.setForeground(Color.WHITE);
        stepLabel.setFont(stepLabel.getFont().deriveFont(18.0F));
        add(stepLabel);

        percentLabel.setBounds(95, 600, 321, 29);
        percentLabel.setForeground(Color.WHITE);
        percentLabel.setFont(percentLabel.getFont().deriveFont(20.0F));
        add(percentLabel);

        this.progressBar.setBounds(95, 557, 321, 29);
        add(this.progressBar);
        this.ramButton.setBounds(852, 512);
        this.ramButton.addEventListener(this);
        add(this.ramButton);
    }

    public void onEvent(SwingerEvent e) {
        if (e.getSource() == this.playButton) {
            setFieldsEnabled(false);
            AuthMineweb.setTypeConnection(TypeConnection.launcher);
            AuthMineweb.setUrlRoot("https://exolia.site");
            AuthMineweb.setUsername(this.usernameField.getText());
            AuthMineweb.setPassword(String.valueOf(this.passwordField.getPassword()));
            try {
                AuthMineweb.start();
            } catch (DataWrongException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, mauvais pseudo ou mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            } catch (DataEmptyException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo et un mot de passe valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            } catch (ServerNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, connexion impossible.", "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur inconnue, essayez plus tard.", "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            }
            if (AuthMineweb.isConnected()) {
                Thread t = new Thread(() -> {
                    try {
                        Launcher.updateMinecraftForge();
                    } catch (Exception e12) {
                        Launcher.interruptThread();
                        JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de mettre jour : " + e12, "Erreur", JOptionPane.ERROR_MESSAGE);
                        LauncherPanel.this.setFieldsEnabled(true);
                        return;
                    }
                    Launcher.getInstance().discord();
                    LauncherPanel.this.getRamSelector().save();
                    LauncherPanel.this.saver.set("username", LauncherPanel.this.usernameField.getText());
                    try {
                        Launcher.launch();
                    } catch (Exception e12) {
                        JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de lancer le jeu : " + e12, "Erreur", JOptionPane.ERROR_MESSAGE);
                        LauncherPanel.this.setFieldsEnabled(true);
                    }
                });
                t.start();
            }
        } else if (e.getSource() == this.quitButton) {
            System.exit(0);
        } else if (e.getSource() == this.hideButton) {
            LauncherFrame.getInstance().setState(1);
        } else if (e.getSource() == this.youtubeButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCZVvJ4mquSagWJV8jV87DGQ"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.twitterButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://twitter.com/Exolia8"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.instagramButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.instagram.com/exoliapvpfaction/"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.tiktokButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tiktok.com/@exolia_officiel/"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.discordButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.exolia.site"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.siteButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://exolia.site"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.signinButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://exolia.site/register"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.ramButton) {
            this.ramSelector.display();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, 1280, 720, this);
    }

    public void setFieldsEnabled(boolean enabled) {
        this.usernameField.setEnabled(enabled);
        this.passwordField.setEnabled(enabled);
        this.playButton.setEnabled(enabled);
    }

    public SColoredBar getProgressBar() {
        return this.progressBar;
    }

    public void setPercentText(String text) {
        percentLabel.setText(text);
    }

    public void setStepText(String text) {
        stepLabel.setText(text);
    }

    public RamSelector getRamSelector() {
        return this.ramSelector;
    }
}
