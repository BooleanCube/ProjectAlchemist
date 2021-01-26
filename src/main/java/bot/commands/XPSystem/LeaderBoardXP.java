package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LeaderBoardXP implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            List<Member> members = event.getGuild().getMembers();
            ArrayList<Long> XP = new ArrayList<>();
            HashMap<Long, ArrayList<Member>> XPAuthor = new HashMap<>();
            ArrayList<Long> authorsXP = new ArrayList<>();
            for(Member m : members) {
                if (m.getUser().isBot()) {
                    continue;
                }
                long MemberXP = Tools.getXP(m.getIdLong(), event.getGuild().getIdLong());
                XP.add(MemberXP);
                if (XPAuthor.containsKey(MemberXP)) {
                    XPAuthor.get(MemberXP).add(m);
                } else {
                    ArrayList<Member> a = new ArrayList<>();
                    a.add(m);
                    XPAuthor.put(MemberXP, a);
                }
                if (event.getMember().getIdLong() == m.getIdLong()) {
                    authorsXP.add(MemberXP);
                }
            }
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle(event.getGuild().getName() + "'s XP leaderboard")
                    .setDescription("");
            Collections.sort(XP);
            String[] emojis = {"\uD83E\uDD47", "\uD83E\uDD48", "\uD83E\uDD49"};
            int a = 0;
            int len = 0;
            if(event.getGuild().getMembers().size()>=3) {
                len = 4;
            } else {
                len = 2;
            }
            for(int i=XP.size()-1; i>XP.size()-len; i--) {
                String text = emojis[a] + " `" + XPAuthor.get(XP.get(i)).get(0).getUser().getName() + "`(" + XP.get(i)  + " XP)" + "\n";
                XPAuthor.get(XP.get(i)).remove(0);
                if(XPAuthor.get(XP.get(i)).isEmpty()) {
                    XPAuthor.remove(XP.get(i));
                }
                a++;
                e.appendDescription(text);
            }
            Collections.reverse(XP);
            String text = "\n`#" + (Collections.indexOfSubList(XP, authorsXP) + 1) + "`: `" + event.getMember().getUser().getName() + "`(" + authorsXP.get(0) + " XP)";
            e.appendDescription(text);
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Shows you the leaderboard for the XP of members in this server\nUsage: `a!ranks`";
    }

    @Override
    public String getInvoke() {
        return "ranks";
    }
}
