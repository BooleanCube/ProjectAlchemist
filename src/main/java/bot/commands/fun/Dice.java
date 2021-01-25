package bot.commands.fun;

import bot.Constants;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dice implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        int sides = 6;
        int dices = 1;
        TextChannel channel = event.getChannel();

        if (!args.isEmpty()) {
            sides = Integer.parseInt(args.get(0));

            if (args.size() == 2) {
                dices = Integer.parseInt(args.get(1));
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }

        if (sides > 100) {
            channel.sendMessage("The maximum sides is 100").queue();

            return;
        }

        if (dices > 50) {
            channel.sendMessage("The maximum dices is 50").queue();

            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Dice Rolling Results:");
        int counter = 0;
        for (int d = 0; d < dices; d++) {
            int num = random.nextInt(1, sides);
            counter += num;
            builder.appendDescription("\uD83C\uDFB2 #")
                    .appendDescription(Integer.toString(d+1))
                    .appendDescription(": **")
                    .appendDescription(Integer.toString(num))
                    .appendDescription("**\n");
        }
        builder.setFooter("\nTotal: " + Integer.toString(counter));
        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Rolls a dice.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [sides] [dices]`";
    }

    @Override
    public String getInvoke() {
        return "dice";
    }
}
