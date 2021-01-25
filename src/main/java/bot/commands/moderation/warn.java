package bot.commands.moderation;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class warn implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.isEmpty() || event.getMessage().getMentionedMembers().isEmpty()) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            } else {
                Member warned = event.getMessage().getMentionedMembers().get(0);
                Tools.addInfraction(warned);
                EmbedBuilder e = new EmbedBuilder();
                e.setAuthor(warned.getUser().getName(), warned.getUser().getAvatarUrl());
                e.setTitle("[WARN] " + warned.getUser().getName());
                e.addField("Moderated by:", event.getMember().getUser().getName(), true);
                e.addField("Warned User:", warned.getUser().getName(), true);
                if(args.size() > 1) {
                    String[] bruh = event.getMessage().getContentRaw().split(" ", 3);
                    e.addField("Reason:", bruh[2], true);
                } else {
                    e.addField("Reason:", "unspecified", true);
                }
                e.setFooter(warned.getUser().getName() + " has " + Tools.getInfractions(warned) + " infractions", warned.getUser().getAvatarUrl());
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("You don't have enough perms for this command!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Warns a user! Note: You must ping the user you are warning\n" +
                "Usage: `a!warn [user] [reason]`";
    }

    @Override
    public String getInvoke() {
        return "warn";
    }
}
