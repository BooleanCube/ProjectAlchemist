package bot.commands.moderation;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class unmute implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!args.isEmpty()) {
            if(event.getMessage().getMentionedMembers().size() != 1) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
            Member toMute = event.getMessage().getMentionedMembers().get(0);
            if(!event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS) || !event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                event.getChannel().sendMessage("You don't have enough perms to unmute others!").queue();
                return;
            }
            if(!args.get(0).startsWith("<@!")) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
            boolean contains = false;
            for (Role r: toMute.getRoles()){
                if (r.getName().equalsIgnoreCase("Muted")) {
                    contains = true;
                }
            }
            if(!contains) {
                event.getChannel().sendMessage(toMute.getUser().getName() + " isn't muted!").queue();
            }
            Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
            event.getGuild().removeRoleFromMember(toMute, muted).queue();
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("[UNMUTE] " + toMute.getEffectiveName())
                    .addField("Muted Member:", toMute.getEffectiveName(), true)
                    .addField("Moderator:", event.getMember().getEffectiveName(), true)
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
        return "Unmutes a member\n" +
                "Usage: `a!unmute [user]`";
    }

    @Override
    public String getInvoke() {
        return "unmute";
    }
}
