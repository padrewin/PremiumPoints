package dev.padrewin.playerpoints.commands;

import dev.padrewin.coldplugin.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import dev.padrewin.playerpoints.PlayerPoints;
import dev.padrewin.playerpoints.manager.CommandManager;
import dev.padrewin.playerpoints.manager.LocaleManager;
import dev.padrewin.playerpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand extends PointsCommand {

    public BroadcastCommand() {
        super("broadcast", CommandManager.CommandAliases.BROADCAST);
    }

    @Override
    public void execute(PlayerPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 1) {
            localeManager.sendMessage(sender, "command-broadcast-usage");
            return;
        }

        PointsUtils.getPlayerByName(args[0], player -> {
            if (player == null) {
                localeManager.sendMessage(sender, "unknown-player", StringPlaceholders.of("player", args[0]));
                return;
            }

            int points = plugin.getAPI().look(player.getFirst());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                localeManager.sendMessage(onlinePlayer, "command-broadcast-message", StringPlaceholders.builder("player", player.getSecond())
                        .add("amount", PointsUtils.formatPoints(points))
                        .add("currency", localeManager.getCurrencyName(points)).build());
            }
        });
    }

    @Override
    public List<String> tabComplete(PlayerPoints plugin, CommandSender sender, String[] args) {
        return args.length != 1 ? Collections.emptyList() : PointsUtils.getPlayerTabComplete(args[0]);
    }

}
