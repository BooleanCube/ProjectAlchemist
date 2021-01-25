package bot.commands.currency;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class store implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder();
            e.setTitle("Alchemists' Store:");
            e.setDescription("Available Items:\n\n");
            e.appendDescription("-**creditcard**: **[PRICE = 200 gold]** Expands bank space for gold! Note: This item doesn't go into your inventory\n" +
                    "-**lock**: **[PRICE = 300 gold]** Adds a lock for your chest such that the next person that tries to steal from you will not succeed and pay 50 gold as a price\n");
            e.setFooter("To buy an item from the store type: `a!buy [item]`. Items are the bold words above! *Note: The items must be spelled exactly!*", event.getAuthor().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Displays a store where you buy all sorts of goods in exchange for gold!\n" +
                "Usage: `a!store`";
    }

    @Override
    public String getInvoke() {
        return "store";
    }
}
