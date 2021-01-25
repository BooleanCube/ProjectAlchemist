package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class fight implements ICommand {
    public static HashMap<Long, Long> delay = new HashMap<>();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            if (Long.parseLong(args.get(0)) < 50) {
                event.getChannel().sendMessage("You must fight for a minimum of 50 coins!").queue();
                return;
            }
            if (Tools.getGold(event.getMember()).get(1) < Long.parseLong(args.get(0))) {
                event.getChannel().sendMessage("You don't have that many coins in you chest!").queue();
                return;
            }
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
                Member m = event.getGuild().getMembers().get(((int) (Math.random() * 100000000) % event.getGuild().getMembers().size()));
                while (m.getUser().isBot() || m.getUser().getIdLong() == event.getMember().getIdLong()) {
                    m = event.getGuild().getMembers().get(((int) (Math.random() * 1000000000) % event.getGuild().getMembers().size()));
                }
                Member finalM = m;
                event.getChannel().sendMessage("The fight begins...").queue(message -> {
                    new fight2(message, event.getMember(), finalM, Long.parseLong(args.get(0)), event).start();
                });
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
        return "Fight a random user in the server for a specified amount of gold\n" +
                "Usage: `a!fight [gold]`";
    }

    @Override
    public String getInvoke() {
        return "fight";
    }
}
class fight2 {
    public Message message;
    public int h1 = 100;
    public int h2 = 100;
    public Member m1;
    public Member m2;
    public GuildMessageReceivedEvent event;
    public long gold;
    public fight2(Message message, Member m1, Member m2, long gold, GuildMessageReceivedEvent event) {
        this.message = message;
        this.m1 = m1;
        this.m2 = m2;
        this.gold = gold;
        this.event = event;
    }
    public void start() {
        String body = "";
        int turn = 1;
        while(h1 > 0 && h2 > 0) {
            boolean damage = new Random().nextBoolean();
            if(damage) {
                int random = ((int)(Math.random()*100)%31)+10;
                if(turn % 2 == 1) {
                    h2 -= random;
                } else {
                    h1 -= random;
                }
                if(h1 < 0) {
                    random -= h1;
                    h1 = 0;
                } if(h2 < 0) {
                    random -= h2;
                    h2 = 0;
                }
                body += turn % 2 == 1 ? "`" + m1.getUser().getName() + "` attacked `" + m2.getUser().getName() + "` for **" + random + " damage**!\n" : "`" + m2.getUser().getName() + "` attacked `" + m1.getUser().getName() + "` for **" + random + " damage**!\n";
                message.editMessage(new EmbedBuilder()
                        .setTitle("Fight!")
                        .setDescription(body)
                        .setFooter(m1.getUser().getName() + "'s health: " + h1 + " health\n" +
                                m2.getUser().getName() + "'s health: " + h2 + " health")
                        .build()).queue();
            } else {
                int random = ((int)(Math.random()*100)%11)+1;
                if(turn % 2 == 1) {
                    h1 += random;
                } else {
                    h2 += random;
                }
                body += turn % 2 == 1 ? "`" + m1.getUser().getName() + "` gained **" + random + "** health!\n" : "`" + m2.getUser().getName() + "` gained **" + random + "** health!\n";
                message.editMessage(new EmbedBuilder()
                        .setTitle("Fight!")
                        .setDescription(body)
                        .setFooter(m1.getUser().getName() + "'s health: " + h1 + " health\n" +
                                m2.getUser().getName() + "'s health: " + h2 + " health")
                        .build()).queue();
            }
            for(int i=0; i<20; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
            turn++;
        }
        if(h1 <= 0) {
            message.editMessage(new EmbedBuilder()
                    .setTitle(m2.getUser().getName() + " Won!")
                    .setDescription(body)
                    .setFooter(m1.getUser().getName() + "'s health: " + 0 + " health\n" +
                            m2.getUser().getName() + "'s health: " + h2 + " health")
                    .build()).queue();
            Tools.addGold(m2, gold);
            Tools.addGold(m1, -gold);
            event.getChannel().sendMessage("`" + m2.getUser().getName() + "` won **" + gold + " gold**!").queue();
        } else {
            message.editMessage(new EmbedBuilder()
                    .setTitle(m1.getUser().getName() + " Won!")
                    .setDescription(body)
                    .setFooter(m1.getUser().getName() + "'s health: " + h1 + " health\n" +
                            m2.getUser().getName() + "'s health: " + 0 + " health")
                    .build()).queue();
            Tools.addGold(m1, gold);
            event.getChannel().sendMessage("`" + m1.getUser().getName() + "` won **" + gold + " gold**!").queue();
        }
    }
}