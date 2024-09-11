// In package org.black_ixx.playerpoints
package org.black_ixx.playerpoints;

public class PlayerPoints {

    private static PlayerPoints instance;

    // Constructor privat pentru singleton
    private PlayerPoints() {
    }

    // Returnează instanța singleton
    public static PlayerPoints getInstance() {
        if (instance == null) {
            instance = new PlayerPoints();
        }
        return instance;
    }

    // Redirecționează API-ul la implementarea ta din dev.padrewin
    public PlayerPointsAPI getAPI() {
        return dev.padrewin.playerpoints.PlayerPoints.getInstance().getAPI();
    }
}
