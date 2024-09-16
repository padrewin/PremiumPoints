package dev.padrewin.premiumpoints.commands;

import dev.padrewin.coldplugin.utils.StringPlaceholders;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.manager.CommandManager;
import dev.padrewin.premiumpoints.manager.LocaleManager;
import dev.padrewin.premiumpoints.setting.SettingKey;
import org.bukkit.command.CommandSender;

/**
 * Handles the commands for the root command.
 *
 * @author padrewin
 */
public class Commander extends CommandHandler {

    public Commander(PremiumPoints plugin) {
        super(plugin, "points", CommandManager.CommandAliases.ROOT);

        // Register commands.
        this.registerCommand(new HelpCommand(this));
        this.registerCommand(new GiveCommand());
        this.registerCommand(new GiveAllCommand());
        this.registerCommand(new TakeCommand());
        this.registerCommand(new LookCommand());
        this.registerCommand(new PayCommand());
        this.registerCommand(new SetCommand());
        this.registerCommand(new BroadcastCommand());
        this.registerCommand(new ResetCommand());
        this.registerCommand(new MeCommand());
        this.registerCommand(new ReloadCommand());
        this.registerCommand(new ExportCommand());
        this.registerCommand(new ImportCommand());
        this.registerCommand(new ConvertCommand());
        this.registerCommand(new ImportLegacyCommand());
        this.registerCommand(new VersionCommand());

        // Register handlers
        this.registerHandler(new LeadCommand(plugin));
    }

    @Override
    public void noArgs(CommandSender sender) {
        try {
            String redirect = SettingKey.BASE_COMMAND_REDIRECT.get().trim().toLowerCase();
            PointsCommand command = this.registeredCommands.get(redirect);
            CommandHandler handler = this.registeredHandlers.get(redirect);
            if (command != null) {
                if (!command.hasPermission(sender)) {
                    this.plugin.getManager(LocaleManager.class).sendMessage(sender, "no-permission");
                    return;
                }
                command.execute(this.plugin, sender, new String[0]);
            } else if (handler != null) {
                if (!handler.hasPermission(sender)) {
                    this.plugin.getManager(LocaleManager.class).sendMessage(sender, "no-permission");
                    return;
                }
                handler.noArgs(sender);
            } else {
                VersionCommand.sendInfo(this.plugin, sender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void unknownCommand(CommandSender sender, String[] args) {
        this.plugin.getManager(LocaleManager.class).sendMessage(sender, "unknown-command", StringPlaceholders.of("input", args[0]));
    }

}