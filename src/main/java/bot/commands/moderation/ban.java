package bot.commands.moderation;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ban implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        int delayDays = 0;
        try {
            delayDays = Integer.parseInt(args.get(1));
        } catch(Exception e) {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
        String reason = String.join(" ", args.subList(2, args.size()));

        if (!member.hasPermission(Permission.BAN_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("You don't have permission to use this command").queue();
            return;
        }


        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("I can't ban that user or I don't have the ban members permission").queue();
            return;
        }


        event.getGuild().ban(target, delayDays, String.format("Ban by: %#s, with reason: %s",
                event.getAuthor(), reason)).queue();

        channel.sendMessage("Success!").queue();
    }

    @Override
    public String getHelp() {
        return "Bans a user off the server for 100 days\n" +
                "Usage: `a!ban [user] [num. of days] [reason]`";
    }

    @Override
    public String getInvoke() {
        return "ban";
    }
}
