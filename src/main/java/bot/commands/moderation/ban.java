package bot.commands.moderation;

import bot.Tools;
////import bot.commands.databases.CustomizableDatabaseManager;
import bot.commands.logs.logs;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
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
            //no thing
        }
        String reason = String.join(" ", args.subList(2, args.size()));

        if ((!member.hasPermission(Permission.BAN_MEMBERS) || !member.canInteract(target)) && event.getMember().getIdLong() != 525126007330570259l) {
            channel.sendMessage("You don't have permission to use this command").queue();
            return;
        }


        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("I can't ban that user or I don't have the ban members permission").queue();
            return;
        }

        event.getGuild().ban(target, delayDays, String.format("Ban by: %#s, with reason: %s",
                event.getAuthor(), reason)).queue();
        target.getUser().openPrivateChannel().queue(chnl -> {
            chnl.sendMessage(event.getGuild().getIdLong()==415277564907487242l? "You were banned from the `" + event.getGuild().getName() + "` server!\n**Reason: " + reason + "**" : "You were banned from the `" + event.getGuild().getName() + "` server!\n**Reason: " + reason + "**\nBan Appeal Link: https://discord.gg/k9NJjxu").queue();
        });
        EmbedBuilder e = new EmbedBuilder()
                .setTitle("[BAN] " + target.getEffectiveName())
                .addField("Banned Member:", target.getEffectiveName(), true)
                .addField("Moderator:", event.getMember().getEffectiveName(), true)
                .addField("Reason:", reason, true)
                .addField("Duration:", delayDays==0?"Until unbanned":delayDays+" days", false)
                .setAuthor(target.getUser().getName(), target.getUser().getAvatarUrl(), target.getUser().getEffectiveAvatarUrl());
        event.getChannel().sendMessage(e.build()).queue();
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(!event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            event.getGuild().getTextChannelsByName("logs", true).get(0).sendMessage(e.build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Bans a user off the server\n" +
                "Usage: `a!ban [user] [num. of days] [reason]`";
    }

    @Override
    public String getInvoke() {
        return "ban";
    }
}
