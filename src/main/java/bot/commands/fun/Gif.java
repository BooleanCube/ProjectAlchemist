package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.brunocvcunha.jiphy.Jiphy;
import org.brunocvcunha.jiphy.JiphyConstants;
import org.brunocvcunha.jiphy.requests.JiphySearchRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Gif implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String[] arguments = null;
        try {
            arguments = args.toArray(new String[0]);
        } catch(Exception e) {
            //nothing
        }
        try {
            if(!args.isEmpty()) {
                action(arguments, event);
            } else {
                action(new String[]{}, event);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Searches for a gif with that search query\n" +
                "Usage: `a!gif [search_query]`";
    }

    @Override
    public String getInvoke() {
        return "gif";
    }

    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        TextChannel tc = event.getChannel();
        User author = event.getAuthor();
        if (args.length < 1) {
            tc.sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
        String query = Arrays.stream(args).filter(s -> !s.startsWith("-")).collect(Collectors.joining("-"));
        int index = args[args.length - 1].startsWith("-") ? Integer.parseInt(args[args.length - 1].substring(1)) - 1 : 0;
        Message msg = event.getChannel().sendMessage("Collecting data...").complete();
        event.getMessage().delete().queue();
        Jiphy jiphy = Jiphy.builder()
                .apiKey(JiphyConstants.API_KEY_BETA)
                .build();
        ArrayList<String> gifs = new ArrayList<>();
        jiphy.sendRequest(new JiphySearchRequest(query)).getData().forEach(g ->
                gifs.add(g.getUrl())
        );
        if (gifs.size() == 0) {
            msg.delete().queue();
            tc.sendMessage("No gifs found with the search query `" + query + "`!").queue(m ->
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            m.delete().queue();
                        }
                    }, 4000)
            );
            return;
        }
        else if (gifs.size() < index)
            index = gifs.size() - 1;
        msg.editMessage(
                String.format("[%s]\n", author.getName()) +
                        gifs.get(index)
        ).queue();
    }
}
