package dev.padrewin.premiumpoints.commands;

import dev.padrewin.colddev.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.CommandManager;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import dev.padrewin.premiumpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SetCommand extends PointsCommand {

    public SetCommand() {
        super("set", CommandManager.CommandAliases.SET);
    }

    @Override
    public void execute(PremiumPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 2) {
            localeManager.sendMessage(sender, "command-set-usage");
            return;
        }

        PointsUtils.getPlayerByName(args[0], player -> {
            if (player == null) {
                localeManager.sendMessage(sender, "unknown-player", StringPlaceholders.of("player", args[0]));
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
                if (amount < 0) {
                    localeManager.sendMessage(sender, "invalid-amount");
                    return;
                }
            } catch (NumberFormatException e) {
                localeManager.sendMessage(sender, "invalid-amount");
                return;
            }

            if (plugin.getAPI().set(player.getFirst(), amount)) {
                int newBalance = plugin.getAPI().look(player.getFirst());

                localeManager.sendMessage(sender, "command-set-success", StringPlaceholders.builder("player", player.getSecond())
                        .add("currency", localeManager.getCurrencyName(amount))
                        .add("amount", PointsUtils.formatPoints(amount))
                        .build());

                localeManager.sendMessage(Bukkit.getConsoleSender(), "command-set-log", StringPlaceholders.builder("player", player.getSecond())
                        .add("new_balance", PointsUtils.formatPoints(newBalance))
                        .add("currency", localeManager.getCurrencyName(newBalance))
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
        } else {
            return Collections.emptyList();
        }
    }

}
