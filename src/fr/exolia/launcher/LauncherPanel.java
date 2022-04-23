package fr.exolia.launcher;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.northenflo.auth.mineweb.AuthMineweb;
import fr.northenflo.auth.mineweb.utils.TypeConnection;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import re.alwyn974.openlauncherlib.util.Saver;
import re.alwyn974.openlauncherlib.util.ramselector.RamSelector;

public class LauncherPanel extends JPanel implements SwingerEventListener {
    private final Image background = Swinger.getResource("background.png");
    private final Saver saver = new Saver(new File(String.valueOf(Launcher.EX_DIR), "launcher.properties"));
    JTextField usernameField = new JTextField(this.saver.get("username"));
    private final JPasswordField passwordField = new JPasswordField();
    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"), Swinger.getResource("playhover.png"), Swinger.getResource("playhover.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"), Swinger.getResource("quithover.png"), Swinger.getResource("quithover.png"));
    private final STexturedButton hideButton = new STexturedButton(Swinger.getResource("hide.png"));
    private final STexturedButton loginButton = new STexturedButton(Swinger.getResource("login.png"), Swinger.getResource("loginhover.png"), Swinger.getResource("login.png"));
    private final STexturedButton signinButton = new STexturedButton(Swinger.getResource("signin.png"), Swinger.getResource("signinhover.png"), Swinger.getResource("signinhover.png"));
    private final STexturedButton youtubeButton = new STexturedButton(Swinger.getResource("youtube.png"), Swinger.getResource("youtubehover.png"), Swinger.getResource("youtubehover.png"));
    private final STexturedButton twitterButton = new STexturedButton(Swinger.getResource("twitter.png"), Swinger.getResource("twitterhover.png"), Swinger.getResource("twitterhover.png"));
    private final STexturedButton instagramButton = new STexturedButton(Swinger.getResource("instagram.png"), Swinger.getResource("instagramhover.png"), Swinger.getResource("instagramhover.png"));
    private final STexturedButton tiktokButton = new STexturedButton(Swinger.getResource("tiktok.png"), Swinger.getResource("tiktokhover.png"), Swinger.getResource("tiktokhover.png"));
    private final STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"), Swinger.getResource("discordhover.png"), Swinger.getResource("discordhover.png"));
    private final STexturedButton siteButton = new STexturedButton(Swinger.getResource("site.png"), Swinger.getResource("sitehover.png"), Swinger.getResource("sitehover.png"));
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("RAM.png"));
    private final RamSelector ramSelector = new RamSelector(new File(String.valueOf(Launcher.EX_DIR), "ram.txt"));
    private final SColoredBar progressBar = new SColoredBar(new Color(0, 0, 0, 0), new Color(44, 209, 115, 255));
    public static JLabel percentLabel = new JLabel("", SwingConstants.CENTER);
    public static JLabel stepLabel = new JLabel("", SwingConstants.CENTER);

    public LauncherPanel() {
        this.setLayout(null);

        usernameField.setForeground(Color.BLACK);
        usernameField.setFont(usernameField.getFont().deriveFont(20F));
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(895, 339, 242, 42);
        this.add(usernameField);

        passwordField.setForeground(Color.BLACK);
        passwordField.setFont(passwordField.getFont().deriveFont(20F));
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(895, 401, 242, 42);
        this.add(passwordField);

        playButton.setBounds(921, 503);
        playButton.addEventListener(this);
        this.add(playButton);

        quitButton.setBounds(1257, 0);
        quitButton.addEventListener(this);
        this.add(quitButton);

        hideButton.setBounds(1234, 0);
        hideButton.addEventListener(this);
        this.add(hideButton);

        loginButton.setBounds(748, 230);
        loginButton.addEventListener(this);
        this.add(loginButton);

        signinButton.setBounds(927, 230);
        signinButton.addEventListener(this);
        this.add(signinButton);

        youtubeButton.setBounds(1221, 660);
        youtubeButton.addEventListener(this);
        this.add(youtubeButton);

        twitterButton.setBounds(1169, 660);
        twitterButton.addEventListener(this);
        this.add(twitterButton);

        instagramButton.setBounds(1112, 660);
        instagramButton.addEventListener(this);
        this.add(instagramButton);

        tiktokButton.setBounds(1057, 660);
        tiktokButton.addEventListener(this);
        this.add(tiktokButton);

        discordButton.setBounds(1002, 660);
        discordButton.addEventListener(this);
        this.add(discordButton);

        siteButton.setBounds(947, 660);
        siteButton.addEventListener(this);
        this.add(siteButton);

        stepLabel.setBounds(95, 630, 321, 29);
        stepLabel.setForeground(Color.WHITE);
        stepLabel.setFont(stepLabel.getFont().deriveFont(18F));
        this.add(stepLabel);

        percentLabel.setBounds(95, 600, 321, 29);
        percentLabel.setForeground(Color.WHITE);
        percentLabel.setFont(percentLabel.getFont().deriveFont(20F));
        this.add(percentLabel);

        progressBar.setBounds(95, 557, 321, 29);
        this.add(progressBar);

        ramButton.setBounds(852,512);
        ramButton.addEventListener(this);
        this.add(ramButton);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == playButton) {
            setFieldsEnabled(false);
            AuthMineweb.setTypeConnection(TypeConnection.launcher);
            AuthMineweb.setUrlRoot("https://exolia.site");
            AuthMineweb.setUsername(usernameField.getText());
            AuthMineweb.setPassword(passwordField.getText());
            try {
                AuthMineweb.start();
            } catch (DataWrongException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, mauvais pseudo ou mot de passe.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            } catch (DataEmptyException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo et un mot de passe valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            }
            catch (ServerNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur, connexion impossible.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur inconnue, réessayez plus tard.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            }
            if (AuthMineweb.isConnected()) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Launcher.updateMinecraftForge();
                        }
                        catch (Exception e) {
                            Launcher.interruptThread();
                            JOptionPane.showMessageDialog(LauncherPanel.this,
                                    "Erreur, impossible de mettre à jour : " + e, "Erreur", JOptionPane.ERROR_MESSAGE);
                            setFieldsEnabled(true);
                            return;
                        }
                        Launcher.getInstance().discord();
                        getRamSelector().save();
                        saver.set("username", usernameField.getText());
                        try {
                            Launcher.launch();
                        }
                        catch (Exception e) {
                            JOptionPane.showMessageDialog(LauncherPanel.this,
                                    "Erreur, impossible de lancer le jeu : " + e, "Erreur", JOptionPane.ERROR_MESSAGE);
                            setFieldsEnabled(true);
                        }
                    }
                };
                t.start();
            }
        }
        else if (e.getSource() == quitButton) {
            System.exit(0);
        }
        else if (e.getSource() == hideButton) {
            LauncherFrame.getinstance().setState(JFrame.ICONIFIED);
        }
        else if (e.getSource() == youtubeButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCZVvJ4mquSagWJV8jV87DGQ"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == twitterButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://twitter.com/Exolia8"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == instagramButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.instagram.com/exoliapvpfaction"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == tiktokButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tiktok.com/@exolia_officiel?"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == discordButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.exolia.site"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == siteButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://exolia.site"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == signinButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://exolia.site/register"));
            } catch (IOException|java.net.URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == this.ramButton) {
            ramSelector.display();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1280, 720, this);
    }

    public void setFieldsEnabled(boolean enabled) {
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
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