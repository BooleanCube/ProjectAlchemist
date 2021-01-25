package bot.commands.animals;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class birdfact implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            try {
                URL web = new URL("https://some-random-api.ml/facts/bird");
                HttpsURLConnection con = (HttpsURLConnection) web.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String data = bf.readLine();
                int idx1 = data.indexOf("{\"fact\":\"") + 9;
                int idx2 = data.indexOf("\"}");
                String fact = data.substring(idx1, idx2);
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("Bird Fact: ");
                e.setDescription(fact);
                event.getChannel().sendMessage(e.build()).queue();
            } catch (MalformedURLException e) {

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
        return "Gives you a random bird fact\n" +
                "Usage: `a!birdfact`";
    }

    @Override
    public String getInvoke() {
        return "birdfact";
    }
}
