package dev.padrewin.playerpoints.commands;

import dev.padrewin.coldplugin.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import dev.padrewin.playerpoints.PlayerPoints;
import dev.padrewin.playerpoints.manager.CommandManager;
import dev.padrewin.playerpoints.manager.DataManager;
import dev.padrewin.playerpoints.manager.LocaleManager;
import dev.padrewin.playerpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAllCommand extends PointsCommand {

    public GiveAllCommand() {
        super("giveall", CommandManager.CommandAliases.GIVEALL);
    }

    @Override
    public void execute(PlayerPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 1) {
            localeManager.sendMessage(sender, "command-giveall-usage");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            localeManager.sendMessage(sender, "invalid-amount");
            return;
        }

        boolean includeOffline = args.length > 1 && args[1].equals("*");
        boolean silent = args.length > 2 && args[args.length - 1].equalsIgnoreCase("-s");

        plugin.getScheduler().runTaskAsync(() -> {
            boolean success;
            if (includeOffline) {
                success = plugin.getManager(DataManager.class).offsetAllPoints(amount);
            } else {
                List<UUID> playerIds = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList());
                success = plugin.getAPI().giveAll(playerIds, amount);
            }

            if (success) {
                if (!silent) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        localeManager.sendMessage(player, "command-give-received", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
                                .add("currency", localeManager.getCurrencyName(amount))
                                .build());
                    }
                }

                localeManager.sendMessage(sender, "command-giveall-success", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
                        .add("currency", localeManager.getCurrencyName(amount))
                        .build());
            }
        });
    }

    @Override
    public List<String> tabComplete(PlayerPoints plugin, CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                return Collections.singletonList("<amount>");
            case 2:
                return Collections.singletonList("*");
            default:
                return Collections.emptyList();
        }
    }

}
