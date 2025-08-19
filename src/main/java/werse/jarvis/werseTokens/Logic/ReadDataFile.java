package werse.jarvis.werseTokens.Logic;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadDataFile {
    private final JavaPlugin plugin;
    private final SaveDataToFile saveDataToFile;
    private final String pathToDataFile;

    public ReadDataFile(JavaPlugin plugin) {
        this.plugin = plugin;
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.pathToDataFile = saveDataToFile.getPathToDataFile();
    }

    public HashMap<String, Integer> readDataFromDatabase() {

        File database = new File(pathToDataFile);
        if (!database.exists()) {
            database.getParentFile().mkdirs();
            try {
                database.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        HashMap<String, Integer> updateData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String uuidKey = parts[0].trim();
                    int intValue = Integer.parseInt(parts[1].trim());
                    updateData.put(uuidKey, intValue);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return updateData;
    }
}
