package bot.commands.owner;


import groovy.lang.GroovyShell;
import bot.Constants;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Eval implements ICommand {
    private final GroovyShell engine;
    private final String imports;

    public Eval() {
        this.engine = new GroovyShell();
        this.imports = "import java.io.*\n" +
                "import java.lang.*\n" +
                "import java.util.*\n" +
                "import java.util.concurrent.*\n" +
                "import net.dv8tion.jda.core.*\n" +
                "import net.dv8tion.jda.core.entities.*\n" +
                "import net.dv8tion.jda.core.entities.impl.*\n" +
                "import net.dv8tion.jda.core.managers.*\n" +
                "import net.dv8tion.jda.core.managers.impl.*\n" +
                "import net.dv8tion.jda.core.utils.*\n";
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != Constants.OWNER && event.getAuthor().getIdLong() != 247492668131770369l) {
            return;
        }

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();

            return;
        }

        try {
            engine.setProperty("args", args);
            engine.setProperty("event", event);
            engine.setProperty("message", event.getMessage());
            engine.setProperty("channel", event.getChannel());
            engine.setProperty("jda", event.getJDA());
            engine.setProperty("guild", event.getGuild());
            engine.setProperty("member", event.getMember());
            engine.setProperty("selfmember", event.getGuild().getSelfMember());
            for(Member m : event.getGuild().getMembers()) {
                String nick = m.getNickname();
                if(nick == null) {
                    nick = m.getUser().getName();
                }
                engine.setProperty(nick.toLowerCase(), event.getGuild().getMembersByName(m.getUser().getName(), true).get(0));
            }

            String script = imports + event.getMessage().getContentRaw().split("\\s+", 2)[1];
            Object out = engine.evaluate(script);

            event.getChannel().sendMessage(out == null ? "Executed without error" : out.toString()).queue();
        }
        catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Evaluates code and returns whatever has been compiled\n" +
                "Usage: `a!eval [code]`";
    }

    @Override
    public String getInvoke() {
        return "eval";
    }
}
