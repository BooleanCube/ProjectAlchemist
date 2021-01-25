package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class statupdate implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.isEmpty()) {
                if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", true).isEmpty()) {
                    int members = event.getGuild().getMembers().size();
                    int bots = 0;
                    for(Member m : event.getGuild().getMembers()) {
                        if(m.getUser().isBot()) {
                            bots++;
                        }
                    }
                    int humans = members - bots;
                    int channels = event.getGuild().getTextChannels().size() + event.getGuild().getVoiceChannels().size();
                    int categories = event.getGuild().getCategories().size();
                    Category statscat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", true).get(0);
                    for(VoiceChannel vc : statscat.getVoiceChannels()) {
                        if(vc.getName().toLowerCase().startsWith("members: ")) {
                            vc.getManager().setName("Members: " + members).queue();
                        }
                        if(vc.getName().toLowerCase().startsWith("humans: ")) {
                            vc.getManager().setName("Humans: " + humans).queue();
                        }
                        if(vc.getName().toLowerCase().startsWith("bots: ")) {
                            vc.getManager().setName("Bots: " + bots).queue();
                        }
                        if(vc.getName().toLowerCase().startsWith("channels: ")) {
                            vc.getManager().setName("Channels: " + channels).queue();
                        }
                        if(vc.getName().toLowerCase().startsWith("Categories: ")) {
                            vc.getManager().setName("Categories: " + categories).queue();
                        }
                    }
                    event.getChannel().sendMessage("Done!").queue();
                }
            } else {
                event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("You do not have the perms to use this bot!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "If the server stats seem kinda fishy, use this command to see if I can fix it!\n" +
                "Usage: `a!updateserverstats`";
    }

    @Override
    public String getInvoke() {
        return "updateserverstats";
    }
}
