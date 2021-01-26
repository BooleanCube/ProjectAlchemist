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

public class unlock implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            if(!Tools.isAdmin(event.getMember())) {
                event.getChannel().sendMessage("You don't have the permission to use this command!").queue();
                return;
            }
            TextChannel t = event.getChannel();
            for(Role r : event.getGuild().getRoles()) {
                t.upsertPermissionOverride(r).setAllow(Permission.MESSAGE_WRITE).complete();
            }
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("[UNLOCK] " + "`#" + event.getChannel().getName()+"`")
                    .addField("Unlocked Channel:", event.getChannel().getAsMention(), true)
                    .addField("Moderator:", event.getMember().getEffectiveName(), true)
                    .setAuthor(event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
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
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Unlocks the channel such that everybody can talk in channel.\nUsage: `a!unlock`";
    }

    @Override
    public String getInvoke() {
        return "unlock";
    }
}
