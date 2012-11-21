package ch.minepvp.spout.kingdoms;

import ch.minepvp.spout.kingdoms.command.KingdomsCommand;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Plot;
import ch.minepvp.spout.kingdoms.listener.BlockListener;
import ch.minepvp.spout.kingdoms.listener.EntityListener;
import ch.minepvp.spout.kingdoms.listener.PlayerListener;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.PlotManager;
import ch.minepvp.spout.kingdoms.task.SaveTask;
import com.alta189.simplesave.Database;
import com.alta189.simplesave.DatabaseFactory;
import com.alta189.simplesave.exceptions.ConnectionException;
import com.alta189.simplesave.exceptions.TableRegistrationException;
import com.alta189.simplesave.mysql.MySQLConfiguration;
import org.spout.api.Server;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.scheduler.TaskPriority;

import java.util.logging.Level;

public class Kingdoms extends CommonPlugin {

    private static Kingdoms instance;
    private Server server;

    private KingdomsConfig config;

    private Database db;

    // Manager
    MemberManager memberManager;
    KingdomManager kingdomManager;
    PlotManager plotManager;

    // Listener
    private PlayerListener playerListener;
    private BlockListener blockListener;
    private EntityListener entityListener;


    public Kingdoms () {
        instance = this;
    }

    @Override
    public void onLoad() {

        // Initial Config
        config = new KingdomsConfig( getDataFolder() );

        getLogger().info("Loaded");
    }

    @Override
    public void onEnable() {

        // Load Config
        try {
            config.load();
        } catch (ConfigurationException e) {
            getLogger().log(Level.WARNING, "Error loading Kingdoms configuration: ", e);
        }

        // MySQL Connection
        MySQLConfiguration mysql = new MySQLConfiguration();
        mysql.setHost( KingdomsConfig.MYSQL_HOST.getString() );
        mysql.setPort( KingdomsConfig.MYSQL_PORT.getInt() );
        mysql.setUser( KingdomsConfig.MYSQL_USER.getString() );
        mysql.setPassword( KingdomsConfig.MYSQL_PASSWORD.getString() );
        mysql.setDatabase( KingdomsConfig.MYSQL_DB.getString() );

        db = DatabaseFactory.createNewDatabase(mysql);

        try {
            db.registerTable(Member.class);
            db.registerTable(Kingdom.class);
            db.registerTable(Plot.class);
        } catch (TableRegistrationException e) {
            e.printStackTrace();
        }

        try {
            db.connect();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        // Manager
        memberManager = new MemberManager();
        plotManager = new PlotManager();
        kingdomManager = new KingdomManager();

        // Listener
        playerListener = new PlayerListener();
        blockListener = new BlockListener();
        entityListener = new EntityListener();

        // Register Listener
        getEngine().getEventManager().registerEvents(playerListener, this);
        getEngine().getEventManager().registerEvents(blockListener, this);
        getEngine().getEventManager().registerEvents(entityListener, this);


        //Register commands
        CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(this), new SimpleAnnotatedCommandExecutorFactory());
        getEngine().getRootCommand().addSubCommands(this, KingdomsCommand.class, commandRegFactory);


        // Start Tasks
        getEngine().getScheduler().scheduleSyncDelayedTask(this, new SaveTask(), ((KingdomsConfig.SAVE_TASK_TIME.getLong() * 20 ) * 60), TaskPriority.MEDIUM );


        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {

        memberManager.saveAll();
        plotManager.saveAll();
        kingdomManager.saveAll();

        getLogger().info("Disabled");
    }

    public static Kingdoms getInstance() {
        return instance;
    }

    public Server getServer() {
        return (Server)getEngine();
    }

    public Database getDatabase() {
        return db;
    }

    public MemberManager getMemberManager() {
        return memberManager;
    }

    public KingdomManager getKingdomManager() {
        return kingdomManager;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }

}
