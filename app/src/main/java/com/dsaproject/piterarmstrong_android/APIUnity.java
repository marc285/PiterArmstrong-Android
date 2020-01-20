package com.dsaproject.piterarmstrong_android;
import com.dsaproject.piterarmstrong_android.models.User;

public class APIUnity {
    public static String getPlayerStats() {
        String res = "";
        res += "1" + ",";
        res += "2" + ",";
        return res;
    }
    public static void sendStats(String stringStats)
    {
        User stats = User.getInstance();
        String[] trozos = stringStats.split(",");
        stats.setHealth(Integer.parseInt(trozos[0]));
        stats.setScreen(Integer.parseInt(trozos[1]));
    }
}
