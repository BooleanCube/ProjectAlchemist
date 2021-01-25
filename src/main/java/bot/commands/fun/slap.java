package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class slap implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String[] links = {"https://giphy.com/gifs/gSIz6gGLhguOY", "https://giphy.com/gifs/sweet-penguin-penguins-mEtSQlxqBtWWA", "https://giphy.com/gifs/mary-steenburgen-vxvNnIYFcYqEE", "https://giphy.com/gifs/angry-fight-family-guy-uqSU9IEYEKAbS", "https://giphy.com/gifs/slap-dog-slapping-lX03hULhgCYQ8", "https://giphy.com/gifs/absurdnoise-slapping-slap-uG3lKkAuh53wc"};
        int random = (int)(Math.random()*100) % links.length;
        String bruh = "";
        for(String oof : args) {
            bruh += oof + " ";
        }
        event.getChannel().sendMessage(event.getAuthor().getName() + " slapped " + bruh.trim()).queue();
        event.getChannel().sendMessage(links[random]).queue();
    }

    @Override
    public String getHelp() {
        return "Simulation of slapping a user\n" +
                "Usage: `a!slap [name]`";
    }

    @Override
    public String getInvoke() {
        return "slap";
    }
}
