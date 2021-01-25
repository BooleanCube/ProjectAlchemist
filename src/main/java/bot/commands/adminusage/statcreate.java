package bot.commands.adminusage;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class statcreate implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty() && event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            if (event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", true).isEmpty()) {
                int members = event.getGuild().getMembers().size();
                int bots = 0;
                int humans = 0;
                for (Member m : event.getGuild().getMembers()) {
                    if (m.getUser().isBot()) {
                        bots++;
                    }
                }
                humans = members - bots;
                int channels = event.getGuild().getTextChannels().size() + event.getGuild().getVoiceChannels().size();
                int categories = event.getGuild().getCategories().size();
                event.getGuild().createCategory("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA").complete();
                Category statscat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", true).get(0);
                statscat.createPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.VIEW_CHANNEL).queue();
                statscat.createVoiceChannel("Members: " + members).complete();
                statscat.createVoiceChannel("Humans: " + humans).complete();
                statscat.createVoiceChannel("Bots: " + bots).complete();
                statscat.createVoiceChannel("Channels: " + (channels + 4)).complete();
                statscat.createVoiceChannel("Categories: " + (categories + 1)).complete();
            } else {
                event.getChannel().sendMessage("You already created a server stats category! You can always update your server stats category if you think its wrong by typing: `a!updateserverstats`").queue();
                return;
            }
        } else if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessage("You don't have the permission to use this command!").queue();
            return;
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Creates a category with info about server\n" +
                "Usage: `a!createserverstats`";
    }

    @Override
    public String getInvoke() {
        return "createserverstats";
    }
}
