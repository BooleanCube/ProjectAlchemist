package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.List;

public class choose implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            event.getChannel().sendMessage("Please give me some arguments to choose from!").queue();
            return;
        } else {
            if(!event.getMessage().getMentionedMembers().isEmpty() || event.getMessage().mentionsEveryone() || !event.getMessage().getMentionedRoles().isEmpty()) {
                event.getChannel().sendMessage("Please do not ping members, servers or roles in the choose utility!").queue();
                return;
            }
            int random = (int)(Math.random()*args.size() );
            event.getChannel().sendMessage(args.get(random)).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Chooses 1 from the input arguments\n" +
                "Usage: `a!choose [arg1] [arg2] [arg3]...`";
    }

    @Override
    public String getInvoke() {
        return "choose";
    }
}
