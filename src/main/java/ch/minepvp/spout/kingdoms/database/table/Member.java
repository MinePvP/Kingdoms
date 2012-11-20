package ch.minepvp.spout.kingdoms.database.table;

import ch.minepvp.spout.kingdoms.entity.KingdomChannel;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

@Table("Member")
public class Member {

    @Id
    private int id;

    @Field
    private String name;

    @Field
    private String kingdom = "null";

    @Field
    private boolean online = false;


    @Field
    private KingdomChannel channel = KingdomChannel.LOCAL;

    @Field
    private KingdomRank rank = KingdomRank.NONE;


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
        return kingdom;
    }

    public void setKingdom(String kingdom) {
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
}
