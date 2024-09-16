package dev.padrewin.premiumpoints.conversion.converter;

import dev.padrewin.coldplugin.ColdPlugin;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import me.realized.tokenmanager.TokenManagerPlugin;
import me.realized.tokenmanager.data.DataManager;
import me.realized.tokenmanager.data.database.Database;
import dev.padrewin.premiumpoints.conversion.CurrencyConverter;
import dev.padrewin.premiumpoints.models.SortedPlayer;
import org.bukkit.Bukkit;

public class TokenManagerConverter extends CurrencyConverter {

    public TokenManagerConverter(ColdPlugin coldPlugin) {
        super(coldPlugin, "TokenManager");
    }

    @Override
    public void convert() {
        TokenManagerPlugin tokenManager = (TokenManagerPlugin) this.plugin;
        DataManager dataManager = tokenManager.getDataManager();
        try {
            Field field_database = DataManager.class.getDeclaredField("database");
            field_database.setAccessible(true);
            Database database = (Database) field_database.get(dataManager);
            this.coldPlugin.getLogger().warning("Converting data from TokenManager, this may take a while if you have a lot of data...");
            database.ordered(Integer.MAX_VALUE, data -> {
                if (data.isEmpty())
                    return;

                boolean isUUID;
                try {
                    UUID.fromString(data.get(0).getKey());
                    isUUID = true;
                } catch (Exception e) {
                    isUUID = false;
                }

                int count = 0;
                SortedSet<SortedPlayer> pointsData = new TreeSet<>();
                for (Database.TopElement entry : data) {
                    try {
                        UUID uuid;
                        String name;
                        if (isUUID) {
                            uuid = UUID.fromString(entry.getKey());
                            name = "Unknown";
                        } else {
                            uuid = Bukkit.getOfflinePlayer(entry.getKey()).getUniqueId();
                            name = entry.getKey();
                        }

                        int amount = Math.toIntExact(entry.getTokens());
                        pointsData.add(new SortedPlayer(uuid, name, amount));

                        if (++count % 500 == 0)
                            this.coldPlugin.getLogger().warning(String.format("Converted %d entries...", count));
                    } catch (Exception e) {
                        this.coldPlugin.getLogger().warning(String.format("Data entry [%s:%d] skipped due to invalid data", entry.getKey(), entry.getTokens()));
                    }
                }

                this.coldPlugin.getManager(dev.padrewin.premiumpoints.manager.DataManager.class).importData(pointsData, Collections.emptyMap());
                this.coldPlugin.getLogger().warning(String.format("Successfully converted %d entries!", count));
            });
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

}
