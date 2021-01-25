package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class deposit implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            event.getChannel().sendMessage(Tools.deposit(event.getMember(), args.get(0))).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Deposits gold from your chest to the bank\n" +
                "Usage: `a!deposit [amount]`";
    }

    @Override
    public String getInvoke() {
        return "deposit";
    }
}
