package bot.commands.fun;

import bot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class table implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            String[] tables = {"https://www.sightunseen.com/content/uploads/2019/09/furniture-pond-table-03-by-kunsik.jpg", "https://lh6.googleusercontent.com/_gKQKwLZ8XUs/TWrTlvCN1zI/AAAAAAAAE50/NcQudqOilcI/s800/straight-line-designs-burnt.jpg", "https://www.sightunseen.com/content/uploads/2019/09/furniture-pond-table-05-by-kunsik.jpg", "https://i.ya-webdesign.com/images/table-clip-transparent.png", "https://cdn.incollect.com/sites/default/files/medium/Berthold-Muller-Weird-Shaped-Mosaic-Coffee-Table-by-Berthold-M-ller-Germany-303996-991666.jpg", "https://www.subsonic.com/3212-thickbox_default/dragon-ball-super-gamer-desk.jpg", "https://shop.prospecdesigns.eu/wp-content/uploads/2015/02/R2s_BR_G.png", "https://img1.cgtrader.com/items/1031131/26059051b5/gamer-desk-3d-model-low-poly-fbx.jpg", "https://secure.img1-fg.wfcdn.com/im/65309975/resize-h800-w800%5Ecompr-r85/1039/103910433/Burov+Gaming+Desk.jpg", "https://images-na.ssl-images-amazon.com/images/I/71i%2Bas9P0%2BL._SL1500_.jpg", "https://en.wiktionary.org/wiki/table#/media/File:Table.png", "https://www.oakcreekamishfurniture.com/wp-content/uploads/2018/06/42-x-72-denver-dbl-pedestal.jpg", "https://ii.worldmarket.com/fcgi-bin/iipsrv.fcgi?FIF=/images/worldmarket/source/33327_XXX_v1.tif&wid=2000&cvt=jpeg", "https://hivemodern.com/public_resources/wood-table-edward-barber-jay-osgerby-vitra-1.jpg", "https://alacraterentals.com/wp-content/uploads/2018/02/ALaCrate-Rentals-Table-Harvest-ThreeQuarterView-Wood-Table-Rental-Weddings-Events-MadeInWisconsin-WEB-600x600-ALM2.jpg", "https://www.potterybarn.com/pbimgs/rk/images/dp/wcm/202001/0015/reed-extending-dining-table-c.jpg", "https://lh5.ggpht.com/_hVfE2qcyzXU/TS9taDxZVGI/AAAAAAAABno/HPmaskejDpo/s800/creative-tables-nature.jpg", "https://2.bp.blogspot.com/-qII9T3yhXug/Tw8M9-I9SmI/AAAAAAAAJqk/a1Hi9ugzPFo/s1600/weirdtables01.jpg", "https://diningroomdid.com/dining-room/wp-content/uploads/2017/07/Funny-Dining-Tables-%E2%80%93-Weird-and-Funny-Designs-of-Dining-Tables-4-550x285.jpg", "https://www.oddee.com/wp-content/uploads/imgs/art450x300/96824.jpg"};
            EmbedBuilder e = new EmbedBuilder();
            int ran = (int)(Math.random()*1000)%tables.length;
            e.setImage(tables[ran]);
            event.getChannel().sendMessage(e.build()).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage:\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Shows a random picture of a table\n" +
                "Usage: `a!table`";
    }

    @Override
    public String getInvoke() {
        return "table";
    }
}
