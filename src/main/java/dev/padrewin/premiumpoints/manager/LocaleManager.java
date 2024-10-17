package dev.padrewin.premiumpoints.manager;

import dev.padrewin.colddev.ColdPlugin;
import dev.padrewin.colddev.manager.AbstractLocaleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.minecart.CommandMinecart;

public class LocaleManager extends AbstractLocaleManager {

    public LocaleManager(ColdPlugin coldPlugin) {
        super(coldPlugin); // Calls super constructor first
    }

    public String getCurrencyName(int value) {
        if (value == 1 || value == -1) {
            return this.getLocaleMessage("currency-singular");
        } else {
            return this.getLocaleMessage("currency-plural");
        }
    }

    @Override
    protected void handleMessage(CommandSender sender, String message) {
        if (!Bukkit.isPrimaryThread() && (sender instanceof BlockCommandSender || sender instanceof CommandMinecart)) {
            Bukkit.getScheduler().runTask(this.coldPlugin, () -> super.handleMessage(sender, message));
        } else {
            super.handleMessage(sender, message);
        }
    }

}
