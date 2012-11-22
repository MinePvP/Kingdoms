package ch.minepvp.spout.kingdoms.database.table;

import ch.minepvp.spout.kingdoms.Kingdoms;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import org.spout.api.geo.discrete.Point;

@Table("Zone")
public class Zone {

    @Id
    private int id;

    @Field
    private String name;

    @Field
    private boolean active = false;

    @Field
    private boolean build = false;


    // Owner
    @Field
    private int kingdom = 0;


    // Corner One
    @Field
    private int cornerOneX;

    @Field
    private int cornerOneY;

    @Field
    private int cornerOneZ;


    // Corner Two
    @Field
    private int cornerTwoX;

    @Field
    private int cornerTwoY;

    @Field
    private int cornerTwoZ;


    // Flag
    @Field
    private int flagX = 0;

    @Field
    private int flagY = 0;

    @Field
    private int flagZ = 0;


    // Settings
    @Field
    private int cost = 0;

    @Field
    private int points = 0;

    @Field
    private int lifePoolDefenders = 0;

    @Field
    private int lifePoolAttackers = 0;

    @Field
    private int minDefenders = 0;

    @Field
    private int minAttackers = 0;

    @Field
    private int delay = 0;

    @Field
    private int duration = 0;

    @Field
    private int cooldown = 0;


    // Spawn Points
    @Field
    private int spawnDefendersX = 0;

    @Field
    private int spawnDefendersY = 0;

    @Field
    private int spawnDefendersZ = 0;

    @Field
    private int spawnAttackersX = 0;

    @Field
    private int spawnAttackersY = 0;

    @Field
    private int spawnAttackersZ = 0;



    // Other Stuff
    private boolean attack = false;
    private int attackCooldown = 0;

    private Kingdom attackedKingdom = null;

    private int deathCounterAttacker = 0;
    private int deathCounterDefenders = 0;

    private int flagLvl = 0;


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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public int getKingdom() {
        return kingdom;
    }

    public void setKingdom(int kingdom) {
        this.kingdom = kingdom;
    }

    public int getCornerOneX() {
        return cornerOneX;
    }

    public void setCornerOneX(int cornerOneX) {
        this.cornerOneX = cornerOneX;
    }

    public int getCornerOneY() {
        return cornerOneY;
    }

    public void setCornerOneY(int cornerOneY) {
        this.cornerOneY = cornerOneY;
    }

    public int getCornerOneZ() {
        return cornerOneZ;
    }

    public void setCornerOneZ(int cornerOneZ) {
        this.cornerOneZ = cornerOneZ;
    }

    public Point getCornerOne() {
        return new Point( Kingdoms.getInstance().getEngine().getWorld("world"), getCornerOneX(), getCornerOneY(), getCornerOneZ() );
    }

    public int getCornerTwoX() {
        return cornerTwoX;
    }

    public void setCornerTwoX(int cornerTwoX) {
        this.cornerTwoX = cornerTwoX;
    }

    public int getCornerTwoY() {
        return cornerTwoY;
    }

    public void setCornerTwoY(int cornerTwoY) {
        this.cornerTwoY = cornerTwoY;
    }

    public int getCornerTwoZ() {
        return cornerTwoZ;
    }

    public void setCornerTwoZ(int cornerTwoZ) {
        this.cornerTwoZ = cornerTwoZ;
    }

    public Point getCornerTwo() {
        return new Point( Kingdoms.getInstance().getEngine().getWorld("world"), getCornerTwoX(), getCornerTwoY(), getCornerTwoZ() );
    }

    public int getFlagX() {
        return flagX;
    }

    public void setFlagX(int flagX) {
        this.flagX = flagX;
    }

    public int getFlagY() {
        return flagY;
    }

    public void setFlagY(int flagY) {
        this.flagY = flagY;
    }

    public int getFlagZ() {
        return flagZ;
    }

    public void setFlagZ(int flagZ) {
        this.flagZ = flagZ;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLifePoolDefenders() {
        return lifePoolDefenders;
    }

    public void setLifePoolDefenders(int lifePoolDefenders) {
        this.lifePoolDefenders = lifePoolDefenders;
    }

    public int getLifePoolAttackers() {
        return lifePoolAttackers;
    }

    public void setLifePoolAttackers(int lifePoolAttackers) {
        this.lifePoolAttackers = lifePoolAttackers;
    }

    public int getMinDefenders() {
        return minDefenders;
    }

    public void setMinDefenders(int minDefenders) {
        this.minDefenders = minDefenders;
    }

    public int getMinAttackers() {
        return minAttackers;
    }

    public void setMinAttackers(int minAttackers) {
        this.minAttackers = minAttackers;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getSpawnDefendersX() {
        return spawnDefendersX;
    }

    public void setSpawnDefendersX(int spawnDefendersX) {
        this.spawnDefendersX = spawnDefendersX;
    }

    public int getSpawnDefendersY() {
        return spawnDefendersY;
    }

    public void setSpawnDefendersY(int spawnDefendersY) {
        this.spawnDefendersY = spawnDefendersY;
    }

    public int getSpawnDefendersZ() {
        return spawnDefendersZ;
    }

    public void setSpawnDefendersZ(int spawnDefendersZ) {
        this.spawnDefendersZ = spawnDefendersZ;
    }

    public int getSpawnAttackersX() {
        return spawnAttackersX;
    }

    public void setSpawnAttackersX(int spawnAttackersX) {
        this.spawnAttackersX = spawnAttackersX;
    }

    public int getSpawnAttackersY() {
        return spawnAttackersY;
    }

    public void setSpawnAttackersY(int spawnAttackersY) {
        this.spawnAttackersY = spawnAttackersY;
    }

    public int getSpawnAttackersZ() {
        return spawnAttackersZ;
    }

    public void setSpawnAttackersZ(int spawnAttackersZ) {
        this.spawnAttackersZ = spawnAttackersZ;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public Kingdom getAttackedKingdom() {
        return attackedKingdom;
    }

    public void setAttackedKingdom(Kingdom attackedKingdom) {
        this.attackedKingdom = attackedKingdom;
    }

    public int getDeathCounterAttacker() {
        return deathCounterAttacker;
    }

    public void setDeathCounterAttacker(int deathCounterAttacker) {
        this.deathCounterAttacker = deathCounterAttacker;
    }

    public int getDeathCounterDefenders() {
        return deathCounterDefenders;
    }

    public void setDeathCounterDefenders(int deathCounterDefenders) {
        this.deathCounterDefenders = deathCounterDefenders;
    }

    public int getFlagLvl() {
        return flagLvl;
    }

    public void setFlagLvl(int flagLvl) {
        this.flagLvl = flagLvl;
    }
}
