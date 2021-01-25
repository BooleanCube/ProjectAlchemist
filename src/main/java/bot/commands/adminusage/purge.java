package bot.commands.adminusage;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class purge implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1 && event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            event.getMessage().delete().complete();
            int num = 0;
            try {
                num = Integer.parseInt(args.get(0));
            } catch (Exception e) {
                event.getChannel().sendMessage("Wrong Usage of command!\n" + getHelp()).queue();
                return;
            }
            int bruh = num/100;
            if(bruh == 0) {
                List<Message> msg = event.getChannel().getHistory().retrievePast(num).complete();
                event.getChannel().purgeMessages(msg);
                return;
            }
            try {
                for(int i=0; i<=bruh; i++) {
                    if(i == num) {
                        List<Message> msg = event.getChannel().getHistory().retrievePast(num).complete();
                        event.getChannel().purgeMessages(msg);
                    } else {
                        List<Message> msg = event.getChannel().getHistory().retrievePast(100).complete();
                        event.getChannel().purgeMessages(msg);
                        num -= 100;
                    }
                }
            } catch(Exception e) {
                event.getChannel().sendMessage("I came across an error! I deleted as many messages I could!").queue();
                return;
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
        return "Deletes *amount* messages from that channel (*not including the command*)\n" +
                "Usage: `a!purge [amount]`";
    }

    @Override
    public String getInvoke() {
        return "purge";
    }
}
