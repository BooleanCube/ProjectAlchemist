package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class covid19 implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            try {
                URL web = new URL("https://coronavirus-19-api.herokuapp.com/all");
                HttpsURLConnection con = (HttpsURLConnection) web.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String data = bf.readLine();
                String cases = data.substring(data.indexOf("{\"cases\":")+9, data.indexOf(",\"deaths\":"));
                String death = data.substring(data.indexOf("\"deaths\":")+9, data.indexOf(",\"recovered\":"));
                String recovered = data.substring(data.indexOf(",\"recovered\":")+13, data.indexOf("}"));
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setTitle("Covid-19 Stats Globally");
                e.setDescription("**Cases:** `" + cases + "`\n" +
                        "**Deaths:** `" + death + "`\n" +
                        "**Recovered:** `" + recovered + "`\n");
                e.setColor(Color.RED);
                event.getChannel().sendMessage(e.build()).queue();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(args.size() == 1) {
            try {
                URL web = new URL("https://coronavirus-19-api.herokuapp.com/countries/" + args.get(0));
                HttpsURLConnection con = (HttpsURLConnection) web.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String data = bf.readLine();
                if(data.equalsIgnoreCase("Country not found")) {
                    event.getChannel().sendMessage("**Country not found!**").queue();
                    return;
                }
                String country = data.substring(data.indexOf("{\"country\":\"")+12, data.indexOf("\",\"cases\":"));
                String cases = data.substring(data.indexOf(",\"cases\":")+9, data.indexOf(",\"todayCases\":"));
                String todayC = data.substring(data.indexOf(",\"todayCases\":")+14, data.indexOf(",\"deaths\":"));
                String todayD = data.substring(data.indexOf(",\"todayDeaths\":")+15, data.indexOf(",\"recovered\":"));
                String death = data.substring(data.indexOf("\"deaths\":")+9, data.indexOf(",\"todayDeaths\":"));
                String recovered = data.substring(data.indexOf(",\"recovered\":")+13, data.indexOf(",\"active\":"));
                String active = data.substring(data.indexOf(",\"active\":")+10, data.indexOf(",\"critical\":"));
                String critical = data.substring(data.indexOf(",\"critical\":")+12, data.indexOf(",\"casesPerOneMillion\":"));
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setTitle("Covid-19 Stats in " + country);
                e.setDescription("**Cases:** `" + cases + "`\n" +
                        "**Cases Today:** `" + todayC + "`\n" +
                        "**Deaths:** `" + death + "`\n" +
                        "**Deaths Today:** `" + todayD + "`\n" +
                        "**Recovered:** `" + recovered + "`\n" +
                        "**Active:** `" + active + "`\n" +
                        "**Critical:** `" + critical + "`\n");
                e.setColor(Color.RED);
                event.getChannel().sendMessage(e.build()).queue();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows you the covid-19 stats\n" +
                "Usage: `a!covid19 [country(optional)]`";
    }

    @Override
    public String getInvoke() {
        return "covid19";
    }
}
