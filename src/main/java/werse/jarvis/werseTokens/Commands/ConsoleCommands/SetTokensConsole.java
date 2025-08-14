package werse.jarvis.werseTokens.Commands.ConsoleCommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class SetTokensConsole {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public SetTokensConsole(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }
    public void setTokensConsole(Player player, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = player.getName();
        int newValue = value;

        updateData.put(user, newValue);

        Bukkit.getLogger().info("Saldo Konta ustawiono na " + newValue + " tokenów!");
        player.sendMessage(PREFIX + "Saldo twojeko konta zostało zakjtualizowane na " + value + " tokenów");
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
