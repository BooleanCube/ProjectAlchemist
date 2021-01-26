package bot.commands.currency;

import bot.Constants;
import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class search implements ICommand {
    public static HashMap<Long, Long> delay = new HashMap<>();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            long timeDelayed = 0;
            if(delay.containsKey(event.getAuthor().getIdLong())) {
                timeDelayed = System.currentTimeMillis() - delay.get(event.getAuthor().getIdLong());
            } else {
                timeDelayed = (120*1000);
            }
            if(timeDelayed >= (120*1000)) {
                if (delay.containsKey(event.getAuthor().getIdLong())) {
                    delay.remove(event.getAuthor().getIdLong());
                }
                delay.put(event.getAuthor().getIdLong(), System.currentTimeMillis());
                String[] locations = {"in the pantry", "in the beaker", "in the grass", "inside a fossil", "in you pocket", "in the fountain", "at the beach", "in the marketplace", "by the swimming pool", "in your lunch", "on the sidewalk", "behind your ear", "in the sink", "inside a toilet paper roll", "in a hat"};
                boolean more = new Random().nextBoolean();
                int loc = (int) (Math.random() * 100) % locations.length;
                if (more) {
                    int random = ((int) (Math.random() * 100) % 30) + 1;
                    event.getChannel().sendMessage("You found **" + random + " gold** " + locations[loc]).queue();
                    Tools.addGold(event.getMember(), random);
                } else {
                    event.getChannel().sendMessage("You had no luck finding any coins!").queue();
                }
            } else {
                String time = Tools.secondsToTime(((120*1000) - timeDelayed)/1000);
                EmbedBuilder e = new EmbedBuilder()
                        .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setDescription("Please Wait! You have " + time + " left!");
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Searches a random place for coins\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "search";
    }
}
