package dev.padrewin.playerpoints.commands;

import java.util.Collections;
import java.util.List;
import dev.padrewin.playerpoints.PlayerPoints;
import dev.padrewin.playerpoints.manager.CommandManager;
import dev.padrewin.playerpoints.manager.LocaleManager;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends PointsCommand {

    public ReloadCommand() {
        super("reload", CommandManager.CommandAliases.RELOAD);
    }

    @Override
    public void execute(PlayerPoints plugin, CommandSender sender, String[] args) {
        plugin.reload();
        plugin.getManager(LocaleManager.class).sendMessage(sender, "command-reload-success");
    }

    @Override
    public List<String> tabComplete(PlayerPoints plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}
