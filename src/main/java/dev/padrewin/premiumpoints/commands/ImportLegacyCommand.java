package dev.padrewin.premiumpoints.commands;

import dev.padrewin.coldplugin.database.MySQLConnector;
import dev.padrewin.coldplugin.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.CommandManager;
import dev.padrewin.premiumpoints.manager.DataManager;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import org.bukkit.command.CommandSender;

public class ImportLegacyCommand extends PointsCommand {

    public ImportLegacyCommand() {
        super("importlegacy", CommandManager.CommandAliases.IMPORTLEGACY);
    }

    @Override
    public void execute(PremiumPoints plugin, CommandSender sender, String[] args) {
        plugin.getScheduler().runTaskAsync(() -> {
            LocaleManager localeManager = plugin.getManager(LocaleManager.class);
            if (!(plugin.getManager(DataManager.class).getDatabaseConnector() instanceof MySQLConnector)) {
                localeManager.sendMessage(sender, "command-importlegacy-only-mysql");
                return;
            }

            if (args.length < 1) {
                localeManager.sendMessage(sender, "command-importlegacy-usage");
                return;
            }

            if (plugin.getManager(DataManager.class).importLegacyTable(args[0])) {
                localeManager.sendMessage(sender, "command-importlegacy-success", StringPlaceholders.of("table", args[0]));
            } else {
                localeManager.sendMessage(sender, "command-importlegacy-failure", StringPlaceholders.of("table", args[0]));
            }
        });
    }

    @Override
    public List<String> tabComplete(PremiumPoints plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}
