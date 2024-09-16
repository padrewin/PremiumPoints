package dev.padrewin.premiumpoints.commands;

import java.util.Collections;
import java.util.List;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.CommandManager;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends PointsCommand {

    public ReloadCommand() {
        super("reload", CommandManager.CommandAliases.RELOAD);
    }

    @Override
    public void execute(PremiumPoints plugin, CommandSender sender, String[] args) {
        plugin.reload();
        plugin.getManager(LocaleManager.class).sendMessage(sender, "command-reload-success");
    }

    @Override
    public List<String> tabComplete(PremiumPoints plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}
