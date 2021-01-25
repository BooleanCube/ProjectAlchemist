package bot.commands.adminusage;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class nuke implements ICommand {

    public static long l;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
        if(args.size() == 0 && event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            String msg = event.getMessage().getContentRaw();
            if(System.currentTimeMillis() - l >= 45000) {
                l = System.currentTimeMillis();
                int random = (int)(Math.random()*100) % 14;
                random+=2;
                List<Message> msgs = event.getChannel().getHistory().retrievePast(random).complete();
                event.getChannel().deleteMessages(msgs).queue();
            } else if(System.currentTimeMillis() - l < 45000) {
                event.getChannel().sendMessage("The nukes are too hot! Let's wait for them to cool down! Approximately **" + (int)(45-Math.round((System.currentTimeMillis()-l)/1000)) + "** seconds until the nukes are ready...").queue();
            }
        } else if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessage("You don't have the permission to use this command!").queue();
            return;
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Clears 2-15 messages at random\n" +
                "Usage: `a!nuke`";
    }

    @Override
    public String getInvoke() {
        return "nuke";
    }
}
