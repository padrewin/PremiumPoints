package dev.padrewin.premiumpoints.conversion;

import dev.padrewin.coldplugin.ColdPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class CurrencyConverter {

    protected final ColdPlugin coldPlugin;
    protected final Plugin plugin;

    public CurrencyConverter(ColdPlugin coldPlugin, String pluginName) {
        this.coldPlugin = coldPlugin;
        this.plugin = Bukkit.getPluginManager().getPlugin(pluginName);
    }

    public boolean canConvert() {
        return this.plugin != null && this.plugin.isEnabled();
    }

    public abstract void convert();

}
