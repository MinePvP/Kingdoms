package ch.minepvp.spout.kingdoms.config;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

import java.io.File;

public class KingdomsConfig extends ConfigurationHolderConfiguration {

    // MySQL
    public static final ConfigurationHolder MYSQL_HOST = new ConfigurationHolder("localhost", "Settings", "MySQL", "Host");
    public static final ConfigurationHolder MYSQL_PORT = new ConfigurationHolder(3306, "Settings", "MySQL", "Port");
    public static final ConfigurationHolder MYSQL_DB = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "DB");
    public static final ConfigurationHolder MYSQL_USER = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "User");
    public static final ConfigurationHolder MYSQL_PASSWORD = new ConfigurationHolder("kingdoms", "Settings", "MySQL", "Password");

    // Kingdoms
    public static final ConfigurationHolder KINGDOMS_DISTANCE_MIN_KINGDOMS = new ConfigurationHolder(550, "Settings", "Kingdoms", "Distance", "MinKingdoms");
    public static final ConfigurationHolder KINGDOMS_DISTANCE_MIN_ZONES = new ConfigurationHolder(300, "Settings", "Kingdoms", "Distance", "MinZones");

    // Teleport
    public static final ConfigurationHolder TELEPORT_DELAY = new ConfigurationHolder( 5, "Settings", "Teleport", "Delay");
    public static final ConfigurationHolder TELEPORT_COOLDOWN = new ConfigurationHolder( 60, "Settings", "Teleport", "Cooldown");


    // Chat
    public static final ConfigurationHolder KINGDOMS_CHAT_LOCAL_MAX_DISTANCE = new ConfigurationHolder(100, "Settings", "Kingdoms", "Chat", "Local", "MaxDistance");


    public KingdomsConfig(File dataFolder) {
        super(new YamlConfiguration(new File(dataFolder, "config.yml")));
    }

    @Override
    public void load() throws ConfigurationException {
        super.load();
        super.save();
    }

}
