package werse.jarvis.werseTokens.Commands.SetDeafultAmout;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class SetDeafultAmount {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public SetDeafultAmount(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void setDeafultAmount(Player player, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = player.getName();
        int newValue = value;

        updateData.put(user, newValue);

        player.sendMessage(PREFIX + "Saldo Konta ustawiono na " + newValue + " tokenów!");
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
