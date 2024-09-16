package dev.padrewin.premiumpoints.listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import dev.padrewin.coldplugin.utils.StringPlaceholders;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import dev.padrewin.premiumpoints.setting.SettingKey;
import dev.padrewin.premiumpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    private final PremiumPoints plugin;

    public VotifierListener(PremiumPoints plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void vote(VotifierEvent event) {
        if (event.getVote().getUsername() == null)
            return;

        String name = event.getVote().getUsername();
        PointsUtils.getPlayerByName(name, playerInfo -> {
            if (playerInfo == null)
                return;

            int amount = SettingKey.VOTE_AMOUNT.get();
            Player player = Bukkit.getPlayer(playerInfo.getFirst());

            if (!SettingKey.VOTE_ONLINE.get() || player != null) {
                this.plugin.getAPI().give(playerInfo.getFirst(), amount);
                if (player != null)
                    this.plugin.getManager(LocaleManager.class).sendMessage(player, "votifier-voted", StringPlaceholders.builder("service", event.getVote().getServiceName())
                            .add("amount", SettingKey.VOTE_AMOUNT.get())
                            .build());
            }
        });
    }
}
