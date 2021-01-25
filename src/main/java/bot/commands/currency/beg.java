package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class beg implements ICommand {
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
                if(delay.containsKey(event.getAuthor().getIdLong())) {
                    delay.remove(event.getAuthor().getIdLong());
                }
                delay.put(event.getAuthor().getIdLong(), System.currentTimeMillis());
                boolean more = false;
                if((int)(Math.random()*10)%2 == 0) {
                    more = true;
                }
                int lead = 0;
                if(more) {
                    lead += ((int)(Math.random()*1000) % 300) + 100;
                }
                String[] pplsformore = {"Obama Care", "Mr.Beast", "Kristofer Yee", "Michael Stevens", "Bill Gates", "Mark Zuckerberg", "Ellen", "Oprah Winfrey", "Jake Paul", "Logan Paul", "Jay-Z", "Elon Musk", "SpaceX", "Kanye West", "David Production", "Dude Perfect", "Joe Russo", "Anthony Russo"};
                String[] pplsfornone = {"**Bernie Sanders** is asking for financial support himself", "**Donald Trump** is angry because the stock market crashed", "**COVID-19** is crushing the stock market", "**Jim Carrey** grins and pulls out a green mask", "**Mark Wahlberg** *ignores you*", "**Your Mom** beats the crap out of you"};
                int random = (int)(Math.random()*1000);
                if(more) {
                    random %= pplsformore.length;
                    long gold = lead / 10;
                    gold += (int)(Math.random()*10) % 5;
                    Tools.addGold(event.getMember(), gold);
                    int randinv = (int)(Math.random()*100) % Tools.accessories.length;
                    boolean inv = new Random().nextBoolean();
                    if(!inv) {
                        Tools.addInv(event.getMember(), Tools.accessories[randinv]);
                    }
                    event.getChannel().sendMessage(inv ? "**" + pplsformore[random] + "** donated **" + lead + " lead pieces** out of which you made **" + gold + " gold**" : "**" + pplsformore[random] + "** donated **" + lead + " lead pieces** out of which you made **" + gold + " gold** and a **" + Tools.accessories[randinv] + "**").queue();
                } else {
                    random %= pplsfornone.length;
                    event.getChannel().sendMessage(pplsfornone[random]).queue();
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
        }
    }

    @Override
    public String getHelp() {
        return "Simulation of you begging for gold\n" +
                "Usage: `a!beg`";
    }

    @Override
    public String getInvoke() {
        return "beg";
    }
}
