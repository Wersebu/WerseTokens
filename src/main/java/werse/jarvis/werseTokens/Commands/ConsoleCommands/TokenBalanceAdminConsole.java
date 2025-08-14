package werse.jarvis.werseTokens.Commands.ConsoleCommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Commands.AdminCommands.SetTokens;
import werse.jarvis.werseTokens.Commands.SetDeafultAmout.SetDeafultAmount;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class TokenBalanceAdminConsole {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private final SetDeafultAmount setDeafultAmount;
    private static String PREFIX;

    public TokenBalanceAdminConsole(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.setDeafultAmount = new SetDeafultAmount(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void tokenBalanceAdminConsole(Player askedPlayer) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = askedPlayer.getName();

        if (updateData.get(user) != null) {
            int value = updateData.get(user);

            Bukkit.getLogger().info("Stan konta gracza " + user + " to " + value + " tokenów.");
            askedPlayer.sendMessage(PREFIX + "Stan konta został ustawiony na " + value + " tokenów.");
            saveDataToFile.addTokensToDatabase(updateData);
        } else {
            setDeafultAmount.setDeafultAmount(askedPlayer, 0);

            updateData = readDataFile.readDataFromDatabase();
            int value = updateData.get(user);

            Bukkit.getLogger().info("Stan konta gracza " + user + " to " + value + " tokenów.");
            askedPlayer.sendMessage(PREFIX + "Stan konta został ustawiony na " + value + " tokenów.");
        }


    }
}
