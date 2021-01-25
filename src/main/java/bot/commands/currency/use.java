package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class use implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            String wanted = args.get(0);
            boolean flag = false;
            for(String item : Tools.getInv(event.getMember())) {
                if(item.toLowerCase().startsWith(wanted) && item.toLowerCase().endsWith("(used)")) {
                    event.getChannel().sendMessage("You have already used this item. Wait until it expires to use this item!").queue();
                    return;
                } else if(item.toLowerCase().startsWith(wanted.toLowerCase())) {
                    Tools.removeFromInv(event.getMember(), item);
                    Tools.addInv(event.getMember(), item.substring(0, item.indexOf("("))+"(Used)");
                    event.getChannel().sendMessage("Successfully used the " + wanted).queue();
                    flag = true;
                }
            }
            if(!flag) {
                 event.getChannel().sendMessage("That is not an item in your inventory that you can sell!").queue();
                 return;
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Uses an item in your inventory\n" +
                "Usage: `a!use [item]`";
    }

    @Override
    public String getInvoke() {
        return "use";
    }
}
