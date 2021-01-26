package bot.commands.logs;

import bot.Tools;
////import bot.commands.databases.CustomizableDatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;

public class logs extends ListenerAdapter {

    public static boolean logs = true;


    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(!Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSon")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setTitle("A new invite was created in " + event.getGuild().getName());
        e.setDescription("Link: " + event.getInvite().getUrl() + "\nCode: " + event.getInvite().getCode());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildInviteDelete(@Nonnull GuildInviteDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(!Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSon")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle("An invite was deleted/has expired in " + event.getGuild().getName());
        e.setDescription("Link: " + event.getUrl() + "\nCode: " + event.getCode());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }

        try {
            if(!Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSon")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle("Message Delete!");
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("members: ")) {
                    int members = Integer.parseInt(vc.getName().substring(9)) + 1;
                    vc.getManager().setName("Members: " + members).queue();
                }
                if(event.getMember().getUser().isBot() && vc.getName().toLowerCase().startsWith("bots: ")) {
                    int bots = Integer.parseInt(vc.getName().substring(6)) + 1;
                    vc.getManager().setName("Bots: " + bots).queue();
                }
                if(vc.getName().toLowerCase().startsWith("humans: ") && !event.getMember().getUser().isBot()) {
                    int humans = Integer.parseInt(vc.getName().substring(8)) + 1;
                    vc.getManager().setName("Humans: " + humans).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setAuthor(event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
        e.setColor(Color.GREEN);
        e.setTitle(event.getMember().getUser().getName() + " joined " + event.getGuild().getName());
        tc.sendMessage(e.build()).queue();

    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("members: ")) {
                    int members = Integer.parseInt(vc.getName().substring(9)) - 1;
                    vc.getManager().setName("Members: " + members).queue();
                }
                if(event.getMember().getUser().isBot() && vc.getName().toLowerCase().startsWith("bots: ")) {
                    int bots = Integer.parseInt(vc.getName().substring(6)) - 1;
                    vc.getManager().setName("Bots: " + bots).queue();
                }
                if(vc.getName().toLowerCase().startsWith("humans: ") && !event.getMember().getUser().isBot()) {
                    int humans = Integer.parseInt(vc.getName().substring(8)) - 1;
                    vc.getManager().setName("Humans: " + humans).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setAuthor(event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
        e.setTitle(event.getMember().getUser().getName() + " left " + event.getGuild().getName());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("channels: ")) {
                    int channels = Integer.parseInt(vc.getName().substring(10)) + 1;
                    vc.getManager().setName("Channels: " + channels).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setTitle("`#" + event.getChannel().getName() + "` was just created in " + event.getGuild().getName());
        e.setDescription(event.getChannel().getName() + " was created on " + event.getChannel().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("channels: ")) {
                    int channels = Integer.parseInt(vc.getName().substring(10)) - 1;
                    vc.getManager().setName("Channels: " + channels).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle("`#" + event.getChannel().getName() + "` was just deleted in " + event.getGuild().getName());
        e.setDescription(event.getChannel().getName() + " was deleted on " + event.getChannel().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(event.getRole().getColor());
        e.setTitle(event.getRole().getName() + " role was just created in " + event.getGuild().getName());
        e.setDescription(event.getRole().getName() + " was created on " + event.getRole().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(event.getRole().getColor());
        e.setTitle(event.getRole().getName() + " role was just deleted in " + event.getGuild().getName());
        e.setDescription(event.getRole().getName() + " was deleted on " + event.getRole().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onCategoryCreate(CategoryCreateEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("categories: ")) {
                    int categories = Integer.parseInt(vc.getName().substring(12)) + 1;
                    vc.getManager().setName("Categories: " + categories).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setTitle(event.getCategory().getName() + " category was just created in " + event.getGuild().getName());
        e.setDescription(event.getCategory().getName() + " was created on " + event.getCategory().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onCategoryDelete(CategoryDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("categories: ")) {
                    int cats = Integer.parseInt(vc.getName().substring(12)) - 1;
                    vc.getManager().setName("Categories: " + cats).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle(event.getCategory().getName() + " category was just deleted in " + event.getGuild().getName());
        e.setDescription(event.getCategory().getName() + " was deleted on " + event.getCategory().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onEmoteAdded(EmoteAddedEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setTitle("`" + event.getEmote().getName() + "` emote was added into " + event.getGuild().getName());
        e.setDescription(event.getEmote().getName() + " was created!");
        e.setImage(event.getEmote().getImageUrl());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onEmoteRemoved(EmoteRemovedEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle("`" + event.getEmote().getName() + "` emote was removed from " + event.getGuild().getName());
        e.setDescription(event.getEmote().getName() + " was removed!");
        e.setImage(event.getEmote().getImageUrl());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        User target = event.getUser();
        EmbedBuilder e = new EmbedBuilder()
                .setTitle("[UNBAN] " + target.getName())
                .addField("Unbanned Member:", target.getName(), true)
                .setAuthor(target.getName(), target.getAvatarUrl(), target.getEffectiveAvatarUrl());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.YELLOW);
        String name = event.getNewNickname() == null ? event.getUser().getName() : event.getNewNickname();
        e.setAuthor(name, event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
        e.setTitle(event.getUser().getName() + "'s nickname changed!");
        e.setDescription(event.getUser().getName() + " changed his nickname from `" + event.getOldNickname() + "` to `" + event.getNewNickname() + "`");
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setAuthor(event.getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
        e.setTitle(event.getUser().getName() + " gained the `" + event.getRoles().get(0).getName() + "` role in " + event.getGuild().getName());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setAuthor(event.getUser().getName(), event.getMember().getUser().getAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
        e.setTitle(event.getUser().getName() + " lost the `" + event.getRoles().get(0).getName() + "` role in " + event.getGuild().getName());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("channels: ")) {
                    int channels = Integer.parseInt(vc.getName().substring(10)) - 1;
                    vc.getManager().setName("Channels: " + channels).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.RED);
        e.setTitle("`" + event.getChannel().getName() + "` was just deleted in " + event.getGuild().getName());
        e.setDescription(event.getChannel().getName() + " was deleted on " + event.getChannel().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        if(event.getGuild().getTextChannelsByName("logs", true).isEmpty()) {
            return;
        }
        if(!event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).isEmpty()) {
            Category statcat = event.getGuild().getCategoriesByName("\uD83D\uDCCA SERVER STATS \uD83D\uDCCA", false).get(0);
            for(VoiceChannel vc : statcat.getVoiceChannels()) {
                if(vc.getName().toLowerCase().startsWith("channels: ")) {
                    int channels = Integer.parseInt(vc.getName().substring(10)) + 1;
                    vc.getManager().setName("Channels: " + channels).queue();
                }
            }
        }
        try {
            if(Tools.getLogsType(event.getGuild().getIdLong()).equals("LOGSoff")) {
                return;
            }
        } catch (IOException e) {
            
        }
        TextChannel tc = event.getGuild().getTextChannelsByName("logs", true).get(0);
        EmbedBuilder e = new EmbedBuilder();
        e.setColor(Color.GREEN);
        e.setTitle("`" + event.getChannel().getName() + "` was just created in " + event.getGuild().getName());
        e.setDescription(event.getChannel().getName() + " was created on " + event.getChannel().getTimeCreated());
        tc.sendMessage(e.build()).queue();
    }


}
