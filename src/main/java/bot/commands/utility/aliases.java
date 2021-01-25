package bot.commands.utility;

import bot.Tools;
import bot.commands.currency.deposit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class aliases extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase("a!lb")) {
            HashMap<Long, ArrayList<Member>> bruh = new HashMap<>();
            ArrayList<Long> banks = new ArrayList<>();
            ArrayList<Long> authorGold = new ArrayList<>();
            for(Member m : event.getGuild().getMembers()) {
                if(m.getUser().isBot()) {
                    continue;
                }
                long Gold = Tools.getGold(m).get(1);
                banks.add(Gold);
                if(bruh.containsKey(Gold)) {
                    bruh.get(Gold).add(m);
                } else {
                    ArrayList<Member> ar = new ArrayList<>();
                    ar.add(m);
                    bruh.put(Gold, ar);
                }
                if(event.getMember().getUser().getIdLong() == m.getUser().getIdLong()) {
                    authorGold.add(Gold);
                }
            }
            EmbedBuilder e = new EmbedBuilder();
            e.setTitle(event.getGuild().getName() + "'s leaderboard");
            e.setDescription("");
            Collections.sort(banks);
            String[] emojis = {"\uD83E\uDD47", "\uD83E\uDD48", "\uD83E\uDD49"};
            int a = 0;
            int len = 0;
            if(event.getGuild().getMembers().size() >= 3) {
                len = 4;
            } else {
                len = 2;
            }
            for(int i=banks.size()-1; i>banks.size()-len; i--) {
                String text = emojis[a] + ": `" + bruh.get(banks.get(i)).get(0).getUser().getName() + "`(" + banks.get(i)  + " gold)" + "\n";
                bruh.get(banks.get(i)).remove(0);
                if(bruh.get(banks.get(i)).isEmpty()) {
                    bruh.remove(banks.get(i));
                }
                a++;
                e.appendDescription(text);
            }
            Collections.reverse(banks);
            String text = "\n`#" + (Collections.indexOfSubList(banks, authorGold) + 1) + "`: `" + event.getMember().getUser().getName() + "`(" + Tools.getGold(event.getMember()).get(1) + " gold)";
            e.appendDescription(text);
            event.getChannel().sendMessage(e.build()).queue();
        } else if(msg.toLowerCase().startsWith("a!dep ")) {
            String[] args = msg.split(" ");
            if(args.length-1 == 1) {
                event.getChannel().sendMessage(Tools.deposit(event.getMember(), args[1])).queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + "Deposits gold from your chest to the bank\n" +
                        "Usage: `a!deposit [amount]`").queue();
                return;
            }
        } else if(msg.toLowerCase().startsWith("a!with ")) {
            String[] args = msg.split(" ");
            if(args.length-1 == 1) {
                event.getChannel().sendMessage(Tools.withdraw(event.getMember(), args[1])).queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + "Withdraws money from the bank to your chest\n" +
                        "Usage: `a!withdraw [amount]`").queue();
                return;
            }
        }
    }
}
