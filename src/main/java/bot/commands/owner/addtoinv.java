package bot.commands.owner;

import bot.Constants;
import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class addtoinv implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 2) {
            if(event.getAuthor().getIdLong() == Constants.OWNER) {
                Tools.addInv(event.getMessage().getMentionedMembers().get(0), Tools.accessories[Integer.parseInt(args.get(0))]);
                event.getChannel().sendMessage("done").queue();
            } else {
                event.getChannel().sendMessage("You don't have the perms to use this command!").queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Adds to inventory\n" +
                "Usage: `a!addToInv [item_number] [mentioned_member]`";
    }

    @Override
    public String getInvoke() {
        return "addtoinv";
    }
}
