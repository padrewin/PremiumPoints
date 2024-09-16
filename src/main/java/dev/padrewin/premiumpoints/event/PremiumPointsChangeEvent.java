package dev.padrewin.premiumpoints.event;

import java.util.UUID;
import org.bukkit.event.HandlerList;

/**
 * Called when a player's points is to be changed.
 */
public class PremiumPointsChangeEvent extends PremiumPointsEvent {

    /**
     * Handler list.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor.
     *
     * @param playerId Player UUID
     * @param change   Amount of points to be changed.
     */
    public PremiumPointsChangeEvent(UUID playerId, int change) {
        super(playerId, change);
    }

    /**
     * Static method to get HandlerList.
     *
     * @return HandlerList.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
