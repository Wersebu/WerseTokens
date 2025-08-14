package werse.jarvis.werseTokens.Logic;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

public class SaveDataToFile {
    private final JavaPlugin plugin;
    private final String pathToDataFile;

    public SaveDataToFile(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pathToDataFile = plugin.getDataFolder() + "/database.yml";
    }

    public void addTokensToDatabase(HashMap<String, Integer> updateData) {

        File database = new File(pathToDataFile);
        if (!database.exists()) {
            database.getParentFile().mkdirs(); // Tworzymy katalogi, jeśli ich nie ma
            try {
                database.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Zapisujemy całą mapę z powrotem do pliku
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToDataFile))) {
            for (HashMap.Entry<String, Integer> entry : updateData.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathToDataFile() {
        return pathToDataFile;
    }

}
