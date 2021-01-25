package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import java.util.List;

public class embarrass implements ICommand {

    private WebhookClient client;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
            if(args.isEmpty()) {
                event.getChannel().createWebhook("Embarrasser").setName("Embarrasser").queue();
                String url = "";
                for(Webhook w : event.getChannel().retrieveWebhooks().complete()) {
                    if(w.getName().equalsIgnoreCase("Embarrasser")) {
                        url = w.getUrl();
                    }
                }
                WebhookClientBuilder builder = new WebhookClientBuilder(url);
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("Webhook-Thread");
                    thread.setDaemon(true);
                    return thread;
                });
                this.client = builder.build();
                User boi = event.getMember().getUser();
                String[] jokes = {"I still pee my pants sometimes", "I repeated 1st grade 7 times", "I forgot how to ride a bicycle", "I don't know why people call old people boomers", "I support Bernie Sanders... I am also once again asking for your financial support.",
                        "My computer stopped working after I tried installing a free minecraft account", "I accidently drank too much vinegar thinking I was drinking water", "I sleep with my favorite stuffed animals",
                        "I have 20 IQ", "I cried because I was called stupid", "I changed my name because too many people made fun of it", "I am not good at embarrassing others", "I am adopted"};
                int random = (int)(Math.random()*10000) % jokes.length;
                String content = jokes[random];
                WebhookMessageBuilder b = new WebhookMessageBuilder()
                        .setUsername(boi.getName())
                        .setAvatarUrl(boi.getEffectiveAvatarUrl().replaceFirst("gif", "png") + "?size=512")
                        .setContent(content);

                client.send(b.build());
                return;
            }
            if(args.size() > 1) {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            } else {
                event.getChannel().createWebhook("Embarrasser").setName("Embarrasser").queue();
                String url = "";
                for(Webhook w : event.getChannel().retrieveWebhooks().complete()) {
                    if(w.getName().equalsIgnoreCase("Embarrasser")) {
                        url = w.getUrl();
                    }
                }
                WebhookClientBuilder builder = new WebhookClientBuilder(url);
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("Webhook-Thread");
                    thread.setDaemon(true);
                    return thread;
                });
                this.client = builder.build();
                User boi = null;
                if(event.getMessage().getMentionedMembers().isEmpty()) {
                    try {
                        long id = Long.parseLong(args.get(0));
                        boi = event.getGuild().getMemberById(id).getUser();
                    } catch(Exception e) {
                        event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                        return;
                    }
                } else {
                    boi = event.getMessage().getMentionedMembers().get(0).getUser();
                }
                String[] jokes = {"I still pee I pants sometimes", "I repeated 1st grade 7 times", "I forgot how to ride a bicycle", "I don't know why people call old people boomers", "I support Bernie Sanders... I am also once again asking for your financial support.",
                "My computer stopped working after I tried installing a free minecraft account", "I accidently drank too much vinegar thinking I was drinking water", "I sleep with my favorite stuffed animals",
                "I have 20 IQ", "I cried because I was called stupid", "I changed his/her name because too many people made fun of it", "I am not good at embarrassing others", "I am adopted"};
                int random = (int)(Math.random()*10000) % jokes.length;
                String content = jokes[random];
                WebhookMessageBuilder b = new WebhookMessageBuilder()
                        .setUsername(boi.getName())
                        .setAvatarUrl(boi.getEffectiveAvatarUrl().replaceFirst("gif", "png") + "?size=512")
                        .setContent(content);

                client.send(b.build());
            }
        } else {
            event.getChannel().sendMessage("I am not allowed to embarrass mankind in this holy christian minecraft server! In order for me to be able to embarrass you children I must need the Manage Webhooks permission for this server.").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Displays an embarrassing message as a mentioned user\n" +
                "Usage: `a!embarrass [user/user_id]`";
    }

    @Override
    public String getInvoke() {
        return "embarrass";
    }
}
