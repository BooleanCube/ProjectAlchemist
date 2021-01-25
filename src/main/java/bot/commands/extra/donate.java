package bot.commands.extra;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class donate implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("Donation link")
                    .setDescription("https://www.gofundme.com/f/donate-to-alchemist")
                    .setAuthor("Alchemist", "https://www.gofundme.com/f/donate-to-alchemist", event.getGuild().getSelfMember().getUser().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Gives you a link to the donation page\n" +
                "Usage: `a!donate`";
    }

    @Override
    public String getInvoke() {
        return "donate";
    }
}
