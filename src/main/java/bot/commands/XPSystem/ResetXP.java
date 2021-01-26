package bot.commands.XPSystem;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.*;
import java.util.List;

public class ResetXP implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            if(args.isEmpty()) {
                File f = new File("/home/samarth/Desktop/alchemist/XP/" + event.getGuild().getId() + ".txt");
                f.delete();
                event.getChannel().sendMessage("Successfully reset XP for this server!").queue();
            }
            else if(args.size() == 1) {
                Member m = event.getMessage().getMentionedMembers().get(0);
                File f = new File("/home/samarth/Desktop/alchemist/XP/" + event.getGuild().getId() + ".txt");
                boolean found = false;
                BufferedReader bf = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = bf.readLine()) != null) {
                    if(line.startsWith(event.getMember().getId())) {
                        found = true;
                    } else sb.append(line + "\n");
                }
                bf.close();
                FileWriter fw = new FileWriter(f);
                fw.write(sb.toString());
                fw.flush();
                fw.close();
                if(!found) {
                    event.getChannel().sendMessage("This member was not found in our database!").queue();
                } else {
                    event.getChannel().sendMessage("Successfully reset " + m.getUser().getName() + "'s XP!").queue();
                }
            }
            else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            }
        } else {
            event.getChannel().sendMessage("You do not have the permission to use this command!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Resets XP for the server this command is used in. To use this command the user needs to have MANAGE_SERVER permission\nUsage: `a!resetxp`";
    }

    @Override
    public String getInvoke() {
        return "resetxp";
    }
}