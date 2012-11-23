package ch.minepvp.spout.kingdoms.config;

import ch.minepvp.spout.kingdoms.entity.KingdomLevel;
import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class KingdomsConfig extends ConfigurationHolderConfiguration {

    // MySQL
    public static final ConfigurationHolder MYSQL_HOST = new ConfigurationHolder("localhost", "Settings", "MySQL", "Host");
    public static final ConfigurationHolder MYSQL_PORT = new ConfigurationHolder(3306, "Settings", "MySQL", "Port");
    public static final ConfigurationHolder MYSQL_DB = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "DB");
    public static final ConfigurationHolder MYSQL_USER = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "User");
    public static final ConfigurationHolder MYSQL_PASSWORD = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "Password");

    // Kingdoms
    public static final ConfigurationHolder KINGDOMS_DISTANCE_MIN_KINGDOMS = new ConfigurationHolder(550, "Settings", "Kingdoms", "Distance", "MinKingdoms");
    public static final ConfigurationHolder KINGDOMS_DISTANCE_MIN_ZONES = new ConfigurationHolder(350, "Settings", "Kingdoms", "Distance", "MinZones");
    public static final ConfigurationHolder KINGDOMS_DISTANCE_MIN_SPAWN = new ConfigurationHolder(600, "Settings", "Kingdoms", "Distance", "MinSpawn");

    public static final ConfigurationHolder KINGDOMS_BASE_MIN_PLAYERS = new ConfigurationHolder(1, "Settings", "Kingdoms", "Base", "MinePlayers");

    // Zone
    public static final ConfigurationHolder ZONE_DISTANCE_MAX_SPAWN = new ConfigurationHolder(100, "Settings", "Zones", "Distance", "MaxSpawn");

    // Teleport
    public static final ConfigurationHolder TELEPORT_DELAY = new ConfigurationHolder( 5, "Settings", "Teleport", "Delay");
    public static final ConfigurationHolder TELEPORT_COOLDOWN = new ConfigurationHolder( 60, "Settings", "Teleport", "Cooldown");


    // Chat
    public static final ConfigurationHolder KINGDOMS_CHAT_LOCAL_MAX_DISTANCE = new ConfigurationHolder(100, "Settings", "Kingdoms", "Chat", "Local", "MaxDistance");


    // Levels
    public static final ArrayList<KingdomLevel> LEVELS = new ArrayList<KingdomLevel>();


    // Tasks
    public static final ConfigurationHolder SAVE_TASK_TIME = new ConfigurationHolder( 10, "Settings", "Task", "Save", "Time");

    public KingdomsConfig(File dataFolder) {
        super(new YamlConfiguration(new File(dataFolder, "config.yml")));
    }

    @Override
    public void load() throws ConfigurationException {
        super.load();
        super.save();

        loadLevels();
    }

    public void loadLevels() {

        if ( getNode("Settings", "Kingdoms", "Level").getChildren().size() > 0 ) {

            // Get all Groups
            for ( int i = 0; i < getNode("Settings", "Kingdoms", "Level").getChildren().size(); i++ ) {

                loadLevel(i);

            }

        } else {
            loadDefaultLevels();
            loadLevels();
        }

    }

    public void loadLevel( Integer id ) {

        KingdomLevel kingdomLevel = new KingdomLevel();

        kingdomLevel.setLevel( getNode("Settings", "Kingdoms", "Level", "" + id, "Level").getInt());
        kingdomLevel.setSize( getNode("Settings", "Kingdoms", "Level", "" + id, "Size").getInt());
        kingdomLevel.setPoints( getNode("Settings", "Kingdoms", "Level", "" + id, "Points").getInt());
        kingdomLevel.setMembers( getNode("Settings", "Kingdoms", "Level", "" + id, "Members").getInt());

        LEVELS.add(kingdomLevel);

    }

    public void loadDefaultLevels() {

        getNode("Settings", "Kingdoms", "Level", "" + 1, "Level").setValue(1);
        getNode("Settings", "Kingdoms", "Level", "" + 1, "Size").setValue(20);
        getNode("Settings", "Kingdoms", "Level", "" + 1, "Points").setValue(0);
        getNode("Settings", "Kingdoms", "Level", "" + 1, "Members").setValue(0);

        getNode("Settings", "Kingdoms", "Level", "" + 2, "Level").setValue(2);
        getNode("Settings", "Kingdoms", "Level", "" + 2, "Size").setValue(40);
        getNode("Settings", "Kingdoms", "Level", "" + 2, "Points").setValue(20);
        getNode("Settings", "Kingdoms", "Level", "" + 2, "Members").setValue(0);

        getNode("Settings", "Kingdoms", "Level", "" + 3, "Level").setValue(3);
        getNode("Settings", "Kingdoms", "Level", "" + 3, "Size").setValue(60);
        getNode("Settings", "Kingdoms", "Level", "" + 3, "Points").setValue(30);
        getNode("Settings", "Kingdoms", "Level", "" + 3, "Members").setValue(0);

        getNode("Settings", "Kingdoms", "Level", "" + 4, "Level").setValue(4);
        getNode("Settings", "Kingdoms", "Level", "" + 4, "Size").setValue(80);
        getNode("Settings", "Kingdoms", "Level", "" + 4, "Points").setValue(40);
        getNode("Settings", "Kingdoms", "Level", "" + 4, "Members").setValue(2);

        getNode("Settings", "Kingdoms", "Level", "" + 5, "Level").setValue(5);
        getNode("Settings", "Kingdoms", "Level", "" + 5, "Size").setValue(100);
        getNode("Settings", "Kingdoms", "Level", "" + 5, "Points").setValue(50);
        getNode("Settings", "Kingdoms", "Level", "" + 5, "Members").setValue(4);

        try {
            save();
        } catch (ConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
