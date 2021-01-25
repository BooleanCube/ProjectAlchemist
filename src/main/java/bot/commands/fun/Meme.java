package bot.commands.fun;

import bot.Constants;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Meme implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            String[] msg = event.getMessage().getContentRaw().split(" ");
            JSONParser parser = new JSONParser();
            String title = "";
            String url = "";
            try {
                URL memeurl = new URL("https://meme-api.herokuapp.com/gimme");
                BufferedReader bf = new BufferedReader(new InputStreamReader(memeurl.openConnection().getInputStream()));
                String lines;
                while((lines = bf.readLine()) != null) {
                    JSONArray array = new JSONArray();
                    array.add(parser.parse(lines));
                    for(Object o : array) {
                        JSONObject obj = (JSONObject) o;
                        title = (String) obj.get("title");
                        url = (String) obj.get("url");
                    }
                }
                bf.close();
                EmbedBuilder e = new EmbedBuilder();
                e.setColor(Color.red);
                e.setTitle(title);
                e.setImage(url);
                event.getChannel().sendMessage(e.build()).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage(":no_entry: **Something went wrong!!!** Please try again later!").queue();
            }
        }
        else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows a random meme\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "meme";
    }
}
