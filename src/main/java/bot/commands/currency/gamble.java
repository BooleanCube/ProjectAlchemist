package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class gamble implements ICommand {
    public static HashMap<Long, Long> delay = new HashMap<>();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            long timeDelayed = 0;
            if(delay.containsKey(event.getAuthor().getIdLong())) {
                timeDelayed = System.currentTimeMillis() - delay.get(event.getAuthor().getIdLong());
            } else {
                timeDelayed = (30*1000);
            }
            if(timeDelayed >= (30*1000)) {
                if(delay.containsKey(event.getAuthor().getIdLong())) {
                    delay.remove(event.getAuthor().getIdLong());
                }
                delay.put(event.getAuthor().getIdLong(), System.currentTimeMillis());
                long num = 0;
                try {
                    num = Long.parseLong(args.get(0));
                } catch(Exception e) {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
                if(num == 0) {
                    event.getChannel().sendMessage("Nobody wants to gamble for 0 gold!").queue();
                }
                if(num > Tools.getGold(event.getMember()).get(1)) {
                    event.getChannel().sendMessage("You can't gamble for more than you already have in your chest!").queue();
                    return;
                }
                int alcran = ((int)(Math.random()*1000) % 10) + 3;
                int memran = ((int)(Math.random()*1000) % 11) + 2;
                if(memran == alcran) {
                    if(alcran == 12) {
                        memran--;
                    } else {
                        alcran++;
                    }
                }
                if(alcran > memran) {
                    Tools.addGold(event.getMember(), -num);
                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("You lost **" + num + " gold**!");
                    e.addField("You rolled:", Long.toString(memran),true);
                    e.addField("Alchemist rolled:", Long.toString(alcran), true);
                    event.getChannel().sendMessage(e.build()).queue();
                } else {
                    Tools.addGold(event.getMember(), num);
                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("You won **" + num + " gold**!");
                    e.addField("You rolled:", Long.toString(memran),true);
                    e.addField("Alchemist rolled:", Long.toString(alcran), true);
                    event.getChannel().sendMessage(e.build()).queue();
                }
            } else {
                String time = Tools.secondsToTime(((30*1000) - timeDelayed)/1000);
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
        return "Simulation of gambling for gold!\n" +
                "Usage: `a!gamble [amount]`";
    }

    @Override
    public String getInvoke() {
        return "gamble";
    }
}
