package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class deletecategory implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.size() == 1) {
                Category c = null;
                for(Category bruh : event.getGuild().getCategories()) {
                    if(bruh.getName().equalsIgnoreCase(args.get(0))) {
                        c = bruh;
                    }
                }
                if(c == null) {
                    event.getChannel().sendMessage("This Category doesn't exist!").queue();
                    return;
                }
                c.delete().queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("You do not have the perms to use this bot!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Deletes a category\n" +
                "Usage: `a!deletecategory`";
    }

    @Override
    public String getInvoke() {
        return "deletecategory";
    }
}
