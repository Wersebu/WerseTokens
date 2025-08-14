package werse.jarvis.werseTokens.Commands.AdminCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class AddTokens {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public AddTokens(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }
    public void addTokensAdmin(Player player, Player addingPlayer, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = addingPlayer.getName();
        int currentValue = updateData.getOrDefault(user, 0);
        int newValue = currentValue + value;

        updateData.put(user, newValue);
        addingPlayer.sendMessage(PREFIX + "Do twojego konta dodano " + value + " Tokenów!");
        player.sendMessage(PREFIX + "Dodano " + value + " Tokenów do konta gracza " + user);
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
