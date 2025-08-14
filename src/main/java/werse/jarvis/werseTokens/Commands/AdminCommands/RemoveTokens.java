package werse.jarvis.werseTokens.Commands.AdminCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class RemoveTokens {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public RemoveTokens(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void removeTokens(Player player,Player punishmentPlayer, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = punishmentPlayer.getName();

        int currentValue = updateData.getOrDefault(user, 0);
        int newValue = currentValue - value;

        updateData.put(user, newValue);

        punishmentPlayer.sendMessage(PREFIX + "Z twojego konta odjęto " + value + " tokenów!");
        player.sendMessage(PREFIX + "Odjęto " + value + " tokenów z konta gracza " + user);
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
