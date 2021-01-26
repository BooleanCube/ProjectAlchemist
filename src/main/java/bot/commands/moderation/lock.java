package bot.commands.moderation;

import bot.Tools;
//import bot.commands.databases.CustomizableDatabaseManager;
import bot.commands.logs.logs;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class lock implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.size()<=1) {
            int secs = -1;
            if(!Tools.isAdmin(event.getMember())) {
                event.getChannel().sendMessage("You don't have the permission to use this command!").queue();
                return;
            }
            if(args.size()==0) {
                TextChannel t = event.getChannel();
                for(Role r : event.getGuild().getRoles()) {
                    if(r.hasPermission(Permission.MANAGE_SERVER)) {
                        continue;
                    }
                    t.upsertPermissionOverride(r).setDeny(Permission.MESSAGE_WRITE).complete();
                }
            } else {
                try {
                    secs = Integer.parseInt(args.get(0));
                } catch (Exception e) {
                    event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                    return;
                }
                TextChannel t = event.getChannel();
                for (Role r : event.getGuild().getRoles()) {
                    if (r.hasPermission(Permission.MANAGE_SERVER)) {
                        continue;
                    }
                    t.upsertPermissionOverride(r).setDeny(Permission.MESSAGE_WRITE).complete();
                }
                for (Role r : event.getGuild().getRoles()) {
                    if (r.hasPermission(Permission.MANAGE_SERVER)) {
                        continue;
                    }
                    t.upsertPermissionOverride(r).setAllow(Permission.MESSAGE_WRITE).queueAfter(secs, TimeUnit.SECONDS);
                }
            }
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("[LOCK] `#" + event.getChannel().getName() + "`")
                    .addField("Locked Channel:", event.getChannel().getAsMention(), true)
                    .addField("Moderator:", event.getMember().getEffectiveName(), true)
                    .addField("Duration:", secs == -1 ? "Until unlocked" : secs + " seconds", false)
                    .setAuthor(event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
            if (Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
            if (!event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
                event.getGuild().getTextChannelsByName("logs", true).get(0).sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Locks a channel such that nobody except admin can talk in it for a temporary amount of seconds.\nUsage: `a!lock [secs(optional)]`";
    }

    @Override
    public String getInvoke() {
        return "lock";
    }
}
