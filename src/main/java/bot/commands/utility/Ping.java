package bot.commands.utility;

import bot.Constants;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Ping implements ICommand {

    public void handle(List<String> args, final GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            event.getChannel().sendMessage("Pong!").queue((message) ->
                    message.editMessageFormat("Ping is %sms", event.getJDA().getGatewayPing()).queue()
            );
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    public String getHelp() {
        return "Gives you the bot ping!\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    public String getInvoke() {
        return "ping";
    }

}
