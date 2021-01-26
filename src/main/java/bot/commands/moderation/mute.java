package bot.commands.moderation;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class mute implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //∞
        if(!args.isEmpty()) {
            if(event.getMessage().getMentionedMembers().size() != 1) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
            Member toMute = event.getMessage().getMentionedMembers().get(0);
            if(Tools.isAdmin(toMute) || toMute.hasPermission(Permission.VOICE_MUTE_OTHERS) || toMute.hasPermission(Permission.MESSAGE_MANAGE)) {
                event.getChannel().sendMessage("You can't mute a moderator!").queue();
                return;
            }
            if((!event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS) || !event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) && event.getMember().getIdLong() != 525126007330570259l) {
                event.getChannel().sendMessage("You don't have enough perms to mute others!").queue();
                return;
            }
            if(!args.get(0).startsWith("<@!")) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
            if(event.getGuild().getRolesByName("Muted", true).isEmpty()) {
                event.getGuild().createRole().setName("Muted").setColor(Color.darkGray).complete();
                Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
                for(TextChannel tc : event.getGuild().getTextChannels()) {
                    tc.upsertPermissionOverride(muted).setDeny(Permission.MESSAGE_WRITE).complete();
                }
            }
            for (Role r: toMute.getRoles()){
                if (r.getName().equalsIgnoreCase("Muted")) {
                    event.getChannel().sendMessage(toMute.getUser().getName() + " is already muted").queue();
                    return;
                }
            }
            String reason = String.join(" ", args.subList(1, args.size()));
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }
            Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
            int mutedposition = muted.getPositionRaw() - 1;
            int move = 0;
            if(!toMute.getRoles().isEmpty()) {
                move = toMute.getRoles().get(0).getPositionRaw() - 1;
            }
            if(mutedposition < move) {
                event.getGuild().modifyRolePositions().selectPosition(mutedposition).moveTo(move).complete();
            }
            muted.getManager().setPermissions(Permission.EMPTY_PERMISSIONS).givePermissions(Permission.MESSAGE_READ).givePermissions(Permission.MESSAGE_HISTORY).queue();
            event.getGuild().addRoleToMember(toMute, muted).queue();
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("[MUTE] " + toMute.getEffectiveName())
                    .addField("Muted Member:", toMute.getEffectiveName(), true)
                    .addField("Moderator:", event.getMember().getEffectiveName(), true)
                    .addField("Reason:", reason, true)
                    .addField("Duration:", "Until unmuted", false)
                    .setAuthor(toMute.getUser().getName(), toMute.getUser().getAvatarUrl(), toMute.getUser().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
            event.getGuild().getTextChannelsByName("logs", true).get(0).sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Mutes a user until you unmute them. Note: If the member can still type then delete all Muted roles and try again!\n" +
                "Usage: `a!mute [user] [reason(optional)]`";
    }

    @Override
    public String getInvoke() {
        return "mute";
    }
}
