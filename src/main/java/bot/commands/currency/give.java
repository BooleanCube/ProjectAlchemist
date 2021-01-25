package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class give implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 2) {
            if(event.getMessage().getMentionedMembers().size() == 1) {
                long num = 0;
                try {
                    num = Long.parseLong(args.get(1));
                } catch(Exception e) {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
                if(event.getMessage().getMentionedMembers().get(0).getUser().isBot()) {
                    event.getChannel().sendMessage("Other bots are not allowed to have gold!").queue();
                    return;
                }
                event.getChannel().sendMessage(Tools.giveGold(event.getMember(), event.getMessage().getMentionedMembers().get(0), num)).queue();
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
        return "Gives a specified user a specified amount of gold\n" +
                "Usage: `a!give [user] [gold]`";
    }

    @Override
    public String getInvoke() {
        return "give";
    }
}
