package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class ToggleXP implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            if(!Tools.isAdmin(event.getMember())) {
                event.getChannel().sendMessage("You do not have the permission to use this command!").queue();
                return;
            }
            boolean flag = !Tools.XPon(event.getGuild().getIdLong());
            Tools.toggleXP(event.getGuild().getIdLong());
            event.getChannel().sendMessage(flag ? "XP System has been toggled ON." : "XP System has been toggled OFF.").queue();
        } else {

        }
    }

    @Override
    public String getHelp() {
        return "Toggles XP on and off\nUsage: `a!togglexp`";
    }

    @Override
    public String getInvoke() {
        return "togglexp";
    }
}
