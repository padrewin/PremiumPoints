package dev.padrewin.premiumpoints.event;

import java.util.UUID;
import org.bukkit.event.HandlerList;

/**
 * Called when a player's points is to be reset.
 */
public class PremiumPointsResetEvent extends PremiumPointsEvent {

    /**
     * Handler list.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor.
     *
     * @param id - UUID of the player.
     */
    public PremiumPointsResetEvent(UUID id) {
        super(id, 0);
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
