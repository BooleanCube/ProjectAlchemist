package bot.commands;

import bot.CommandManager;
import bot.Constants;
import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Help implements ICommand {

    private final CommandManager manager;

    public Help(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty()) {
            generateAndSendEmbed(event);
            return;
        }
        EmbedBuilder e = new EmbedBuilder();
        boolean bruh = false;
        String joined = (String.join("", args));
        if(joined.equalsIgnoreCase("fun")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Fun Commands:**");
            e.setDescription("`chucknorris`\n" +
                    "`covid19`\n" +
                    "`dice`\n" +
                    "`instagram`\n" +
                    "`meme`\n" +
                    "`8ball`\n" +
                    "`joke`\n" +
                    "`slap`\n" +
                    "`embarrass`\n" +
                    "`uselessweb`\n" +
                    "`gif`\n" +
                    "`rps`\n" +
                    "`table`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(joined.equalsIgnoreCase("moderation")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Moderation Commands:**");
            e.setDescription("`mute`\n" +
                    "`tempmute`\n" +
                    "`unmute`\n" +
                    "`kick`\n" +
                    "`ban`\n" +
                    "`unban`\n" +
                    "`warn`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(joined.equalsIgnoreCase("logs")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Log Information:**");
            e.setDescription("Alchemist has logging commands! To receive the logs you must create a channel named `#logs` where every log will be stored by me. If the logs are not working please delete the manually created log channel and use the `a!createlogchannel` command! To get rid of logs just delete the channel where logs are found");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
            event.getChannel().sendMessage(e.build()).queue();
            return;
        }
        if(joined.equalsIgnoreCase("animals")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Animal Commands:**");
            e.setDescription("`dog`\n" +
                    "`cat`\n" +
                    "`panda`\n" +
                    "`fox`\n" +
                    "`bird`\n" +
                    "`dogfact`\n" +
                    "`catfact`\n" +
                    "`pandafact`\n" +
                    "`foxfact`\n" +
                    "`birdfact`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(joined.equalsIgnoreCase("currency")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Currency Commands:**");
            e.setDescription("`bal`\n" +
                    "`leaderboard`\n" +
                    "`work`\n" +
                    "`beg`\n" +
                    "`steal`\n" +
                    "`buy`\n" +
                    "`deposit`\n" +
                    "`withdraw`\n" +
                    "`gamble`\n" +
                    "`give`\n" +
                    "`store`\n" +
                    "`coinflip`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(joined.equalsIgnoreCase("admin") && Tools.isAdmin(event.getMember())) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Admin Commands:**");
            e.addField("Note:", "All admin must have **MANAGE_SERVER** permission to be able to use this command!\n", false);
            e.setDescription("`giveaway`\n" +
                    "`reroll`\n" +
                    "`poll`\n" +
                    "`dmall`\n" +
                    "`createserverstats`\n" +
                    "`updateserverstats`\n" +
                    "`nuke`\n" +
                    "`purge`\n" +
                    "`clearwebhooks`\n" +
                    "`createlogchannel`\n" +
                    "`createcategory`\n" +
                    "`deletecategory`\n" +
                    "`createchannel`\n" +
                    "`deletechannel`\n" +
                    "`createrole`\n" +
                    "`deleterole`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(joined.equalsIgnoreCase("utility")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Utility Commands:**");
            e.setDescription("`listallcommands`\n" +
                    "`ping`\n" +
                    "`quote`\n" +
                    "`calc`\n" +
                    "`createinvite`\n" +
                    "`serverinfo`\n" +
                    "`userinfo`\n" +
                    "`pastebin`\n" +
                    "`choose`\n" +
                    "`uptime`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }if(joined.equalsIgnoreCase("advertisements")) {
            bruh = true;
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("**Advertisement Commands:**");
            e.setDescription("`mydevelopers`\n" +
                    "`myserver`\n" +
                    "`vote`\n" +
                    "`donate`\n");
            e.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        }
        if(bruh && !joined.equalsIgnoreCase("logs")) {
            e.getDescriptionBuilder().append("\n" +
                    "\nFor **EVEN MORE** help use: `a!help [command]`\n");
            event.getChannel().sendMessage(e.build()).queue();
            return;
        }
        ICommand command = manager.getCommand(joined);
        if (command == null) {
            event.getChannel().sendMessage("The command `" + joined + "` does not exist\n" + "Use: `" + Constants.PREFIX + getInvoke() + "` for a list of commands.").queue();
            return;
        }
        String message = "Command help for `" + command.getInvoke() + "`:\n" + command.getHelp();
        event.getChannel().sendMessage(message).queue();
    }

    private void generateAndSendEmbed(GuildMessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Alchemist Command List:");
        Random ran = new Random();
        float r = ran.nextFloat();
        float b = ran.nextFloat();
        float g = ran.nextFloat();
        embedBuilder.setColor(new Color(r, g, b));
        embedBuilder.setTitle("**Alchemist Command List:**");
        embedBuilder.setDescription("`Fun`\n`Animals`\n`Currency`\n`Moderation`\n`Admin`\n`Utility`\n`Logs`\n`Advertisements`\n\nFor more help use: `a!help [category]`\n");
        embedBuilder.setFooter("{ProjectAlchemist#4690}, {Giunk#4759}", "https://cdn.discordapp.com/attachments/683877665295695883/683877696064716837/apfpf.jpg");
        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows a list of all the commands.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [command]`";
    }

    @Override
    public String getInvoke() {
        return "help";
    }

}