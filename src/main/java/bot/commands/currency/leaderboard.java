package bot.commands.currency;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class leaderboard implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
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
    }

    @Override
    public String getHelp() {
        return "Displays the servers leaderboard\n" +
                "Usage: `a!leaderboard`";
    }

    @Override
    public String getInvoke() {
        return "leaderboard";
    }
}
