package bot.commands.XPSystem;

import bot.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class XPLogging extends ListenerAdapter {

    public static HashMap<String, Long> timer = new HashMap<>();

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        try {
            if(!Tools.XPon(event.getGuild().getIdLong())) {
                return;
            }
        } catch (IOException e) {
            
        }
        if(event.getMessage().isWebhookMessage()) {
            return;
        }
        if(event.getMember().getUser().isBot()) {
            return;
        }
        long userId = event.getAuthor().getIdLong();
        long serverId = event.getGuild().getIdLong();
        long currentTime = System.currentTimeMillis();
        long prevLevel = 0;
        try {
            prevLevel = Tools.TotalXPToLevel(Tools.getXP(userId, serverId));
        } catch (IOException e) {}
        long currentLevel = 0;
        try {
            currentLevel = Tools.TotalXPToLevel(Tools.getXP(userId, serverId));
        } catch (IOException e) {}
        boolean autoRole = false;
        try {
            autoRole = Tools.autoRoleEnabled(event.getGuild());
        } catch (IOException e) {}
        if(autoRole) {
            String[] levels = {"bronze", "silver", "gold", "platinum", "diamond"};
            if(!levelToRoleName(currentLevel).equals("")) {
                String roleName = levelToRoleName(currentLevel);
                if(event.getGuild().getRolesByName(roleName, true).isEmpty()) {
                    createLevelRoles(event.getGuild());
                }
                if(!event.getMember().getRoles().contains(event.getGuild().getRolesByName(roleName, true).get(0))) {
                    event.getGuild().addRoleToMember(userId, event.getGuild().getRolesByName(roleName, true).get(0)).queue();
                    if(!levels[arrayIndexOf(levels, roleName)-1].equals("")) {
                        try {
                            for(int i=0; i<arrayIndexOf(levels, roleName); i++) {
                                event.getGuild().removeRoleFromMember(userId, event.getGuild().getRolesByName(levels[i], true).get(0)).queue();
                            }
                            for(int i=arrayIndexOf(levels, roleName)+1; i<levels.length; i++) {
                                event.getGuild().removeRoleFromMember(userId, event.getGuild().getRolesByName(levels[i], true).get(0)).queue();
                            }
                        } catch(Exception e){}
                    }
                }
            }
        }
        if(timer.containsKey(userId + " " + serverId)) {
            if(currentTime - timer.get(userId + " " + serverId) > 60000) {
                timer.remove(userId + " " + serverId);
                timer.put(userId + " " + serverId, currentTime);
                try {
                    Tools.addXP(userId, serverId, randomXP());
                } catch (IOException e) {
                    
                }
                if(prevLevel!=currentLevel) {
                    if(Tools.DMLevelNotifs(event.getGuild(), event.getMember())) {
                        long finalPrevLevel = prevLevel;
                        long finalCurrentLevel = currentLevel;
                        long totalXP = 0;
                        try {
                            totalXP = Tools.getXP(event.getAuthor().getIdLong(), event.getGuild().getIdLong());
                        } catch (IOException e) {

                        }
                        long finalTotalXP = totalXP;
                        event.getMember().getUser().openPrivateChannel().queue(channel -> {
                            channel.sendMessage(new EmbedBuilder()
                                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                                    .setTitle(event.getGuild().getName() + " level notification!")
                                    .addField("Previous Level:", Long.toString(finalPrevLevel), true)
                                    .addField("Current Level:", Long.toString(finalCurrentLevel), true)
                                    .addField("XP:", (finalTotalXP -Tools.levelToXP(finalPrevLevel)) + "/" + Tools.levelToXP(finalCurrentLevel) + " (total: " + finalTotalXP + ")", true)
                                    .setFooter("You can disable level notifications by using `a!togglelevelnotifs` in a server")
                                    .build()
                            ).queue();
                        });
                    }
                }
            }
        } else {
            try {
                Tools.addXP(userId, serverId, randomXP());
            } catch (IOException e) {
                
            }
            timer.put(userId + " " + serverId, currentTime);
        }
    }

    public int randomXP() {
        return (int)(Math.random()*10)+16;
    }

    public String levelToRoleName(long level) {
        if(level >= 80) {
            return "Diamond";
        } else if(level >= 40) {
            return "Platinum";
        } else if(level >= 20) {
            return "Gold";
        } else if(level >= 10) {
            return "Silver";
        } else if(level >= 5) {
            return "Bronze";
        }
        return "";
    }

    public void createLevelRoles(Guild g) {
        g.createRole().setColor(Color.LIGHT_GRAY).setName("Diamond").queue();
        g.createRole().setColor(Color.DARK_GRAY).setName("Platinum").queue();
        g.createRole().setColor(Color.YELLOW).setName("Gold").queue();
        g.createRole().setColor(Color.GRAY).setName("Silver").queue();
        g.createRole().setColor(new Color(102, 51, 0)).setName("Bronze").queue();
    }

    public int arrayIndexOf(String[] levels, String str) {
        for(int i=0; i<levels.length; i++) {
            if(levels[i].equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }
}