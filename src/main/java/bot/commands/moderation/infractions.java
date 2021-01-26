package bot.commands.moderation;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class infractions implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size()==1) {
            if(event.getMessage().getMentionedMembers().size()==1) {
                Member target = event.getMessage().getMentionedMembers().get(0);
                EmbedBuilder e = new EmbedBuilder()
                        .setAuthor(target.getEffectiveName(), target.getUser().getAvatarUrl(), target.getUser().getEffectiveAvatarUrl())
                        .setTitle("Infractions Check")
                        .addField("Member:", target.getEffectiveName(), true)
                        .addField("# of infractions:", Tools.getInfractions(target) + " infractions", true);
                event.getChannel().sendMessage(e.build()).queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows the number of infractions a user has\nUsage: `a!infractions [user]`";
    }

    @Override
    public String getInvoke() {
        return "infractions";
    }
}
