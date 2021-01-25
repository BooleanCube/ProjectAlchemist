package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class rockpaperscissors implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            String choice = args.get(0);
            int num = 0;
            String[] mychoices = {"rock", "paper", "scissors"};
            if(!choice.equalsIgnoreCase("rock") && !choice.equalsIgnoreCase("paper") && !choice.equalsIgnoreCase("scissors")) {
                event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp() + "\n**READ THE NOTE!!!**").queue();
                return;
            }
            for(String str : mychoices) {
                if(str.equalsIgnoreCase(choice)) {
                    break;
                } else {
                    num++;
                }
            }
            int random = (int)(Math.random()*10)%mychoices.length;
            String myChoice = mychoices[random];
            if(num == random) {
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("Tie!");
                e.setDescription("I chose `" + myChoice + "` and you chose `" + choice + "`! Therefore we tied! Try again!");
                event.getChannel().sendMessage(e.build()).queue();
            } else if(random == 0 && num == 2) {
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("I win!");
                e.setDescription("I chose `" + myChoice + "` and you chose `" + choice + "`! Therefore I win! Try again! GG");
                event.getChannel().sendMessage(e.build()).queue();
            } else if(random == 2 && num == 0) {
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("You win!");
                e.setDescription("I chose `" + myChoice + "` and you chose `" + choice + "`! Therefore you win! Congrats! GG");
                event.getChannel().sendMessage(e.build()).queue();
            } else if(random > num) {
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("I win!");
                e.setDescription("I chose `" + myChoice + "` and you chose `" + choice + "`! Therefore I win! Try again! GG");
                event.getChannel().sendMessage(e.build()).queue();
            } else if(num > random) {
                EmbedBuilder e = new EmbedBuilder();
                Random ran = new Random();
                float r = ran.nextFloat();
                float b = ran.nextFloat();
                float g = ran.nextFloat();
                e.setColor(new Color(r, g, b));
                e.setTitle("You win!");
                e.setDescription("I chose `" + myChoice + "` and you chose `" + choice + "`! Therefore you win! Congrats! GG");
                event.getChannel().sendMessage(e.build()).queue();
            }
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Plays a game of rock, paper and scissors! Note: your choice must always be either rock/paper/scissors\n" +
                "Usage: `a!rps [choice]`";
    }

    @Override
    public String getInvoke() {
        return "rps";
    }
}
