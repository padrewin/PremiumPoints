package dev.padrewin.premiumpoints.conversion;

import dev.padrewin.coldplugin.ColdPlugin;
import dev.padrewin.premiumpoints.PremiumPoints;
import dev.padrewin.premiumpoints.conversion.converter.GamePointsConverter;
import dev.padrewin.premiumpoints.conversion.converter.TokenManagerConverter;

public enum CurrencyPlugin {

    TokenManager(TokenManagerConverter.class),
    GamePoints(GamePointsConverter.class);

    private final Class<? extends CurrencyConverter> converterClass;

    CurrencyPlugin(Class<? extends CurrencyConverter> converterClass) {
        this.converterClass = converterClass;
    }

    public CurrencyConverter getConverter() {
        try {
            return this.converterClass.getConstructor(ColdPlugin.class).newInstance(PremiumPoints.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CurrencyPlugin get(String name) {
        for (CurrencyPlugin currencyPlugin : values())
            if (currencyPlugin.name().equalsIgnoreCase(name))
                return currencyPlugin;
        return null;
    }

}
