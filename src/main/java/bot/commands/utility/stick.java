package bot.commands.utility;

import bot.Constants;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class stick implements ICommand {
    private boolean stickOn = false;
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!args.isEmpty()) {
            String sm = String.join(" ", args);
            event.getChannel().sendMessage().queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Sticks a message to a channel\n" +
                "Usage: `"+ Constants.PREFIX + getInvoke() + " [message]`";
    }

    @Override
    public String getInvoke() {
        return "stick";
    }
}
