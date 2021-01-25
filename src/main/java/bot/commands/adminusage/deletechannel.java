package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class deletechannel implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.size() == 1) {
                if(event.getMessage().getMentionedChannels().size() == 1) {
                    event.getMessage().getMentionedChannels().get(0).delete().queue();
                } else {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("You do not have the perms to use this bot!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Deletes a channel. Note: You have to mention the channel (Mention a channel by putting a pound symbol in front of the channel name)\n" +
                "Usage: `a!deletechannel [channel]`";
    }

    @Override
    public String getInvoke() {
        return "deletechannel";
    }
}
