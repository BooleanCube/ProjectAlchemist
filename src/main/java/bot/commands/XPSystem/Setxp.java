package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Setxp implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.size() == 2) {
            Member member = event.getGuild().getMemberById(Long.parseLong(args.get(0).replaceAll("<@", "").replaceAll(">", "").replaceAll("!", "")));
            if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage("You do not have the Manage Server Permission in order to use this command!").queue();
                return;
            }
            long xp = 0;
            try {
                xp = Long.parseLong(args.get(1));
            } catch(NumberFormatException nfe) {
                event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
                return;
            }
            long toGive = xp-Tools.getXP(member.getIdLong(), event.getGuild().getIdLong());
            Tools.addXP(member.getIdLong(), event.getGuild().getIdLong(), (int)toGive);
            event.getChannel().sendMessage("Successfully set **" + member.getUser().getAsTag() + "**'s XP to **" + xp + " XP**").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Sets the XP of a user to the amount given in the arguments\nUsage: `a!setxp [user] [xp]`";
    }

    @Override
    public String getInvoke() {
        return "setxp";
    }
}
