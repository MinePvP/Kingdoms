package ch.minepvp.spout.kingdoms.database.table;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.entity.KingdomLevel;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

import java.util.ArrayList;

@Table("Kingdom")
public class Kingdom {

    @Id
    private int id;

    @Field
    private String tag;

    @Field
    private String name;

    @Field
    private int level = 0;

    @Field
    private int points = 0;

    @Field
    private int pointsAll = 0;


    // Base
    @Field
    private int baseX = 0;

    @Field
    private int baseY = 0;

    @Field
    private int baseZ = 0;


    // Spawn
    @Field
    private int spawnX = 0;

    @Field
    private int spawnY = 0;

    @Field
    private int spawnZ = 0;


    // Stats
    @Field
    private int playerKills = 0;

    @Field
    private int playerDeaths = 0;

    @Field
    private int monsterKills = 0;

    @Field
    private int monsterDeaths = 0;

    @Field
    private int blockBreak = 0;

    @Field
    private int blockPlace = 0;


    private ArrayList<Member> members = members = new ArrayList<Member>();
    private ArrayList<Member> invitedMembers = invitedMembers = new ArrayList<Member>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KingdomLevel getLevel() {

        for ( KingdomLevel level : KingdomsConfig.LEVELS ) {

            if ( level.getLevel() == this.level ) {
                return level;
            }

        }

        return null;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints( int points ) {
        this.points += points;
        this.pointsAll += points;
    }

    public void removePoints( int points ) {
        this.points -= points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPointsAll() {
        return pointsAll;
    }

    public void setPointsAll(int pointsAll) {
        this.pointsAll = pointsAll;
    }

    public Point getBasePoint() {
        return new Point( Kingdoms.getInstance().getEngine().getWorld("world"), getBaseX(), getBaseY(), getBaseZ() );
    }

    public int getBaseX() {
        return baseX;
    }

    public void setBaseX(int baseX) {
        this.baseX = baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public void setBaseY(int baseY) {
        this.baseY = baseY;
    }

    public int getBaseZ() {
        return baseZ;
    }

    public void setBaseZ(int baseZ) {
        this.baseZ = baseZ;
    }

    public Point getBaseCornerOne() {
        return new Point( Kingdoms.getInstance().getEngine().getWorld("world"), getBaseX() - getLevel().getSize(), getBaseY(), getBaseZ() - getLevel().getSize() );
    }

    public Point getBaseCornerTwo() {
        return new Point( Kingdoms.getInstance().getEngine().getWorld("world"), getBaseX() + getLevel().getSize(), getBaseY(), getBaseZ() + getLevel().getSize() );
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getSpawnZ() {
        return spawnZ;
    }

    public void setSpawnZ(int spawnZ) {
        this.spawnZ = spawnZ;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void addPlayerKill() {
        this.playerKills++;
    }

    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    public int getPlayerDeaths() {
        return playerDeaths;
    }

    public void addPlayerDeath() {
        this.playerDeaths++;
    }

    public void setPlayerDeaths(int playerDeaths) {
        this.playerDeaths = playerDeaths;
    }

    public int getMonsterKills() {
        return monsterKills;
    }

    public void addMonsterKill() {
        this.monsterKills++;
    }

    public void setMonsterKills(int monsterKills) {
        this.monsterKills = monsterKills;
    }

    public int getMonsterDeaths() {
        return monsterDeaths;
    }

    public void addMonsterDeath() {
        this.monsterDeaths++;
    }

    public void setMonsterDeaths(int monsterDeaths) {
        this.monsterDeaths = monsterDeaths;
    }

    public int getBlockBreak() {
        return blockBreak;
    }

    public void addBlockBreak() {
        this.blockBreak++;
    }

    public void setBlockBreak(int blockBreak) {
        this.blockBreak = blockBreak;
    }

    public int getBlockPlace() {
        return blockPlace;
    }

    public void addBlockPlace() {
        this.blockPlace++;
    }

    public void setBlockPlace(int blockPlace) {
        this.blockPlace = blockPlace;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void addMember( Member member ) {
        this.members.add(member);
    }

    public void removeMember( Member member ) {
        this.members.remove(member);
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<Member> getInvitedMembers() {
        return invitedMembers;
    }

    public void addInvitedMember( Member member ) {
        this.invitedMembers.add(member);
    }

    public void removeInvitedMember( Member member ) {
        this.invitedMembers.remove(member);
    }

    public void setInvitedMembers(ArrayList<Member> invitedMembers) {
        this.invitedMembers = this.invitedMembers;
    }

    public boolean contains(Point point) {

        if ( getBaseCornerTwo().getBlockX() > point.getBlockX() && getBaseCornerOne().getBlockX() < point.getBlockX()) {

            if ( getBaseCornerTwo().getBlockZ() > point.getBlockZ() && getBaseCornerOne().getBlockZ() < point.getBlockZ() ) {
                return true;
            }

        }

        return false;

    }

}
