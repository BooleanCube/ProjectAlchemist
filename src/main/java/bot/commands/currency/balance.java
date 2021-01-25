package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.List;

public class balance implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            User boi = event.getAuthor();
            List<Long> list = Tools.getGold(event.getMember());
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(boi.getName() + "'s Balance:");
            embed.addField("Bank:", list.get(0).toString() + "/" + list.get(2).toString() + " gold", true);
            embed.addField("Chest:", list.get(1).toString() + " gold", true);
            event.getChannel().sendMessage(embed.build()).queue();
        } else if(args.size() == 1 && event.getMessage().getMentionedMembers().size() == 1) {
            User boi = event.getMessage().getMentionedMembers().get(0).getUser();
            if(boi.isBot()) {
                event.getChannel().sendMessage("Other bots are not allowed to have gold!").queue();
                return;
            }
            List<Long> list = Tools.getGold(event.getMessage().getMentionedMembers().get(0));
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(boi.getName() + "'s Balance:");
            embed.addField("Bank:", list.get(0).toString() + "/" + list.get(2).toString() + " gold", true);
            embed.addField("Chest:", list.get(1).toString() + " gold", true);
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Returns a user's balance\n" +
                "Usage: `a!bal [user(optional)]`";
    }

    @Override
    public String getInvoke() {
        return "bal";
    }
}
