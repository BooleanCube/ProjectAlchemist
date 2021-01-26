package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class ToggleNotifs implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            boolean dmnotifs = Tools.DMLevelNotifs(event.getGuild(), event.getMember());
            Tools.toggleDMLevelNotifs(event.getGuild(), event.getMember());
            event.getChannel().sendMessage(dmnotifs ? "Level Notifications have been toggled OFF!" : "Level notifications have been toggled ON!").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Toggles Notifications for levels in the specific server\nUsage: `a!togglelevelnotifs`";
    }

    @Override
    public String getInvoke() {
        return "togglelevelnotifs";
    }
}
