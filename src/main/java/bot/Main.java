package bot;

import bot.commands.logs.logs;
import bot.commands.utility.aliases;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {

    private Main() {

        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        try {
            logger.info("Booting...");
            new DefaultShardManagerBuilder()
                    .setToken("NjgzNTEwODcwNDU4MTA1ODk2.XmVehA.gO4ibsepId4U9setLPAds-7Sa7M")
                    .setShardsTotal(2)
                    .addEventListeners(listener)
                    .addEventListeners(new logs())
                    .addEventListeners(new aliases())
                    .setActivity(Activity.playing("a!help | " + 5 + " servers"))
                    .build();
            logger.info("Running...");
        } catch(LoginException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)   {
        new Main();
    }

}