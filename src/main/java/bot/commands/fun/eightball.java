package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class eightball implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String[] msg = {"Definitely!", "Sure!", "I think so...", "Maybe?", "I am not so sure about that one", "Probably", "Probably not!", "Absolutely not!", "!false", "No, of course so not", "Oh please! We all know that's true!", "It seems possible!"};
        int random = (int)(Math.random()*100000)%msg.length;
        event.getChannel().sendMessage(msg[random]).queue();
    }

    @Override
    public String getHelp() {
        return "Check your luck!\n" +
                "Usage: `a!8ball [question/statement]`";
    }

    @Override
    public String getInvoke() {
        return "8ball";
    }
}