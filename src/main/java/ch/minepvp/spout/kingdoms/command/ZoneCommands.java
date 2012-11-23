package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.component.SelectionComponent;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.ZoneManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.lang.Translation;
import org.spout.vanilla.material.VanillaMaterials;

public class ZoneCommands {

    private final Kingdoms plugin;

    private KingdomManager kingdomManager;
    private MemberManager memberManager;
    private ZoneManager zoneManager;

    public ZoneCommands(Kingdoms instance) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
        zoneManager = plugin.getZoneManager();
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
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone list",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}List all Zones", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.create") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone create <name>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Create a new Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.delete") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone delete <name>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Delete a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setactive") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setactive <name> <true|false>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the active Property of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setbuild") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setbuild <name> <true|false>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the build Property of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setkingdom") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setkingdom <name> <name>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Kingdom of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setflag") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setflag",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Flag Point", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setcost") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setcost <name> <cost>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Cost of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setpoints") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setpoints <name> <points>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Points of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setlifepool") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setlivepool <name> <attackers|defenders> <lifes>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Lifepool", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setminplayers") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setminplayers <name> <attackers|defenders> <players>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set min Players for Attackers and Defenders", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setdelay") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setdelay <name> <delay>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Delay of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setduration") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setduration <name> <duration>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Duration of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setcooldown") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setcooldown <name> <cooldown>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set Cooldown of a Zone", player)) );
        }

        if ( player.hasPermission("kingdoms.command.zone.setspawn") ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/zone setspawn <name> <defender|attackers>",player)) );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Spawn Points", player)) );
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
        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Zone is now deleted!", player)) );
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

        if ( Boolean.getBoolean(args.getString(1)) ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setactive <name> <true|false>", player)) );
            return;
        }

        zone.setActive( Boolean.getBoolean(args.getString(1)) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Zone Property is set!", player)) );
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

        if ( Boolean.getBoolean(args.getString(1)) ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setbuild <name> <true|false>", player)) );
            return;
        }

        zone.setBuild( Boolean.getBoolean(args.getString(1)) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Zone Property is set!", player)) );
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

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Kingdom is added!", player)) );
    }

    @Command(aliases = {"setflag"}, usage = "", desc = "Set the Flag of a Zone")
    @CommandPermissions("kingdoms.command.zone.setflag")
    public void setFlag(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Zone zone = zoneManager.getZoneByPoint(player.getTransform().getPosition());

        if ( zone == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You need to be in a Zone!", player)) );
            return;
        }

        // Create new Flag
        World world = player.getWorld();


        // Reset old Flag
        if ( zone.getFlagX() != 0 && zone.getFlagY() != 0 && zone.getFlagZ() != 0 ) {

            // Ground
            world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.DIRT, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.DIRT, (short)0, null);

            //
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 2, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 3, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 4, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 5, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 6, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 7, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 8, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 9, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);

            // Flag
            world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);

        }

        // Set new Flag
        zone.setFlagX( player.getTransform().getPosition().getBlockX() );
        zone.setFlagY(player.getTransform().getPosition().getBlockY());
        zone.setFlagZ(player.getTransform().getPosition().getBlockZ());

        // Ground
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ() + 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ() - 1, VanillaMaterials.STONE, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);

        // Pole
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 2, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 3, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 4, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 5, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 6, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 7, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 8, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 9, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);

        // Flag
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Flag is created!", player)) );
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

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Zone cost is set!", player)) );
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

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Points for Zone set!", player)) );
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

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Lifepool is set!", player)) );
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

            zone.setMinAttackers( args.getInteger(2) );

        } else if ( args.getString(1).equalsIgnoreCase("defenders") ) {

            zone.setMinDefenders( args.getInteger(2) );

        } else {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setminplayers <name> <attackers|defenders> <players>", player)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Min Players is set!", player)) );
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

        zone.setDelay( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Delay for Zone set!", player)) );
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

        zone.setDuration( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Duration for Zone set!", player)) );
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

        zone.setCooldown( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Cooldown for Zone set!", player)) );
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

        if ( args.getString(1).equalsIgnoreCase("attacker") ) {

            zone.setSpawnAttackersX( player.getTransform().getPosition().getBlockX() );
            zone.setSpawnAttackersY( player.getTransform().getPosition().getBlockY() );
            zone.setSpawnAttackersZ( player.getTransform().getPosition().getBlockZ() );

        } else if ( args.getString(1).equalsIgnoreCase("defender") ) {

            zone.setSpawnDefendersX( player.getTransform().getPosition().getBlockX() );
            zone.setSpawnDefendersY( player.getTransform().getPosition().getBlockY() );
            zone.setSpawnDefendersZ( player.getTransform().getPosition().getBlockZ() );

        } else {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/zone setspawn <name> <attacker|defender>", player)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}Spwan for the Zone ist set!", player)) );
    }




}
