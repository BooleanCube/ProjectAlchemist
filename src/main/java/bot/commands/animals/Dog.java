package bot.commands.animals;

import bot.Constants;
import bot.objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Dog implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            WebUtils.ins.getJSONObject("https://dog.ceo/api/breeds/image/random").async( (json) -> {
                String url = json.get("message").asText();
                EmbedBuilder e = new EmbedBuilder();
                e.setImage(url);
                event.getChannel().sendMessage(e.build()).queue();
            });
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return ("Shows you a random dog picture\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`");
    }

    @Override
    public String getInvoke() {
        return "dog";
    }

}