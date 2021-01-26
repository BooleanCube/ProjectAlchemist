package bot.commands.XPSystem;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class toggleautorole implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.isEmpty()) {
            if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage("You do not have the permission to use this command!").queue();
                return;
            }
            if(!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                event.getChannel().sendMessage("I do not have the permission to Manage Roles in this server!").queue();
                return;
            }
            Tools.toggleAutoRole(event.getGuild());
            event.getChannel().sendMessage(Tools.autoRoleEnabled(event.getGuild()) ? "Auto Level Role System has been toggled **ON**!" : "Auto Level Role System has been toggled **OFF**!").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Toggles auto level role system on and off!\nUsage: `a!toggleautolvlrole`";
    }

    @Override
    public String getInvoke() {
        return "toggleautolvlrole";
    }
}
