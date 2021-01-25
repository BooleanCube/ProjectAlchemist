package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class dmall implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!args.isEmpty()) {
            if(Tools.isAdmin(event.getMember())) {
                String toSend = event.getMessage().getContentRaw().substring(8).trim();
                for(Member m : event.getGuild().getMembers()) {
                    if(event.getGuild().getSelfMember().getIdLong() == m.getIdLong()) {
                        continue;
                    }
                    m.getUser().openPrivateChannel().complete().sendMessage(toSend).queue();
                }
                event.getChannel().sendMessage("Done!").queue();
            } else {
                event.getChannel().sendMessage("You don't have the perms to use this command!").queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "DM's an announcement message to everybody in the server. Note: I send the exact content of the specified announcement used in the command!\n" +
                "Usage: `a!dmall [message]`";
    }

    @Override
    public String getInvoke() {
        return "dmall";
    }
}
