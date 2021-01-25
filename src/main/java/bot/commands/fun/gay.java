package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class gay implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(args.size() == 1 || args.isEmpty()) {
            int random = (int)(Math.random()*1000) % 101;
            int random2 = (int)(Math.random()*1000) % 101;
            String person = "";
            if(msg.contains(" ")) {
                person = msg.split(" ")[1];
            } else {
                person = "you";
            }
            String bruhplsstop = "are";
            if(!person.equalsIgnoreCase("you")) {
                bruhplsstop = "is";
            }
            EmbedBuilder e = new EmbedBuilder();
            e.setColor(Color.RED);
            e.setTitle("Gayness Levels");
            e.setDescription("There is a " + random + "% chance that " + person + " " + bruhplsstop + " " + random2 + "% gay.");
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows you how gay you are\n" +
                "Usage: `a!gay`";
    }

    @Override
    public String getInvoke() {
        return "gay";
    }
}
