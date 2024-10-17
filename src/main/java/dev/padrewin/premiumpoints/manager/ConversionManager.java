package dev.padrewin.premiumpoints.manager;

import dev.padrewin.colddev.ColdPlugin;
import dev.padrewin.colddev.manager.Manager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import dev.padrewin.premiumpoints.conversion.CurrencyConverter;
import dev.padrewin.premiumpoints.conversion.CurrencyPlugin;

public class ConversionManager extends Manager {

    private final Map<CurrencyPlugin, CurrencyConverter> converters;

    public ConversionManager(ColdPlugin coldPlugin) {
        super(coldPlugin);

        this.converters = new HashMap<>();
    }

    @Override
    public void reload() {
        for (CurrencyPlugin currencyPlugin : CurrencyPlugin.values())
            this.converters.put(currencyPlugin, currencyPlugin.getConverter());
    }

    @Override
    public void disable() {
        this.converters.clear();
    }

    public boolean convert(CurrencyPlugin currencyPlugin) {
        CurrencyConverter converter = this.converters.get(currencyPlugin);
        if (!converter.canConvert())
            return false;

        try {
            converter.convert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Set<CurrencyPlugin> getEnabledConverters() {
        return this.converters.entrySet().stream()
                .filter(x -> x.getValue().canConvert())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
