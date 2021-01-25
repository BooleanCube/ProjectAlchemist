package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class inventory implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            List<String> inv = Tools.getInv(event.getMember());
            String toSend = "";
            if(inv.isEmpty()) {
                toSend = "**Empty Inventory!**";
            } else {
                toSend = String.join("\n", inv);
            }
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle(event.getMember().getUser().getName() + "'s Inventory")
                    .setDescription(toSend)
                    .setFooter("You can sell collectibles in your inventory! Don't type the item type when you are trying to sell an item! (Usage: a!sell [item])")
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows your inventory\n" +
                "Usage: `a!inventory`";
    }

    @Override
    public String getInvoke() {
        return "inventory";
    }
}
