package bot.commands.utility;

import bot.CommandManager;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class listallcommands implements ICommand {

    public CommandManager manager;

    public listallcommands(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder();
            e.setTitle("A List of all my commands:");
            StringBuilder descriptBuilder = e.getDescriptionBuilder();
            manager.getCommands().forEach(
                    (command) -> descriptBuilder.append('`').append(command.getInvoke()).append("`\n")
            );
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows a complete list of all my commands from all of my categories\n" +
                "Usage: `a!listallcommands`";
    }

    @Override
    public String getInvoke() {
        return "listallcommands";
    }
}
