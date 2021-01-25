package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class serverinfo implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder();
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle(event.getGuild().getName() + " Info:");
            int members = event.getGuild().getMembers().size();
            int bots = 0;
            Member owner = null;
            for(Member m : event.getGuild().getMembers()) {
                if(m.getUser().isBot()) {
                    bots++;
                }
                if(m.isOwner()) {
                    owner = m;
                }
            }
            int humans = members - bots;
            Date creation = Date.from(event.getGuild().getTimeCreated().toInstant());
            String url = event.getGuild().getIconUrl();
            int emotes = event.getGuild().getEmotes().size();
            int roles = event.getGuild().getRoles().size();
            String verification = event.getGuild().getVerificationLevel().toString();
            String contentfilter = event.getGuild().getExplicitContentLevel().getDescription();
            String region = event.getGuild().getRegionRaw();
            String statbod = "**Members: **" + members + "\n" +
                    "**Bots: **" + bots + "\n" +
                    "**Humans: **" + humans + "\n" +
                    "**Emotes: **" + emotes + "\n" +
                    "**Roles: **" + roles + "\n" +
                    "**Creation: **" + creation.toString() + "\n";
            String safety = "**Verification: **" + verification + "\n" +
                    "**Content Filter: **" + contentfilter + "\n";
            String infobod = "**Owner: **" + owner.getUser().getName() + "\n" +
                    "**Region: **" + region;
            e.addField("Statistics:", statbod, true);
            e.addField("Safety:", safety, true);
            e.addField("Info:", infobod, true);
            e.setImage(url);
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows you some information about this server!\n" +
                "Usage: `a!serverinfo`";
    }

    @Override
    public String getInvoke() {
        return "serverinfo";
    }
}
