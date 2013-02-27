package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.task.Task;
import org.spout.api.chat.ChatArguments;
import org.spout.api.entity.Player;
import org.spout.api.lang.Translation;
import org.spout.api.scheduler.TaskPriority;

public class AttackTask extends Task {

    private Zone zone;
    private Kingdom attacker;
    private Kingdom defender;

    private FlagTask flagTask;
    private boolean isAttacked = false;

    public AttackTask( Zone zone, Kingdom attacker, Kingdom defender ) {

        this.zone = zone;
        this.attacker = attacker;
        this.defender = defender;


    }


    @Override
    public void run() {

        if ( isActive() ) {

            if ( isAttacked ) {
                stopAttack();
            } else {
                startAttack();
            }

        }


    }

    private void startAttack() {

        zone.setAttacker(attacker);
        zone.setDefender(defender);

        zone.setAttack(true);

        for ( Member member : attacker.getMembers() ) {

            Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}You can now Attack the Zone!", toPlayer ) ) );
            }

        }

        for ( Member member : defender.getMembers() ) {

            Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}The Zone can no be Attacked!", toPlayer ) ) );
            }

        }

        // Start Zone Task

        flagTask = new FlagTask(zone, attacker, defender);
        Kingdoms.getInstance().getTaskManager().createSyncRepeatingTask(flagTask, 0L, 12000L, TaskPriority.HIGH);

        isAttacked = true;

    }

    private void stopAttack() {

        // Winner?
        if ( flagTask.wasAttackSuccessfully() ) {
            zone.setKingdom( attacker.getId() );

            for ( Member member : attacker.getMembers() ) {

                Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

                if ( toPlayer.isOnline() ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}The Attack is over! And your Kingdomm has taken the Zone!", toPlayer ) ) );
                }

            }

            for ( Member member : defender.getMembers() ) {

                Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

                if ( toPlayer.isOnline() ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}The Attack is over! And your Kingdom has lost the Zone!", toPlayer ) ) );
                }

            }

        } else {

            for ( Member member : attacker.getMembers() ) {

                Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

                if ( toPlayer.isOnline() ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}The Attack is over! And your Kingdom has lost the Zone!", toPlayer ) ) );
                }

            }

            for ( Member member : defender.getMembers() ) {

                Player toPlayer = Kingdoms.getInstance().getServer().getPlayer(member.getName(), true);

                if ( toPlayer.isOnline() ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}The Attack is over! And your Kingdom has defended the Zone!", toPlayer ) ) );
                }

            }

        }

        // Start Cooldown Task
        zone.setAttackCooldown(true);

        Long delay = (zone.getCooldown() * 20L) * 60L;

        Task task = new CooldownTask(zone);
        Kingdoms.getInstance().getTaskManager().createAsyncDelayedTask(task, delay, TaskPriority.LOW);

        // Reset Zone
        Kingdoms.getInstance().getZoneManager().resetZone(zone);

        setActive(false);
    }

}
