package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import org.spout.api.plugin.services.EconomyService;

import java.util.List;

public class EconomyServiceManager extends EconomyService {

    private KingdomManager kingdomManager;
    private MemberManager memberManager;

    public EconomyServiceManager() {
        kingdomManager = Kingdoms.getInstance().getKingdomManager();
        memberManager = Kingdoms.getInstance().getMemberManager();
    }

    @Override
    public boolean exists(String name) {

        Member member = memberManager.getMemberByName( name );
        Kingdom kingdom = kingdomManager.getKingdomByName( name );

        if ( member != null || kingdom != null ) {
            return true;
        }

        return false;
    }

    @Override
    public boolean create(String s) {
        return false;
    }

    @Override
    public boolean remove(String s) {
        return false;
    }

    @Override
    public double get(String name) {

        Member member = memberManager.getMemberByName( name );
        Kingdom kingdom = kingdomManager.getKingdomByName( name );

        if ( member != null ) {
            return member.getMoney();
        }

        if ( kingdom != null ) {
            return kingdom.getMoney();
        }

        return 0;
    }

    @Override
    public double get(String s, String s2) throws UnknownCurrencyException {
        return 0; // MULTICURRENCY ONLY
    }

    @Override
    public boolean has(String name, double v) {

        Member member = memberManager.getMemberByName( name );
        Kingdom kingdom = kingdomManager.getKingdomByName( name );

        if ( member != null ) {

            if ( member.getMoney() >= v ) {
                return true;
            }

        }

        if ( kingdom != null ) {

            if ( kingdom.getMoney() >= v ) {
                return true;
            }

        }

        return false;
    }

    @Override
    public boolean has(String s, double v, String s2) throws UnknownCurrencyException {
        return false; // MULTICURRENCY ONLY
    }

    @Override
    public boolean canWithdraw(String name, double v) {
        return has( name, v );
    }

    @Override
    public boolean canWithdraw(String s, double v, String s2) throws UnknownCurrencyException {
        return false; // MULTICURRENCY ONLY
    }

    @Override
    public boolean canHold(String name, double v) {
        return exists(name);
    }

    @Override
    public boolean canHold(String s, double v, String s2) throws UnknownCurrencyException {
        return false; // MULTICURRENCY ONLY
    }

    @Override
    protected boolean onWithdraw(String s, double v, String s2) throws UnknownCurrencyException {
        return false; // TODO ???
    }

    @Override
    protected boolean onDeposit(String s, double v, String s2) throws UnknownCurrencyException {
        return false;  // TODO ???
    }

    @Override
    public String getCurrencyNameSingular() {
        return "Credit";
    }

    @Override
    public String getCurrencyNamePlural() {
        return "Credits";
    }

    @Override
    public String getCurrencyNamePlural(String s) throws UnknownCurrencyException {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public String getCurrencySymbol() {
        return "$";
    }

    @Override
    public String getCurrencySymbol(String s) throws UnknownCurrencyException {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public String format(double v) {
        return v + " " + getCurrencyNamePlural();
    }

    @Override
    public String format(String s, double v) {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public String formatShort(double v) {
        return v + getCurrencySymbol();
    }

    @Override
    public String formatShort(String s, double v) {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public List<String> getTopAccounts(int i, int i2, boolean b) {
        return null; // TODO
    }

    @Override
    public List<String> getTopAccounts(int i, int i2, String s, boolean b) throws UnknownCurrencyException {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public int numSignificantDigits() {
        return 0;
    }

    @Override
    public boolean hasMulticurrencySupport() {
        return false;
    }

    @Override
    public List<String> getCurrencyNames() {
        return null; // MULTICURRENCY ONLY
    }

    @Override
    public double getExchangeRate(String s, String s2) throws UnknownCurrencyException {
        return 0; // MULTICURRENCY ONLY
    }

}
