package werse.jarvis.werseTokens.Commands.PlayerCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Commands.AdminCommands.SetTokens;
import werse.jarvis.werseTokens.Commands.SetDeafultAmout.SetDeafultAmount;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class GiveTokens {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private final SetDeafultAmount setDeafultAmount;
    private static String PREFIX;

    public GiveTokens(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.setDeafultAmount = new SetDeafultAmount(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void giveTokens(Player fromPlayer, Player toPlayer, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

        String fromPlayerName = fromPlayer.getName();
        String toPlayerName = toPlayer.getName();

        if (updateData.get(fromPlayerName) == null) {
            setDeafultAmount.setDeafultAmount(fromPlayer, 0);
        }
        if (updateData.get(toPlayerName) == null) {
            setDeafultAmount.setDeafultAmount(toPlayer, 0);
        }
        updateData = readDataFile.readDataFromDatabase();

        int accualValueFromPlayerName = updateData.get(fromPlayerName);

        if (accualValueFromPlayerName >= value) {
            int newValueFromPlayerName = updateData.get(fromPlayerName) - value;
            int newValueToPlayerName = updateData.get(toPlayerName) + value;

            updateData.put(fromPlayerName, newValueFromPlayerName);
            updateData.put(toPlayerName, newValueToPlayerName);

            fromPlayer.sendMessage(PREFIX + "Wysłałeś " + value + " tokenów do gracza " + toPlayerName);
            toPlayer.sendMessage(PREFIX + "Otrzymałeś " + value + " tokenów od gracza " + fromPlayerName);

            saveDataToFile.addTokensToDatabase(updateData);
        } else {
            fromPlayer.sendMessage(PREFIX + "Masz za mało tokenów by wykonać tą operację!");
        }
    }
}
