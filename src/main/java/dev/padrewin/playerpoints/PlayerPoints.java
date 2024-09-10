package dev.padrewin.playerpoints;

import dev.padrewin.coldplugin.ColdPlugin;
import dev.padrewin.coldplugin.config.ColdSetting;
import dev.padrewin.coldplugin.manager.Manager;
import java.util.Arrays;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import dev.padrewin.playerpoints.hook.PointsPlaceholderExpansion;
import dev.padrewin.playerpoints.listeners.PointsMessageListener;
import dev.padrewin.playerpoints.listeners.VotifierListener;
import dev.padrewin.playerpoints.manager.CommandManager;
import dev.padrewin.playerpoints.manager.DataManager;
import dev.padrewin.playerpoints.manager.LeaderboardManager;
import dev.padrewin.playerpoints.manager.LocaleManager;
import dev.padrewin.playerpoints.setting.SettingKey;
import dev.padrewin.playerpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

/**
 * Main plugin class for PlayerPoints.
 */
public class PlayerPoints extends ColdPlugin {

    private static PlayerPoints instance;
    private PlayerPointsAPI api;
    private PlayerPointsVaultLayer vaultLayer;

    public PlayerPoints() {
        super(80745, 10234, DataManager.class, LocaleManager.class, null);
        instance = this;
    }

    @Override
    public void enable() {
        this.api = new PlayerPointsAPI(this);

        if (SettingKey.VAULT.get() && Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            this.vaultLayer = new PlayerPointsVaultLayer(this);

            // Check valid values for the priorities
            ServicePriority priority = null;
            String desiredPriority = SettingKey.VAULT_PRIORITY.get();
            for (ServicePriority value : ServicePriority.values()) {
                if (value.name().equalsIgnoreCase(desiredPriority)) {
                    priority = value;
                    break;
                }
            }

            if (priority == null) {
                this.getLogger().warning("vault-priority value in the config.yml is invalid, defaulting to Low.");
                priority = ServicePriority.Low;
            }

            Bukkit.getServicesManager().register(Economy.class, this.vaultLayer, this, priority);
        }

        if (SettingKey.BUNGEECORD_SEND_UPDATES.get()) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, PointsMessageListener.CHANNEL);
            Bukkit.getMessenger().registerIncomingPluginChannel(this, PointsMessageListener.CHANNEL, new PointsMessageListener(this));
        }

        this.getScheduler().runTask(() -> {
            // Register placeholders, if applicable
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                new PointsPlaceholderExpansion(this).register();

            // Register votifier listener, if applicable
            if (SettingKey.VOTE_ENABLED.get()) {
                Plugin votifier = Bukkit.getPluginManager().getPlugin("Votifier");
                if (votifier != null) {
                    Bukkit.getPluginManager().registerEvents(new VotifierListener(this), this);
                } else {
                    this.getLogger().warning("The hook for Votifier was enabled, but it does not appear to be installed.");
                }
            }
        });
    }

    @Override
    public void disable() {
        if (this.vaultLayer != null)
            Bukkit.getServicesManager().unregister(Economy.class, this.vaultLayer);

        if (SettingKey.BUNGEECORD_SEND_UPDATES.get()) {
            Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
            Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
        }
    }

    @Override
    public void reload() {
        super.reload();
        PointsUtils.setCachedValues(this);
    }

    @Override
    protected List<Class<? extends Manager>> getManagerLoadPriority() {
        return Arrays.asList(
                CommandManager.class,
                LeaderboardManager.class
        );
    }

    @Override
    protected List<ColdSetting<?>> getColdConfigSettings() {
        return SettingKey.getKeys();
    }

    @Override
    protected String[] getColdConfigHeader() {
        return new String[] {
                "   ____ ___  _     ____  ",
                "  / ___/ _ \\| |   |  _ \\ ",
                " | |  | | | | |   | | | |",
                " | |__| |_| | |___| |_| |",
                "  \\____\\___/|_____|____/ ",
                "                         "
        };
    }

    public static PlayerPoints getInstance() {
        return instance;
    }

    /**
     * Get the plugin's API.
     *
     * @return API instance.
     */
    public PlayerPointsAPI getAPI() {
        return this.api;
    }

}
