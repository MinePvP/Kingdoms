package ch.minepvp.spout.kingdoms.database.table;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.entity.KingdomChannel;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table("Member")
public class Member {

    @Id
    private int id;

    @Field
    private String name;

    @Field
    private int kingdom = 0;

    @Field
    private int money = 0;

    @Field
    private boolean online = false;


    @Field
    private KingdomChannel channel = KingdomChannel.LOCAL;

    @Field
    private KingdomRank rank = KingdomRank.NONE;

    // Login's
    @Field
    private String firstLogin = "";

    @Field
    private String lastLogin = "";

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKingdom() {

        Kingdom kingdom = Kingdoms.getInstance().getDatabase().select(Kingdom.class).where().equal("id", this.kingdom).execute().findOne();

        if ( kingdom != null ) {
            return kingdom.getName();
        }

        return null;
    }

    public void setKingdom(int kingdom) {
        this.kingdom = kingdom;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public KingdomChannel getChannel() {
        return channel;
    }

    public void setChannel(KingdomChannel channel) {
        this.channel = channel;
    }

    public KingdomRank getRank() {
        return rank;
    }

    public void setRank(KingdomRank rank) {
        this.rank = rank;
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

    public Date getLastLogin() {

        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastLogin);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public void setLastLogin(Date date) {
        this.lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public Date getFirstLogin() {

        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(firstLogin);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public void setFirstLogin(Date date) {
        this.firstLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
