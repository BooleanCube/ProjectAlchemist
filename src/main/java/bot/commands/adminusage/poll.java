package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class poll implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!args.isEmpty()) {
            if(!Tools.isAdmin(event.getMember()) && !event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                event.getChannel().sendMessage("You do not have enough perms to use this command!").queue();
                return;
            }
            event.getChannel().sendMessage(new EmbedBuilder().setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl()).setDescription("**" + String.join(" ", args) + "**").build()).queue(msg -> {
                msg.addReaction("✅").queue();
                msg.addReaction("❌").queue();
                msg.addReaction("\uD83E\uDD37").queue();
            });
            event.getMessage().delete().queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Creates a poll\n" +
                "Usage: `a!poll [question]`";
    }

    @Override
    public String getInvoke() {
        return "poll";
    }
}
