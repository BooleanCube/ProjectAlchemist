package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class userinfo implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            Member m = event.getMember();
            EmbedBuilder e = new EmbedBuilder();
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
            String join = Date.from(Instant.from(m.getTimeJoined())).toString();
            String register = Date.from(Instant.from(m.getUser().getTimeCreated())).toString();
            String url = m.getUser().getAvatarUrl();
            String roles = "";
            for(Role role : m.getRoles()) {
                roles += "`" + role.getName() + "`, ";
            }
            if(!roles.isEmpty()) {
                roles = roles.substring(0, roles.length()-2);
            }
            //Joined, Registered, Image, Roles
            e.setImage(url);
            e.addField("Join date: ", join, true);
            e.addField("Registration date: ", register, true);
            if(roles.length() > 500) {
                e.addField("Roles: ", "Too many roles!", true);
                event.getChannel().sendMessage(e.build()).queue();
            } else {
                e.addField("Roles: ", roles, true);
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else if(args.size() == 1) {
            Member m = event.getMessage().getMentionedMembers().get(0);
            EmbedBuilder e = new EmbedBuilder();
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setAuthor(m.getUser().getName(), m.getUser().getAvatarUrl());
            String join = Date.from(Instant.from(m.getTimeJoined())).toString();
            String register = Date.from(Instant.from(m.getUser().getTimeCreated())).toString();
            String url = m.getUser().getAvatarUrl();
            String roles = "";
            for(Role role : m.getRoles()) {
                roles += "`" + role.getName() + "`, ";
            }
            roles = roles.substring(0, roles.length()-2);
            //Joined, Registered, Image, Roles
            e.setImage(url);
            e.addField("Join date: ", join, true);
            e.addField("Registration date: ", register, true);
            if(roles.length() > 500) {
                e.addField("Roles: ", "Too many roles!", true);
                event.getChannel().sendMessage(e.build()).queue();
            } else {
                e.addField("Roles: ", roles, true);
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows information about a user. **Note:** If command is used without a user ping the command will show information about you.\n" +
                "Usage: `a!userinfo [user(optional)]`";
    }

    @Override
    public String getInvoke() {
        return "userinfo";
    }
}
