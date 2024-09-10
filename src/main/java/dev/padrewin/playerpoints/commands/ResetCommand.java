package dev.padrewin.playerpoints.commands;

import dev.padrewin.coldplugin.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import dev.padrewin.playerpoints.PlayerPoints;
import dev.padrewin.playerpoints.manager.CommandManager;
import dev.padrewin.playerpoints.manager.LocaleManager;
import dev.padrewin.playerpoints.util.PointsUtils;
import org.bukkit.command.CommandSender;

public class ResetCommand extends PointsCommand {

    public ResetCommand() {
        super("reset", CommandManager.CommandAliases.RESET);
    }

    @Override
    public void execute(PlayerPoints plugin, CommandSender sender, String[] args) {
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

            if (plugin.getAPI().reset(player.getFirst())) {
                localeManager.sendMessage(sender, "command-reset-success", StringPlaceholders.builder("player", player.getSecond())
                        .add("currency", localeManager.getCurrencyName(0))
                        .build());
            }
        });
    }

    @Override
    public List<String> tabComplete(PlayerPoints plugin, CommandSender sender, String[] args) {
        return args.length == 1 ? PointsUtils.getPlayerTabComplete(args[0]) : Collections.emptyList();
    }

}
