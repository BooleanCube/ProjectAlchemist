package bot.commands.extra;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class support implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            String msg = "Need support? Please contact one of the developers(Usage: `a!mydevelopers`) or else check out my official server(Usage: `a!myserver`) to get support!";
            EmbedBuilder e = new EmbedBuilder().setTitle("Support on it's way!").setDescription(msg);
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "In need of support use this command\n" +
                "Usage: `a!support`";
    }

    @Override
    public String getInvoke() {
        return "support";
    }
}
