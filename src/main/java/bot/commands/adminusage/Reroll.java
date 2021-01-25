package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.LinkedList;
import java.util.List;

public class Reroll implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
        //            return;
        if(args.size() == 1) {
            if(!Tools.isAdmin(event.getMember()))
            {
                event.getChannel().sendMessage("You must have admin perms to use this!").queue();
                return;
            }
            String id = event.getMessage().getContentRaw().substring(9).trim();
            if(!id.matches("\\d{17,22}"))
            {
                event.getChannel().sendMessage("Invalid message id").queue();
                return;
            }
            Message m = null;
            try {
                m = event.getChannel().retrieveMessageById(id).complete();
            } catch(Exception e) {
                event.getChannel().sendMessage("Invalid message id").queue();
                return;
            }
            if(m==null)
            {
                event.getChannel().sendMessage("Message not found!").queue();
                return;
            }
            m.getReactions()
                    .stream().filter(mr -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                    .findAny().ifPresent(mr -> {
                List<User> users = new LinkedList<User>(mr.retrieveUsers().complete());
                users.remove(event.getJDA().getSelfUser());
                String uid = users.get((int)(Math.random()*users.size())).getId();
                event.getChannel().sendMessage("Congratulations to <@"+uid+">! You won the reroll!").queue();
            });
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Rerolls for a new winner. Remember to provide giveaway message after command usage\n" +
                "Usage: `a!reroll [msg_id]`";
    }

    @Override
    public String getInvoke() {
        return "reroll";
    }
}
