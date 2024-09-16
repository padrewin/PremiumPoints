package dev.padrewin.premiumpoints.commands;

import dev.padrewin.coldplugin.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.CommandManager;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import dev.padrewin.premiumpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends PointsCommand {

    public GiveCommand() {
        super("give", CommandManager.CommandAliases.GIVE);
    }

    @Override
    public void execute(PremiumPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 2) {
            localeManager.sendMessage(sender, "command-give-usage");
            return;
        }

        boolean silent = args.length > 2 && args[args.length - 1].equalsIgnoreCase("-s");

        PointsUtils.getPlayerByName(args[0], player -> {
            if (player == null) {
                localeManager.sendMessage(sender, "unknown-player", StringPlaceholders.of("player", args[0]));
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                localeManager.sendMessage(sender, "invalid-amount");
                return;
            }

            if (amount <= 0) {
                localeManager.sendMessage(sender, "invalid-amount");
                return;
            }

            if (plugin.getAPI().give(player.getFirst(), amount)) {
                Player onlinePlayer = Bukkit.getPlayer(player.getFirst());
                if (onlinePlayer != null && !silent) {
                    localeManager.sendMessage(onlinePlayer, "command-give-received", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
                            .add("currency", localeManager.getCurrencyName(amount))
                            .build());
                }

                localeManager.sendMessage(sender, "command-give-success", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
                        .add("currency", localeManager.getCurrencyName(amount))
                        .add("player", player.getSecond())
                        .build());
            }
        });
    }

    @Override
    public List<String> tabComplete(PremiumPoints plugin, CommandSender sender, String[] args) {
        if (args.length == 1) {
            return PointsUtils.getPlayerTabComplete(args[0]);
        } else if (args.length == 2) {
            return Collections.singletonList("<amount>");
        } else if (args.length == 3) {
            return Collections.singletonList("-s");
        } else {
            return Collections.emptyList();
        }
    }

}
