package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

import java.util.List;

public class pastebin implements ICommand {

    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("Alchemist")
            .setDefaultExpiry("10m")
            .build();

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        final TextChannel channel = event.getChannel();

        if (args.size() < 1) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        String language = args.get(0);
        String body = "";
        int index = 0;
        String contentRaw = "";
        if(language.toLowerCase().startsWith("lang:")) {
            language = language.substring(5);
            contentRaw = event.getMessage().getContentRaw();
            index = contentRaw.indexOf(language) + language.length();
            body = contentRaw.substring(index).trim();
        } else {
            language = "text";
            body = event.getMessage().getContentRaw().substring(11).trim();
        }
        client.createPaste(language, body).async(
                (id) -> client.getPaste(id).async((paste) -> {
                    EmbedBuilder builder = new EmbedBuilder()
                            .setTitle("Paste " + id, paste.getPasteUrl())
                            .setDescription("```")
                            .appendDescription(paste.getLanguage().getId())
                            .appendDescription("\n")
                            .appendDescription(paste.getBody())
                            .appendDescription("```");

                    channel.sendMessage(builder.build()).queue();
                })
        );
    }

    @Override
    public String getHelp() {
        return "Creates a pastebin file of inputted text. Note: The pastebin created expires in 10 minutes!\n" +
                "Usage: `a!pastebin [\"lang:\"*coding_language*(optional)] [text]`";
    }

    @Override
    public String getInvoke() {
        return "pastebin";
    }
}