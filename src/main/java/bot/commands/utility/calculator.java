package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.mariuszgromada.math.mxparser.Expression;
import java.util.HashSet;
import java.util.List;

public class calculator implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() >= 1) {
            HashSet<String> operations = new HashSet<>();
            String expression = getRidOfSpaces(String.join(" ", args));
            Expression e = new Expression(expression);
            event.getChannel().sendMessage(Double.toString(e.calculate()).endsWith(".0") ? Integer.toString((int)(e.calculate())) : Double.toString(e.calculate())).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Solves math expressions! For more help got to this link --> **https://pastebin.com/KnNU4q63**\n" +
                "Command Usage: `a!calc [expression]`\n";
    }

    @Override
    public String getInvoke() {
        return "calc";
    }

    public static String getRidOfSpaces(String str) {
        String nstr = "";
        for(char c : str.toCharArray()) {
            if(c != ' ') {
                nstr += c;
            }
        }
        return nstr;
    }
}
