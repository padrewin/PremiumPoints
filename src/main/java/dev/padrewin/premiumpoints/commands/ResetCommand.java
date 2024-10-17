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

public class ResetCommand extends PointsCommand {

    public ResetCommand() {
        super("reset", CommandManager.CommandAliases.RESET);
    }

    @Override
    public void execute(PremiumPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 1) {
            localeManager.sendMessage(sender, "command-reset-usage");
            return;
        }

        PointsUtils.getPlayerByName(args[0], player -> {
            if (player == null) {
                localeManager.sendMessage(sender, "unknown-player", StringPlaceholders.of("player", args[0]));
                return;
            }

            int oldBalance = plugin.getAPI().look(player.getFirst());

            if (plugin.getAPI().reset(player.getFirst())) {
                int newBalance = plugin.getAPI().look(player.getFirst());

                localeManager.sendMessage(sender, "command-reset-success", StringPlaceholders.builder("player", player.getSecond())
                        .add("currency", localeManager.getCurrencyName(0))
                        .build());

                localeManager.sendMessage(Bukkit.getConsoleSender(), "command-reset-log", StringPlaceholders.builder("player", player.getSecond())
                        .add("new_balance", PointsUtils.formatPoints(newBalance))
                        .add("currency", localeManager.getCurrencyName(newBalance))
                        .build());
            }
        });
    }


    @Override
    public List<String> tabComplete(PremiumPoints plugin, CommandSender sender, String[] args) {
        return args.length == 1 ? PointsUtils.getPlayerTabComplete(args[0]) : Collections.emptyList();
    }

}
