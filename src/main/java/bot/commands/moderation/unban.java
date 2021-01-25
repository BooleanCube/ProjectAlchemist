package bot.commands.moderation;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class unban implements ICommand {
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
        if (!member.hasPermission(Permission.BAN_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("You don't have permission to use this command").queue();
            return;
        }
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("I can't unban that user or I don't have the ban members permission").queue();
            return;
        }
        event.getGuild().unban(target.getUser()).queue();
        channel.sendMessage("Success!").queue();
    }

    @Override
    public String getHelp() {
        return "Unbans a user from the server. Note: Mention the user!\n" +
                "Usage: `a!unban [user]`";
    }

    @Override
    public String getInvoke() {
        return "unban";
    }
}
