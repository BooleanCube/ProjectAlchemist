package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class createlogchannel implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty() && Tools.isAdmin(event.getMember())) {
            event.getGuild().createTextChannel("logs").complete().createPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.VIEW_CHANNEL).queue();
            event.getGuild().getTextChannelsByName("logs", true).get(0).sendMessage("<@" + event.getMember().getUser().getId() + "> I have created a logging channel where I will store all of my logs for you! At the moment we are in the state where only the admin(people with Manage Server permission) of this server can view this channel. You may play with the settings of the channel so other ppl can view it but I will still post the logs over here.\n" +
                    "**IMPORTANT**: **Do NOT change the name of the channel**. You can play with the settings and change the topic but do not change the channel's name. If you do so you will not receive the logs!").queue();
        } else if(!Tools.isAdmin(event.getMember())) {
            event.getChannel().sendMessage("You do not have the permission to use this command").queue();
            return;
        } else {
            event.getChannel().sendMessage("A logging channel already exists in your server!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Create a channel to store all logs\n" +
                "Usage: `a!createlogchannel`";
    }

    @Override
    public String getInvoke() {
        return "createlogchannel";
    }
}
