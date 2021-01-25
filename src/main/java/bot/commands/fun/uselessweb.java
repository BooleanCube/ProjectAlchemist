package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class uselessweb implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            String[] uselesswebsites = {"http://unicodesnowmanforyou.com/", "http://spaceis.cool/", "http://www.rrrgggbbb.com/", "http://weirdorconfusing.com/", "http://ihasabucket.com/", "http://www.yesnoif.com/", "http://eelslap.com/", "http://corndogoncorndog.com/", "http://www.electricboogiewoogie.com/", "http://www.sanger.dk/", "http://www.republiquedesmangues.fr/", "http://randomcolour.com/", "\n" +
                    "http://www.theendofreason.com/", "http://whitetrash.nl/", "http://hasthelargehadroncolliderdestroyedtheworldyet.com/", "http://pixelsfighting.com/", "http://www.leduchamp.com/",
                    "http://chihuahuaspin.com/", "http://www.crossdivisions.com/", "http://burnie.com/", "http://beesbeesbees.com/", "http://www.electricboogiewoogie.com/", "http://heeeeeeeey.com/",
                    "http://www.republiquedesmangues.fr/", "http://www.everydayim.com/", "http://dogs.are.the.most.moe/", "http://www.trashloop.com/", "http://randomcolour.com/", "https://pointerpointer.com/", "http://www.movenowthinklater.com/," +
                    "http://iloveyoulikeafatladylovesapples.com/", "http://ninjaflex.com/", "http://semanticresponsiveillustration.com/", "http://cant-not-tweet-this.com/", "http://metaphorsofinfinity.com/",
                    "http://www.sadforjapan.com/", "http://www.ismycomputeron.com/", "http://burymewithmymoney.com/", "http://www.omfgdogs.com/", "http://www.fallingfalling.com/"};
            int random = (int)(Math.random()*10000)%uselesswebsites.length;
            EmbedBuilder e = new EmbedBuilder();
            Random ran = new Random();
            float r = ran.nextFloat();
            float b = ran.nextFloat();
            float g = ran.nextFloat();
            e.setColor(new Color(r, g, b));
            e.setTitle("Useless Website Link:");
            e.setDescription(uselesswebsites[random]);
            event.getChannel().sendMessage(e.build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Gives a link to a useless website\n" +
                "Usage: `a!uselessweb`";
    }

    @Override
    public String getInvoke() {
        return "uselessweb";
    }
}
