package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class buy implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() >= 1) {
            String item = args.get(0);
            if(item.equalsIgnoreCase("creditcard")) {
                if(Tools.getGold(event.getMember()).get(1) < 200) {
                    event.getChannel().sendMessage("**You do not have enough gold in your chest to buy this item!**").queue();
                    return;
                }
                Tools.addGold(event.getMember(), -200);
                Tools.expandBank(event.getMember(), 50);
                event.getChannel().sendMessage("You successfully bought a credit card!").queue();
            }
            if(item.equalsIgnoreCase("lock")) {
                if(Tools.getGold(event.getMember()).get(1) < 300) {
                    event.getChannel().sendMessage("**You do not have enough gold in your chest to buy this item!**").queue();
                    return;
                }
                Tools.addGold(event.getMember(), -300);
                Tools.addInv(event.getMember(), "Lock(Usable-Item)");
                event.getChannel().sendMessage("You successfully bought a lock!").queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Buy an item in the store\n" +
                "Usage: `a!buy [item]`";
    }

    @Override
    public String getInvoke() {
        return "buy";
    }
}
