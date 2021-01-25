package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class chucknorris implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //http://api.icndb.com/jokes/random
        if(args.isEmpty()) {
            try {
                URL web = new URL("http://api.icndb.com/jokes/random");
                BufferedReader bf = new BufferedReader(new InputStreamReader(web.openStream()));
                String data = bf.readLine();
                int idx1 = data.indexOf("\"joke\": \"") + 9;
                int idx2 = data.indexOf("\", \"categories\":");
                String joke = data.substring(idx1, idx2);
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("Chuck Norris Joke: ");
                e.setDescription(StringEscapeUtils.unescapeJava(joke));
                event.getChannel().sendMessage(e.build()).queue();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Gives you a random  and classic chuck norris joke\n" +
                "Usage: `a!chucknorris`";
    }

    @Override
    public String getInvoke() {
        return "chucknorris";
    }
}
