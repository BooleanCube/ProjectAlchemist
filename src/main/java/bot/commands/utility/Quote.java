package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quote implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String[] arguments = args.toArray(new String[0]);
        if(args.size() == 1) {
            action(arguments, event);
        } else if(args.isEmpty()) {
            action(new String[]{}, event);
        }
    }

    @Override
    public String getHelp() {
        return "Quotes a message from the whole server\n" +
                "Usage: `a!quote [msg_id]`";
    }

    @Override
    public String getInvoke() {
        return "quote";
    }

    public void action(String[] args, GuildMessageReceivedEvent event) {
        if (args.length < 1) {
            event.getChannel().sendMessage("Wrong Usage! Usage: `fakquote [msg_id]`").queue();
            return;
        }

        event.getMessage().delete().queue();

        Message chanMSG = event.getChannel().sendMessage(new EmbedBuilder().setDescription("Searching for message in text channels...").build()).complete();

        List<Message> msg = new ArrayList<>();
        event.getGuild().getTextChannels().forEach(c -> {
            try {
                msg.add(c.retrieveMessageById(args[0]).complete());
            } catch (Exception e) {}
        });

        if (msg.size() < 1) {
            event.getChannel().sendMessage("There is no message in any chat on this guild with the ID `" + args[0] + "`.").queue();
            return;
        }

        chanMSG.editMessage(new EmbedBuilder()
                .setAuthor(msg.get(0).getAuthor().getName(), null, msg.get(0).getAuthor().getAvatarUrl())
                .setDescription(msg.get(0).getContentRaw())
                .setFooter(
                        msg.get(0).getTimeCreated().getDayOfMonth() + ". " +
                                msg.get(0).getTimeCreated().getMonth() + " " +
                                msg.get(0).getTimeCreated().getYear() +
                                " at " + msg.get(0).getTimeCreated().getHour() + ":" +
                                msg.get(0).getTimeCreated().getMinute() + ":" +
                                msg.get(0).getTimeCreated().getSecond() +
                                " in channel #" + msg.get(0).getTextChannel().getName(),
                        null)
                .build()
        ).queue();
    }
}
