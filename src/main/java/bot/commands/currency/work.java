package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class work implements ICommand {
    public static HashMap<Long, Long> delay = new HashMap<>();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            long timeDelayed = 0;
            if(delay.containsKey(event.getAuthor().getIdLong())) {
                timeDelayed = System.currentTimeMillis() - delay.get(event.getAuthor().getIdLong());
            } else {
                timeDelayed = (7200*1000);
            }
            if(timeDelayed >= (7200*1000)) {
                if(delay.containsKey(event.getAuthor().getIdLong())) {
                    delay.remove(event.getAuthor().getIdLong());
                }
                delay.put(event.getAuthor().getIdLong(), System.currentTimeMillis());
                int hours = ((int)(Math.random()*100) % 30) + 20;
                int Gold = ((int)(Math.random()*100)%12) + 2;
                int tot = hours * Gold;
                Tools.addGold(event.getMember(), tot);
                EmbedBuilder e = new EmbedBuilder()
                        .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setDescription("You worked for " + hours + " hours for " + Gold + " gold per hour, therefore you earned " + tot + " gold!");
                event.getChannel().sendMessage(e.build()).queue();
            } else {
                String time = Tools.secondsToTime(((7200*1000) - timeDelayed)/1000);
                EmbedBuilder e = new EmbedBuilder()
                        .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setDescription("Please Wait! You have " + time + " left!");
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Makes you work for gold!\n" +
                "Usage: `a!work`";
    }

    @Override
    public String getInvoke() {
        return "work";
    }
}
