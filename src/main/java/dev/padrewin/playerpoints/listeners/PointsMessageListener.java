package dev.padrewin.playerpoints.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dev.padrewin.coldplugin.ColdPlugin;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import dev.padrewin.playerpoints.manager.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PointsMessageListener implements PluginMessageListener {

    public static final String CHANNEL = "BungeeCord";
    public static final String REFRESH_SUBCHANNEL = "playerpoints:refresh";
    private final DataManager dataManager;

    public PointsMessageListener(ColdPlugin rosePlugin) {
        this.dataManager = rosePlugin.getManager(DataManager.class);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player attachedPlayer, byte[] message) {
        if (!channel.equals(CHANNEL))
            return;

        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();
        if (subchannel.equals(REFRESH_SUBCHANNEL)) {
            short length = input.readShort();
            byte[] data = new byte[length];
            input.readFully(data);

            String uuidString = new String(data, StandardCharsets.UTF_8);
            UUID uuid = UUID.fromString(uuidString);
            this.dataManager.refreshPoints(uuid);
        }
    }

}
