package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.lang.model.element.ElementVisitor;
import java.io.IOException;
import java.util.List;

public class XP implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            if(!Tools.XPon(event.getGuild().getIdLong())) {
                event.getChannel().sendMessage("XP System has been turned OFF in this server!").queue();
                return;
            }
            long level = Tools.TotalXPToLevel(Tools.getXP(event.getAuthor().getIdLong(), event.getGuild().getIdLong()));
            long xp = Tools.getXP(event.getAuthor().getIdLong(), event.getGuild().getIdLong());
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                    .addField("XP:", level != 0 ? (xp-Tools.levelToXP(level-1)) + "/" + Tools.levelToXP(level) + "\n(total: " + xp + ")" : (xp) + "/" + Tools.levelToXP(level) + "\n(total: " + xp + ")", true)
                    .addField("Level (Completion):", level != 0 ? level + " (" + (int) (((xp-Tools.levelToXP(level-1))*100)/Tools.levelToXP(level)) + "%)" : level + " (" + (int) ((xp*100)/Tools.levelToXP(level)) + "%)", true)
                    .addField("Rank:", Tools.getRank(event.getMember().getIdLong(), event.getGuild()) + "/" + event.getGuild().getMembers().size(), true)
                    .build()).queue();
        } else if (args.size()==1) {
            Member retreive = null;
            if(event.getMessage().getMentionedMembers().size()==1) {
                retreive = event.getMessage().getMentionedMembers().get(0);
            } else {
                try {
                    long id = Long.parseLong(args.get(0));
                    retreive = event.getGuild().getMemberById(id);
                } catch(Exception e) {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
            }
            if(!Tools.XPon(event.getGuild().getIdLong())) {
                event.getChannel().sendMessage("XP System has been turned OFF in this server!").queue();
                return;
            }
            long level = Tools.TotalXPToLevel(Tools.getXP(retreive.getIdLong(), event.getGuild().getIdLong()));
            long xp = Tools.getXP(retreive.getIdLong(), event.getGuild().getIdLong());
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(retreive.getUser().getName(), retreive.getUser().getAvatarUrl(), retreive.getUser().getEffectiveAvatarUrl())
                    .addField("XP:", level != 0 ? (xp-Tools.levelToXP(level-1)) + "/" + Tools.levelToXP(level) + "\n(total: " + xp + ")" : (xp) + "/" + Tools.levelToXP(level) + "\n(total: " + xp + ")", true)
                    .addField("Level (Completion):", level != 0 ? level + " (" + (int) (((xp-Tools.levelToXP(level-1))*100)/Tools.levelToXP(level)) + "%)" : level + " (" + (int) ((xp*100)/Tools.levelToXP(level)) + "%)", true)
                    .addField("Rank:", Tools.getRank(retreive.getIdLong(), event.getGuild()) + "/" + event.getGuild().getMembers().size(), true)
                    .build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows the amount of XP you have in the specific server\nUsage: `a!xp`";
    }

    @Override
    public String getInvoke() {
        return "xp";
    }
}
