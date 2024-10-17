// In package org.black_ixx.playerpoints
package org.black_ixx.playerpoints;

public class PlayerPoints {

    private static PlayerPoints instance;

    private PlayerPoints() {
    }

    public static PlayerPoints getInstance() {
        if (instance == null) {
            instance = new PlayerPoints();
        }
        return instance;
    }

    public PlayerPointsAPI getAPI() {
        return dev.padrewin.premiumpoints.PremiumPoints.getInstance().getAPI();
    }
}