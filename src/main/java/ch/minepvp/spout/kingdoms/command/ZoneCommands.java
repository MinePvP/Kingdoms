package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.SelectionComponent;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.TaskManager;
import ch.minepvp.spout.kingdoms.manager.ZoneManager;
import ch.minepvp.spout.kingdoms.task.Task;
import ch.minepvp.spout.kingdoms.task.kingdom.SpawnTask;
import ch.minepvp.spout.kingdoms.task.zone.AttackTask;
import org.spout.api.chat.ChatArguments;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.lang.Translation;
import org.spout.api.scheduler.TaskPriority;

public class ZoneCommands {

    private final Kingdoms plugin;

    private KingdomManager kingdomManager;
    private MemberManager memberManager;
    private ZoneManager zoneManager;
    private TaskManager taskManager;

    public ZoneCommands(Kingdoms instance) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
        zoneManager = plugin.getZoneManager();
        taskManager = plugin.getTaskManager();
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /zone")
    @CommandPermissions("kingdoms.command.zone.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Help") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        if ( player.hasPermission("kingdoms.command.zone.list") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 list",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}List all Zones", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.create") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 create <name>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Create a new Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.delete") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 delete <name>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Delete a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setactive") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setactive <name> <true|false>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the active Property of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setbuild") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setbuild <name> <true|false>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the build Property of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setkingdom") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setkingdom <name> <name>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Kingdom of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setflag") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setflag",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Flag Point", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setcost") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setcost <name> <cost>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Cost of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setpoints") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setpoints <name> <points>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Points of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setlifepool") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setlivepool <name> <attackers|defenders> <lifes>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Lifepool", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setminplayers") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setminplayers <name> <attackers|defenders> <players>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set min Players for Attackers and Defenders", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setdelay") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setdelay <name> <delay>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Delay of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setduration") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setduration <name> <duration>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Duration of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setcooldown") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setcooldown <name> <cooldown>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Cooldown of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setspawn") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 setspawn <name> <defender|attackers>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Spawn Points", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.attack") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 attack <name>",player, args.getCommand())) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Attack a Zone", player)) );
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

    }

    @Command(aliases = {"list"}, usage = "", desc = "List all Kingdoms")
    @CommandPermissions("kingdoms.command.zone.list")
    public void list(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}List all Zones") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        if ( zoneManager.getAllZones().size() > 0) {

            for ( Zone zone : zoneManager.getAllZones() ) {

                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}ID : {{WHITE}}%0 {{YELLOW}}Name : {{WHITE}}%1 {{YELLOW}}Active : {{WHITE}}%2", player, zone.getId(), zone.getName(), zone.isActive()) ) );
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}Build : {{WHITE}}%0 {{YELLOW}}Cost : {{WHITE}}%1 {{YELLOW}}Points per Hour : {{WHITE}}%2", player, zone.isBuild(), zone.getCost(), zone.getPoints() ) ) );
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}Min. Online Defender : {{WHITE}}%0 {{YELLOW}}Attackers : {{WHITE}}%1", player, zone.getMinDefenders(), zone.getMinAttackers() ) ) );
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}Life Pool Defender : {{WHITE}}%0 {{YELLOW}}Attackers : {{WHITE}}%1", player, zone.getLifePoolDefenders(), zone.getLifePoolAttackers() ) ) );
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}Delay : {{WHITE}}%0sec {{YELLOW}}Duration : {{WHITE}}%1sec {{YELLOW}}Cooldown : {{WHITE}}%2sec", player, zone.getDelay(), zone.getDuration(), zone.getCooldown() ) ) );

                player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));
            }

        }

    }


    @Command(aliases = {"create"}, usage = "", desc = "Create a new Zone")
    @CommandPermissions("kingdoms.command.zone.create")
    public void create(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Point point1 = player.get(SelectionComponent.class).getSelection().getPoint1();
        Point point2 = player.get(SelectionComponent.class).getSelection().getPoint2();

        if ( point1 == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You need to select the first Corner!", player)) );
            return;
        }

        if ( point2 == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You need to select the second Corner!", player)) );
            return;
        }

        if ( kingdomManager.getAllKingdomsNearPoint( point1, KingdomsConfig.KINGDOMS_DISTANCE_MIN_ZONES.getInt() ) != null ) {

            // TODO better Info System about the near to a Kingdom
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Point 1 is to near to a Kingdom!", player)) );
            return;

        }

        if ( kingdomManager.getAllKingdomsNearPoint( point2, KingdomsConfig.KINGDOMS_DISTANCE_MIN_ZONES.getInt() ) != null ) {

            // TODO better Info System about the near to a Kingdom
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Point 2 is to near to a Kingdom!", player)) );
            return;

        }

        if ( zoneManager.getZoneByName( args.getString(0) ) != null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is already a Zone with this Name!", player)) );
            return;
        }

        if ( zoneManager.getZoneByPoint( point1 ) != null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Point 1 is in a other Zone!", player)) );
            return;
        }

        if ( zoneManager.getZoneByPoint( point2 ) != null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Point 2 is in a other Zone!", player)) );
            return;
        }

        zoneManager.createZone(args.getString(0), player.get(SelectionComponent.class).getSelection().getMinPoint(), player.get(SelectionComponent.class).getSelection().getMaxPoint());

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}The Zone is created!", player) ) );
    }

    @Command(aliases = {"delete"}, usage = "", desc = "Delete a Zone")
    @CommandPermissions("kingdoms.command.zone.delete")
    public void delete(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 1 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone delete <name>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zoneManager.deleteZone(zone);
        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Zone is now deleted!", player)) );
    }

    @Command(aliases = {"setactive"}, usage = "", desc = "Set the active Property of a Zone")
    @CommandPermissions("kingdoms.command.zone.setactive")
    public void setActive(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setactive <name> <true|false>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        if ( Boolean.parseBoolean(args.getString(1)) != true && Boolean.parseBoolean(args.getString(1)) != false ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setactive <name> <true|false>", player)) );
            return;
        }

        zone.setActive( Boolean.parseBoolean(args.getString(1)) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Zone Property is set!", player)) );
    }

    @Command(aliases = {"setbuild"}, usage = "", desc = "Set the build Property of a Zone")
    @CommandPermissions("kingdoms.command.zone.setbuild")
    public void setBuild(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setbuild <name> <true|false>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        if ( Boolean.parseBoolean(args.getString(1)) != true && Boolean.parseBoolean(args.getString(1)) != false ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setbuild <name> <true|false>", player)) );
            return;
        }

        zone.setBuild( Boolean.parseBoolean(args.getString(1)) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Zone Property is set!", player)) );
    }

    @Command(aliases = {"setkingdom"}, usage = "", desc = "Set the kingdom Property of a Zone")
    @CommandPermissions("kingdoms.command.zone.setkingdom")
    public void setKingdom(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setkingdom <name> <kingdom>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        Kingdom kingdom = kingdomManager.getKingdomByName( args.getString(1) );

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Kingdom with this Name!", player)) );
            return;
        }

        zone.setKingdom( kingdom.getId() );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Kingdom is added!", player)) );
    }

    @Command(aliases = {"setflag"}, usage = "", desc = "Set the Flag of a Zone")
    @CommandPermissions("kingdoms.command.zone.setflag")
    public void setFlag(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Zone zone = zoneManager.getZoneByPoint(player.getScene().getTransform().getPosition());

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You need to be in a Zone!", player)) );
            return;
        }

        // Create new Flag
        World world = player.getWorld();


        // Reset old Flag
        if ( zone.getFlagX() != 0 && zone.getFlagY() != 0 && zone.getFlagZ() != 0 ) {
            zoneManager.removeFlag(world, zone);
        }

        // Set new Flag
        zone.setFlagX( player.getScene().getTransform().getPosition().getBlockX() );
        zone.setFlagY( player.getScene().getTransform().getPosition().getBlockY() - 1 );
        zone.setFlagZ( player.getScene().getTransform().getPosition().getBlockZ() );

        zoneManager.createFlag(world, zone);

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Flag is created!", player)) );
    }

    @Command(aliases = {"setcost"}, usage = "", desc = "Set the cost for a Zone")
    @CommandPermissions("kingdoms.command.zone.setcost")
    public void setCost(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setcost <name> <cost>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zone.setCost( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Zone cost is set!", player)) );
    }

    @Command(aliases = {"setpoints"}, usage = "", desc = "Set the points for a Zone who the Kingdom per Houer get")
    @CommandPermissions("kingdoms.command.zone.setpoints")
    public void setPoints(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setpoints <name> <points>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zone.setPoints( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Points for Zone set!", player)) );
    }

    @Command(aliases = {"setlivepool"}, usage = "", desc = "Set the Livepools for Attackers or Defenders")
    @CommandPermissions("kingdoms.command.zone.setlivepool")
    public void setLivePool(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 3 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setlivepool <name> <attackers|defenders> <lives>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        if ( args.getString(1).equalsIgnoreCase("attackers") ) {

            zone.setLifePoolAttackers( args.getInteger(2) );

        } else if ( args.getString(1).equalsIgnoreCase("defenders") ) {

            zone.setLifePoolDefenders( args.getInteger(2) );

        } else {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setlivepool <name> <attackers|defenders> <lives>", player)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Lifepool is set!", player)) );
    }

    @Command(aliases = {"setminplayers"}, usage = "", desc = "Set min Players for Attackers or Defenders")
    @CommandPermissions("kingdoms.command.zone.setminplayers")
    public void setMinPlayers(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 3 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setminplayers <name> <attacker|defender> <players>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        if ( args.getString(1).equalsIgnoreCase("attackers") ) {

            zone.setMinAttackers(args.getInteger(2));

        } else if ( args.getString(1).equalsIgnoreCase("defenders") ) {

            zone.setMinDefenders(args.getInteger(2));

        } else {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setminplayers <name> <attackers|defenders> <players>", player)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Min Players is set!", player)) );
    }

    @Command(aliases = {"setdelay"}, usage = "", desc = "Set the Delay for a Attack")
    @CommandPermissions("kingdoms.command.zone.setdelay")
    public void setDelay(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setdelay <name> <delay>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zone.setDelay(args.getInteger(1));

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Delay for Zone set!", player)) );
    }

    @Command(aliases = {"setduration"}, usage = "", desc = "Set the Duration of a Attack")
    @CommandPermissions("kingdoms.command.zone.setduration")
    public void setDuration(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setduration <name> <duration>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zone.setDuration(args.getInteger(1));

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Duration for Zone set!", player)) );
    }

    @Command(aliases = {"setcooldown"}, usage = "", desc = "Set the Cooldown after a Attack")
    @CommandPermissions("kingdoms.command.zone.setcooldown")
    public void setCooldown(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setcooldown <name> <cooldown>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        zone.setCooldown(args.getInteger(1));

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Cooldown for Zone set!", player)) );
    }

    @Command(aliases = {"setspawn"}, usage = "", desc = "Sett the Spawn for Attackers and Defenders")
    @CommandPermissions("kingdoms.command.zone.setspawn")
    public void setSpawn(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setspawn <name> <attacker|defender>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }

        if ( zone.getCornerOne().distance( player.getScene().getTransform().getPosition() ) > KingdomsConfig.ZONE_DISTANCE_MAX_SPAWN.getInt() &&
             zone.getCornerTwo().distance( player.getScene().getTransform().getPosition() ) > KingdomsConfig.ZONE_DISTANCE_MAX_SPAWN.getInt()) {

            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You are to far away from the Zone!", player)) );
            return;
        }

        if ( args.getString(1).equalsIgnoreCase("attacker") ) {

            zone.setSpawnAttackersX( player.getScene().getTransform().getPosition().getBlockX() );
            zone.setSpawnAttackersY( player.getScene().getTransform().getPosition().getBlockY() );
            zone.setSpawnAttackersZ( player.getScene().getTransform().getPosition().getBlockZ() );

        } else if ( args.getString(1).equalsIgnoreCase("defender") ) {

            zone.setSpawnDefendersX( player.getScene().getTransform().getPosition().getBlockX() );
            zone.setSpawnDefendersY( player.getScene().getTransform().getPosition().getBlockY() );
            zone.setSpawnDefendersZ( player.getScene().getTransform().getPosition().getBlockZ() );

        } else {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setspawn <name> <attacker|defender>", player)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Spwan for the Zone ist set!", player)) );
    }

    @Command(aliases = {"attack"}, usage = "", desc = "Sett the Spawn for Attackers and Defenders")
    @CommandPermissions("kingdoms.command.zone.attack")
    public void attack(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 1 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone attack <name>", player)) );
            return;
        }

        Zone zone = zoneManager.getZoneByName( args.getString(0) );

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Zone with this Name!", player)) );
            return;
        }


        Kingdom attackKingdom = kingdomManager.getKingdomByPlayer( player );

        if ( attackKingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You are not in a Kingdom!", player)) );
            return;
        }

        if ( zone.getMinAttackers() > attackKingdom.getOnlineMembers().size() ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You are not enough Online Members!", player)) );
            return;
        }

        Kingdom defenseKingdom = kingdomManager.getKingdomByName( zone.getKingdom() );

        if ( defenseKingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}No Kingdom owns this Zone! You can buy this Zone with /zone buy <name>", player)) );
            return;
        }

        if ( zone.getAttackCooldown() ) {
            // TODO Cooldown left
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You can't attack this Zone right now! You need to wait some Minutes.", player)) );
            return;
        }

        if ( defenseKingdom.getOnlineMembers() == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There are no Defenders Online!", player)) );
            return;
        }

        if ( zone.getMinDefenders() > defenseKingdom.getOnlineMembers().size() ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There are not enough Defenders Online!", player)) );
            return;
        }

        for ( Member member : attackKingdom.getMembers() ) {

            Player toPlayer = plugin.getServer().getPlayer(member.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}In %0sec you can attack the Zone %1 from %2!", toPlayer, zone.getDelay(), zone.getName(), zone.getKingdom())) );
            }

        }

        for ( Member member : defenseKingdom.getMembers() ) {

            Player toPlayer = plugin.getServer().getPlayer(member.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}The Zone %0 will attacked in %1 sec from %2!", toPlayer, zone.getName(), zone.getDelay(), attackKingdom.getName())) );
            }

        }

        // Start Attack Tasks
        Long delay = (zone.getDelay() * 20L) * 60L;
        Long repeating = (zone.getDuration() * 20L) * 60L;

        Task task = new AttackTask(zone, attackKingdom, defenseKingdom);
        taskManager.createSyncRepeatingTask(task, delay, repeating, TaskPriority.MEDIUM);

    }

}
