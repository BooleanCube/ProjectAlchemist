package bot.commands.extra;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class server implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            String msg = "**``Alchemist Official™``**\n" +
                    "**This is the official server for this bot! If anything seems wrong report in this server and admin or developers will fix it asap!\n\n" +
                    "https://discord.gg/XVjNq8M**";
            EmbedBuilder e = new EmbedBuilder().setTitle("My server: ").setDescription(msg).setImage(event.getJDA().getSelfUser().getAvatarUrl());
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Gives you a link to the bots main server\n" +
                "Usage: `a!myserver`";
    }

    @Override
    public String getInvoke() {
        return "myserver";
    }
}
