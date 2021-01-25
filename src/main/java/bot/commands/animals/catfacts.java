package bot.commands.animals;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class catfacts implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //https://meowfacts.herokuapp.com/
        if(args.isEmpty()) {
            URL web = null;
            try {
                web = new URL("https://meowfacts.herokuapp.com/");
            } catch (MalformedURLException e) {

            }
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(web.openStream()));
                String data = bf.readLine();
                int idx1 = data.indexOf("{\"data\":[\"") + 10;
                int idx2 = data.indexOf("\"]}");
                String fact = data.substring(idx1, idx2);
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("Cat Fact:");
                e.setDescription(fact);
                event.getChannel().sendMessage(e.build()).queue();
            } catch (IOException e) {
                //nothing
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Gives you a random cat fact\n" +
                "Usage: `a!catfact`";
    }

    @Override
    public String getInvoke() {
        return "catfact";
    }
}
