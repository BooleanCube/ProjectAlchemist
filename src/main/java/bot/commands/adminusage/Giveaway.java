package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.LinkedList;
import java.util.List;

public class Giveaway implements ICommand {

    public static int amount = 0;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() >= 2) {
            if(!Tools.isAdmin(event.getMember()))
            {
                event.getChannel().sendMessage("You must have admin perms to use this!").queue();
                return;
            }
            String str = event.getMessage().getContentRaw().substring(11).trim();
            String[] parts = str.split("\\s+",2);
            try{
                int sec = Integer.parseInt(parts[0]);
                event.getChannel().sendMessage(":partying_face:  **GIVEAWAY!**  :partying_face:\n"+(parts.length>1 ? "\u25AB*`"+parts[1]+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!").queue(m -> {
                    m.addReaction("\uD83C\uDF89").queue();
                    new Giveaway2(sec,m,parts.length>1 ? parts[1] : null).start();
                });
                event.getMessage().delete().queue();
            } catch(NumberFormatException ex)
            {
                event.getChannel().sendMessage("Could not parse seconds from `"+parts[0]+"`").queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Starts a giveaway for *prize* that lasts *seconds* long\n" +
                "Usage: `a!giveaway [seconds] [prize]`";
    }

    @Override
    public String getInvoke() {
        return "giveaway";
    }
}
class Giveaway2 {

    int seconds;
    Message message;
    String item;
    public Giveaway2(int time, Message message, String item)
    {
        seconds = time;
        this.message = message;
        this.item = item;
    }

    public void start()
    {
        new Thread(){
            @Override
            public void run() {
                while(seconds>5)
                {
                    message.editMessage(":partying_face: **GIVEAWAY!**  :partying_face:\n"+(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                    seconds-=5;
                    try{Thread.sleep(5000);}catch(Exception e){}
                }
                while(seconds>0)
                {
                    message.editMessage(":partying_face: **G I V E A W A Y!** :partying_face:\nLAST CHANCE TO ENTER!!!\n"+(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                    seconds--;
                    try{Thread.sleep(1000);}catch(Exception e){}
                }
                message.getChannel().retrieveMessageById(message.getId()).complete().getReactions()
                        .stream().filter((mr) -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                        .findAny().ifPresent(mr -> {
                    List<User> users = new LinkedList<>(mr.retrieveUsers().complete());
                    users.remove(message.getJDA().getSelfUser());
                    String id = users.get((int)(Math.random()*users.size())).getId();
                    message.editMessage(":partying_face: **GIVEAWAY ENDED!** :partying_face:\n"+(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"\nWinner: <@"+id+"> \uD83C\uDF89").queue();
                    message.getChannel().sendMessage("Congratulations to <@"+id+">! You won"+(item==null ? "" : " **"+item + "**")+"!").queue();
                });
            }
        }.start();
    }
    public static String secondsToTime(long timeseconds)
    {
        StringBuilder builder = new StringBuilder();
        int years = (int)(timeseconds / (60*60*24*365));
        if(years>0)
        {
            builder.append("**").append(years).append("** years, ");
            timeseconds = timeseconds % (60*60*24*365);
        }
        int weeks = (int)(timeseconds / (60*60*24*365));
        if(weeks>0)
        {
            builder.append("**").append(weeks).append("** weeks, ");
            timeseconds = timeseconds % (60*60*24*7);
        }
        int days = (int)(timeseconds / (60*60*24));
        if(days>0)
        {
            builder.append("**").append(days).append("** days, ");
            timeseconds = timeseconds % (60*60*24);
        }
        int hours = (int)(timeseconds / (60*60));
        if(hours>0)
        {
            builder.append("**").append(hours).append("** hours, ");
            timeseconds = timeseconds % (60*60);
        }
        int minutes = (int)(timeseconds / (60));
        if(minutes>0)
        {
            builder.append("**").append(minutes).append("** minutes, ");
            timeseconds = timeseconds % (60);
        }
        if(timeseconds>0)
            builder.append("**").append(timeseconds).append("** seconds");
        String str = builder.toString();
        if(str.endsWith(", "))
            str = str.substring(0,str.length()-2);
        if(str.equals(""))
            str="**No time**";
        return str;
    }

}