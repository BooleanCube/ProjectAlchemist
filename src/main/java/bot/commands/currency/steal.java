package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class steal implements ICommand {
    public static HashMap<Long, Long> delay = new HashMap<>();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1 && event.getMessage().getMentionedMembers().size()==1) {
            Member m = event.getMessage().getMentionedMembers().get(0);
            long boisGold = Tools.getGold(m).get(1);
            if (boisGold < 50) {
                event.getChannel().sendMessage("You can't steal from somebody who has less the 50 gold in his chest").queue();
                return;
            }
            if(Tools.getGold(event.getMember()).get(1) < 50) {
                event.getChannel().sendMessage("You must have 50 gold minimum in your chest!\n").queue();
                return;
            }
            long timeDelayed = 0;
            if(delay.containsKey(event.getAuthor().getIdLong())) {
                timeDelayed = System.currentTimeMillis() - delay.get(event.getAuthor().getIdLong());
            } else {
                timeDelayed = (1800*1000);
            }
            if(timeDelayed >= (1800*1000)) {
                if (delay.containsKey(event.getAuthor().getIdLong())) {
                    delay.remove(event.getAuthor().getIdLong());
                }
                delay.put(event.getAuthor().getIdLong(), System.currentTimeMillis());
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    boolean contains = false;
                    String toRemove = "";
                    for(String item : Tools.getInv(m)) {
                        if(item.startsWith("Lock(Used)")) { contains=true; toRemove=item; }
                    }
                    if ((int)(Math.random() * 10) % 2 == 0 && !contains) {
                        int sc = ((int) (Math.random() * 100) % 51);
                        Tools.giveGold(m, event.getMember(), sc);
                        event.getChannel().sendMessage("Successfully stole **" + sc + " gold** from " + m.getUser().getName()).queue();
                    } else {
                        if(contains) {
                            Tools.removeFromInv(m, toRemove);
                        }
                        Tools.addGold(event.getMember(), -50);
                        Tools.addGold(m, 50);
                        event.getChannel().sendMessage("You were caught stealing and you paid 50 gold as a price!").queue();
                    }
                } else {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
            } else {
                String time = Tools.secondsToTime(((1800*1000) - timeDelayed)/1000);
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
        return "Steals gold from a person\n" +
                "Usage: `a!steal [user]`";
    }

    @Override
    public String getInvoke() {
        return "steal";
    }
}
