package ch.minepvp.spout.kingdoms.database.table;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

@Table("Log")
public class Log {

    @Id
    private int id;

    @Field
    private String typ;

    @Field
    private String command;

    @Field
    private String player;

    @Field
    private String time;

}
