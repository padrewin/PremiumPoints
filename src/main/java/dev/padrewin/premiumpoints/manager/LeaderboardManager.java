package dev.padrewin.premiumpoints.manager;

import dev.padrewin.coldplugin.ColdPlugin;
import dev.padrewin.coldplugin.manager.Manager;
import dev.padrewin.coldplugin.scheduler.task.ScheduledTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import dev.padrewin.premiumpoints.models.SortedPlayer;
import dev.padrewin.premiumpoints.setting.SettingKey;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LeaderboardManager extends Manager implements Listener {

    private ScheduledTask refreshTask;
    private final DataManager dataManager;
    private long refreshInterval;

    private List<SortedPlayer> leaderboard;
    private long lastLeaderboardRefreshTime;
    private boolean usedLeaderboardSinceLastRefresh;

    private Map<UUID, Long> positions;
    private long lastPositionsRefreshTime;
    private boolean usedPositionsSinceLastRefresh;

    public LeaderboardManager(ColdPlugin coldPlugin) {
        super(coldPlugin);

        this.dataManager = coldPlugin.getManager(DataManager.class);
        this.leaderboard = new ArrayList<>();
        this.positions = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, this.coldPlugin);
    }

    @Override
    public void reload() {
        if (!SettingKey.LEADERBOARD_DISABLE.get()) {
            this.refreshTask = this.coldPlugin.getScheduler().runTaskTimerAsync(this::refresh, 10L, 20L);
            this.refreshInterval = SettingKey.LEADERBOARD_PLACEHOLDER_REFRESH_INTERVAL.get() * 1000;
        }
    }

    @Override
    public void disable() {
        this.leaderboard.clear();
        this.positions.clear();

        if (this.refreshTask != null) {
            this.refreshTask.cancel();
            this.refreshTask = null;
        }
    }

    /**
     * Refreshes the leaderboard after a certain amount of time has passed.
     */
    public void refresh() {
        if (this.usedLeaderboardSinceLastRefresh && System.currentTimeMillis() - this.lastLeaderboardRefreshTime >= this.refreshInterval) {
            this.usedLeaderboardSinceLastRefresh = false;
            this.lastLeaderboardRefreshTime = System.currentTimeMillis();
            this.leaderboard = this.dataManager.getTopSortedPoints(SettingKey.LEADERBOARD_PLACEHOLDER_ENTRIES.get());
        }

        if (this.usedPositionsSinceLastRefresh && System.currentTimeMillis() - this.lastPositionsRefreshTime >= this.refreshInterval) {
            this.usedPositionsSinceLastRefresh = false;
            this.lastPositionsRefreshTime = System.currentTimeMillis();
            this.positions = this.dataManager.getOnlineTopSortedPointPositions();
        }
    }

    /**
     * @return the current points leaderboard
     */
    public List<SortedPlayer> getLeaderboard() {
        this.usedLeaderboardSinceLastRefresh = true;
        return this.leaderboard;
    }

    /**
     * Gets a player's position in the leaderboard.
     *
     * @param uuid The UUID of the player
     * @return the position of the player in the leaderboard
     */
    public Long getPlayerLeaderboardPosition(UUID uuid) {
        this.usedPositionsSinceLastRefresh = true;
        return this.positions.get(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.usedPositionsSinceLastRefresh = true;
    }

}
