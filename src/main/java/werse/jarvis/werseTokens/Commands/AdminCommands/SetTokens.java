package werse.jarvis.werseTokens.Commands.AdminCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class SetTokens {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public SetTokens(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }
    public void setTokens(Player admin, Player player, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = player.getName();
        int newValue = value;

        updateData.put(user, newValue);

        player.sendMessage(PREFIX + "Saldo Konta ustawiono na " + newValue + " tokenów!");
        admin.sendMessage(PREFIX + "Ustawiono saldo tokenów gracza: " + user + " na " + value);
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
