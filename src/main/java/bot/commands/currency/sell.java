package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class sell implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            String item = args.get(0);
            if(item.indexOf("x") > 0) {
                event.getChannel().sendMessage("You can't sell more than one object at a time!").queue();
                return;
            }
            String actitem = "";
            boolean flag = false;
            for(String s : Tools.accessories) {
                final boolean b = s.toLowerCase().startsWith(item.toLowerCase());
                if(b && (Tools.trimX(s).endsWith("(Usable-Item)") || Tools.trimX(s).endsWith("(Used)"))) {
                    event.getChannel().sendMessage("You can't sell a usable/used item!").queue();
                    return;
                } else if(b) {
                    actitem = s;
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                event.getChannel().sendMessage("That is not an item in your inventory that you can sell!").queue();
                return;
            }
            if(!contains(Tools.getInv(event.getMember()), actitem)) {
                event.getChannel().sendMessage("You don't have that item in you inventory!").queue();
                return;
            }
            Tools.removeFromInv(event.getMember(), actitem);
            int random = ((int)(Math.random() * 100)%11)+10;
            Tools.addGold(event.getMember(), random);
            event.getChannel().sendMessage("You sold the **" + item + "** for **" + random + " gold**!").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command usage!\n" + getHelp()).queue();
            return;
        }
    }

    public static boolean contains(List<String> l, String item) {
        for(String s : l) {
            if(s.toLowerCase().startsWith(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getHelp() {
        return "Sells an item for a random price. Note: You can't sell Usable or Used items!\n" +
                "Usage: `a!sell [item]`";
    }

    @Override
    public String getInvoke() {
        return "sell";
    }
}
