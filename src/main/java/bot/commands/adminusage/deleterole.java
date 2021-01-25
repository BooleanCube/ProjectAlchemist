package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class deleterole implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {

        } else {
            event.getChannel().sendMessage("You do not have the perms to use this bot!").queue();
            return;
        }
        if(args.size() == 1) {
            if(event.getMessage().getMentionedRoles().size() == 1) {
                event.getMessage().getMentionedRoles().get(0).delete().queue();
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
        return "Deletes a role. Note: You must mention the role\n" +
                "Usage: `a!deleterole [role]`";
    }

    @Override
    public String getInvoke() {
        return "deleterole";
    }
}
