package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class coinflip implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            boolean win = new Random().nextBoolean();
            String coin = "";
            if(!args.get(0).equalsIgnoreCase("heads") && !args.get(0).equalsIgnoreCase("tails")) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
            if(Tools.getGold(event.getMember()).get(1) < 50) {
                event.getChannel().sendMessage("You must have 50 gold in you chest to be able to use this command!").queue();
                return;
            }
            if(win) {
                coin = args.get(0);
            } else {
                coin = args.get(0).equalsIgnoreCase("tails") ? "heads" : "tails";
            }
            if(win) {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setAuthor(event.getMember().getUser().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setTitle("You won 50 gold!")
                        .setFooter("The coin flipped " + coin)
                .build()).queue();
                Tools.addGold(event.getMember(), 50);
            } else {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setAuthor(event.getMember().getUser().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setTitle("You lost 50 gold!")
                        .setFooter("The coin flipped " + coin)
                        .build()).queue();
                Tools.addGold(event.getMember(), -50);
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Flips a coin for 50 gold\n" +
                "Usage: `a!coinflip [heads|tails]`";
    }

    @Override
    public String getInvoke() {
        return "coinflip";
    }
}
