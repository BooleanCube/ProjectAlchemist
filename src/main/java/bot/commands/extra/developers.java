package bot.commands.extra;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class developers implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder();
            e.setTitle("My developers:");
            e.setDescription("`ProjectAlchemist#4690`\n`Giunk#4759`\n");
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Gives the developers names\n" +
                "Usage: `a!mydevelopers`";
    }

    @Override
    public String getInvoke() {
        return "mydevelopers";
    }
}
