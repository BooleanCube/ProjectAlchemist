package bot;
import bot.commands.*;
import bot.commands.adminusage.*;
import bot.commands.animals.*;
import bot.commands.currency.*;
import bot.commands.extra.developers;
import bot.commands.extra.donate;
import bot.commands.extra.server;
import bot.commands.extra.support;
import bot.commands.fun.*;
import bot.commands.owner.Eval;
import bot.commands.owner.addtoinv;
import bot.commands.utility.*;
import bot.commands.moderation.*;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new Ping());
        addCommand(new Dog());
        addCommand(new Cat());
        addCommand(new Kick());
        addCommand(new Meme());
        addCommand(new ban());
        addCommand(new unban());
        addCommand(new addtoinv());
        addCommand(new coinflip());
        addCommand(new fight());
        addCommand(new inventory());
        addCommand(new nuke());
        addCommand(new purge());
        addCommand(new Quote());
        addCommand(new Giveaway());
        addCommand(new Reroll());
        addCommand(new catfacts());
        addCommand(new gay());
        addCommand(new sell());
        addCommand(new use());
        addCommand(new mute());
        addCommand(new tempmute());
        addCommand(new unmute());
        addCommand(new Bird());
        addCommand(new server());
        addCommand(new Instagram());
        addCommand(new panda());
        addCommand(new support());
        addCommand(new birdfact());
        addCommand(new Gif());
        addCommand(new deletechannel());
        addCommand(new deleterole());
        addCommand(new createchannel());
        addCommand(new createrole());
        addCommand(new deletecategory());
        addCommand(new createcategory());
        addCommand(new covid19());
        addCommand(new poll());
        addCommand(new dmall());
        addCommand(new Dice());
        addCommand(new Eval());
        addCommand(new foxfact());
        addCommand(new joke());
        addCommand(new donate());
        addCommand(new chucknorris());
        addCommand(new deposit());
        addCommand(new work());
        addCommand(new withdraw());
        addCommand(new give());
        addCommand(new leaderboard());
        addCommand(new dogfact());
        addCommand(new table());
        addCommand(new balance());
        addCommand(new eightball());
        addCommand(new uselessweb());
        addCommand(new beg());
        addCommand(new store());
        addCommand(new calculator());
        addCommand(new buy());
        addCommand(new invite());
        addCommand(new gamble());
        addCommand(new serverinfo());
        addCommand(new search());
        addCommand(new userinfo());
        addCommand(new statcreate());
        addCommand(new developers());
        addCommand(new slap());
        addCommand(new rockpaperscissors());
        addCommand(new pandafact());
        addCommand(new fox());
        addCommand(new listallcommands(this));
        addCommand(new Help(this));
        addCommand(new choose());
        addCommand(new statupdate());
        addCommand(new warn());
        addCommand(new createlogchannel());
        addCommand(new pastebin());
        addCommand(new uptime());
        addCommand(new steal());
        addCommand(new embarrass());
        addCommand(new clearwebhooks());
    }

    private void addCommand(ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);
            commands.get(invoke).handle(args, event);
        }
    }
}