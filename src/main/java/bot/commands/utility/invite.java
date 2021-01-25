package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class invite implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            if(event.getGuild().getSelfMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
                Invite i = event.getChannel().createInvite().complete();
                EmbedBuilder e = new EmbedBuilder();
                e.addField("Invite link:", i.getUrl(), true);
                e.addField("Code:", i.getCode(), true);
                e.setAuthor(event.getGuild().getName(), event.getGuild().getSplashUrl(), event.getGuild().getIconUrl());
                event.getChannel().sendMessage(e.build()).queue();
            } else {
                event.getChannel().sendMessage("I don't have the CREATE_INVITE permission!").queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Creates an invite for the server. Note: The invites created expire in 1 day!\n" +
                "Usage: `a!createinvite`";
    }

    @Override
    public String getInvoke() {
        return "createinvite";
    }
}
