package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Say implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
        } else {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                    .setDescription(String.join(" ", args))
                    .build()
            ).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Says whatever is given in arguments\nUsage: `a!say [message]`";
    }

    @Override
    public String getInvoke() {
        return "say";
    }
}
