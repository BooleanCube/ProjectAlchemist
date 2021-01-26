package bot;

//These programs are run on an ubuntu server so the file directories might be a bit weird.



//import bot.commands.databases.CustomizableDataSource;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.util.*;

public class Tools {
    public static HashMap<Member, Integer> infractions = new HashMap<>();
    public static String[] accessories = {"Lead-Chunk(Collectible)", "Thread(Collectible)", "Beaker(Collectible)", "Powdered-Iron(Collectible)", "Mortar(Collectible)", "Pestle(Collectible)", "Spoon(Collectible)"};
    public static double LEVELCONSTANT = 1.7;

    public static boolean autoRoleEnabled(Guild g) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/autorole.txt")));
        String line;
        while((line = bf.readLine()) != null) {
            if(line.startsWith(g.getId())) {
                return line.split(" ")[1].equalsIgnoreCase("true");
            }
        }
        return false;
    }

    public static void toggleAutoRole(Guild g) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/autorole.txt")));
        StringBuilder input = new StringBuilder();
        boolean flag = false;
        String line;
        while((line = bf.readLine()) != null) {
            if(line.equalsIgnoreCase(g.getId() + " true")) {
                flag = true;
                input.append(g.getId() + " false\n");
            } else if(line.equalsIgnoreCase(g.getId() + " false")) {
                flag = true;
                input.append(g.getId() + " true\n");
            } else input.append(line + "\n");
        }
        if(!flag) {
            input.append(g.getId() + " true\n");
        }
        FileWriter fw = new FileWriter(new File("/home/username/Desktop/alchemist/autorole.txt"));
        fw.write(input.toString());
        fw.flush();
    }

    public static long TotalXPToLevel(long xp) {
        long level= 0;
        while(true) {
            long current = Tools.levelToXP(level);
            if(current > xp) {
                return level;
            }
            level++;
        }
    }

    public static long levelToXP(long level) {
        return (level+1)*500;
    }

    public static long levelToTotalXP(long level) {
        return Tools.levelToXP(level) + (level*500);
    }

    public static boolean DMLevelNotifs(Guild g, Member m) {
        try {
            File f = new File("/home/username/Desktop/alchemist/XP/"+g.getId()+".txt");
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String input;
            while((input=bf.readLine()) != null) {
                if(input.split(" ")[0].equalsIgnoreCase(m.getId())) {
                    if (input.split(" ").length == 2) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void toggleDMLevelNotifs(Guild g, Member m) {
        try {
            File f = new File("/home/username/Desktop/alchemist/XP/"+g.getId()+".txt");
            BufferedReader bf = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String input;
            while((input=bf.readLine()) != null) {
                if(input.split(" ")[0].equalsIgnoreCase(m.getId())) {
                        if(input.split(" ").length == 2) {
                        sb.append(input + " lOFF\n");
                    } else {
                        sb.append(input.split(" ")[0]+" "+input.split(" ")[1]+ "\n");
                    }
                } else {
                    sb.append(input + "\n");
                }
            }
            bf.close();
            FileWriter fw = new FileWriter(f);
            fw.write(sb.toString());
            fw.flush();
        } catch(Exception e) {

        }
    }

    public static long timeToSeconds(String time) {
        long seconds = 0;
        String[] timeArray = time.split(":");
        if(timeArray.length==4 && time.length()==11) {
            seconds += Long.parseLong(timeArray[0])*24*60*60;
            seconds += Long.parseLong(timeArray[1])*60*60;
            seconds += Long.parseLong(timeArray[2])*60;
            seconds += Long.parseLong(timeArray[3]);
            return seconds;
        }
        return -1;
    }

    public static int getRank(long userId, Guild g) throws IOException {
        List<Member> members = g.getMembers();
        ArrayList<Long> XP = new ArrayList<>();
        for(Member m : members) {
            if (m.getUser().isBot()) {
                continue;
            }
            long MemberXP = Tools.getXP(m.getIdLong(), g.getIdLong());
            XP.add(MemberXP);
        }
        long authorsXP = Tools.getXP(userId, g.getIdLong());
        Collections.sort(XP);
        Collections.reverse(XP);
        return XP.indexOf(authorsXP)+1;
    }

    public static String getLogsType(long serverId) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/Customization.txt")));
        String input;
        while((input=bf.readLine()) != null) {
            if(input.split(" ")[0].equalsIgnoreCase(Long.toString(serverId))) {
                return input.split(" ")[2];
            }

        }
        addServerCustomization(serverId, true, true, true);
        return "LIGHTLOGSon";
    }

    public static void setLogsType(long serverId, String logs) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/Customization.txt")));
        String input;
        StringBuilder sb = new StringBuilder();
        while((input=bf.readLine()) != null) {
            if(input.split(" ")[0].equalsIgnoreCase(Long.toString(serverId))) {
                String XP = input.split(" ")[1];
                sb.append(serverId + " " + XP + " " + logs + "\n");
            } else {
                sb.append(input + "\n");
            }
        }
        bf.close();
        FileWriter fw = new FileWriter(new File("/home/username/Desktop/alchemist/Customization.txt"));
        fw.write(sb.toString());
        fw.flush();
    }


    public static boolean XPon(long serverId) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/Customization.txt")));
        String input;
        while((input=bf.readLine()) != null) {
            if(input.split(" ")[0].equalsIgnoreCase(Long.toString(serverId))) {
                return input.split(" ")[1].equalsIgnoreCase("XPon");
            }
        }
        addServerCustomization(serverId, true, true, true);
        return true;
    }

    public static void toggleXP(long serverId) throws IOException {
        boolean toPut = !XPon(serverId);
        BufferedReader bf = new BufferedReader(new FileReader(new File("/home/username/Desktop/alchemist/Customization.txt")));
        String input;
        StringBuilder sb = new StringBuilder();
        while((input=bf.readLine()) != null) {
            if(input.split(" ")[0].equalsIgnoreCase(Long.toString(serverId))) {
                String logs = input.split(" ")[2];
                sb.append(toPut ? serverId + " XPon " + logs + "\n" : serverId + " XPoff " + logs + "\n");
            } else {
                sb.append(input + "\n");
            }
        }
        bf.close();
        FileWriter fw = new FileWriter(new File("/home/username/Desktop/alchemist/Customization.txt"));
        fw.write(sb.toString());
        fw.flush();
    }

    public static void addServerCustomization(long serverId, boolean XP, boolean logs, boolean logIsLight) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/Customization.txt"));
        StringBuilder sb = new StringBuilder();
        String input;
        while ((input = br.readLine()) != null) {
            sb.append(input).append('\n');
        }
        br.close();
        FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/Customization.txt");
        String text = serverId+"";
        if(XP) {
            text += " XPon";
        } else {
            text += " XPoff";
        }
        if(logs) {
            if(logIsLight) {
                text += " LIGHTLOGSon";
            } else {
                text += " LOGSon";
            }
        } else {
            text += " LOGSoff";
        }
        sb.append(text);
        fw.write(sb.toString());
        fw.flush();

    }


    public static void addXP(long userId, long serverId, int XP) throws IOException {
        File serverXPFile = new File("/home/username/Desktop/alchemist/XP/" + serverId + ".txt");
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(serverXPFile));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.split(" ")[0].equalsIgnoreCase(Long.toString(userId))) {
                    long newXP = Long.parseLong(input.split(" ")[1]) + XP;
                    if(newXP < 0) {
                        newXP = 0;
                    }
                    flag = true;
                    if(input.contains("lOFF")) {
                        sb.append(userId).append(" ").append(newXP).append(" ").append("lOFF").append("\n");
                    } else {
                        sb.append(userId).append(" ").append(newXP).append("\n");
                    }
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter(serverXPFile);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            addMemberXP(userId, serverId, XP);
        }
    }

    public static long getXP(long userId, long serverId) throws IOException {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/XP/" + serverId + ".txt"));
        } catch(IOException e) {
            addMemberXP(userId, serverId, 0);
            return 0;
        }
        String input;
        while((input=bf.readLine()) != null) {
            if(input.split(" ")[0].equalsIgnoreCase(Long.toString(userId))) {
                return Long.parseLong(input.split(" ")[1]);
            }
        }
        addMemberXP(userId, serverId, 0);
        return 0;
    }

    public static void addMemberXP(long userId, long serverId, int XP) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/XP/" + serverId + ".txt"));
            StringBuilder sb = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null) {
                sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/XP/" + serverId + ".txt");
            String text = userId + " " + XP;
            sb.append(text);
            fw.write(sb.toString());
            fw.flush();
        } catch (FileNotFoundException e) {
            File folder = new File("/home/username/Desktop/alchemist/XP/");
            File f = new File(folder, serverId+".txt");
            FileWriter fw = new FileWriter(f);
            String text= userId + " " + XP;
            fw.write(text);
            fw.flush();
        }

    }








    public static boolean isAdmin(Member m) {
        if(m.hasPermission(Permission.MANAGE_SERVER)) {
            return true;
        }
        return false;
    }
    public static void setInfractions(Member m, int num) {
        try {
            infractions.remove(m);
            infractions.put(m, num);
        } catch(Exception e) {

        }
    }
    public static void addInfraction(Member m) {
        int num = 0;
        try {
            num = infractions.get(m);
            infractions.remove(m);
        } catch(Exception e) {

        }
        infractions.put(m, ++num);
    }
    public static int getInfractions(Member m) {
        try {
            return infractions.get(m);
        } catch(Exception e) {
            return 0;
        }
    }
    public static List<Long> getGold(Member m) {
        String mid = m.getUser().getId();
        ArrayList<Long> bal = new ArrayList<>();
        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.split(" ")[0].equals(mid)) {
                    bal.add(Long.parseLong(input.split(" ")[1]));
                    bal.add(Long.parseLong(input.split(" ")[2]));
                    bal.add(Long.parseLong(input.split(" ")[3]));
                    flag = true;
                }
            }
            br.close();
        } catch (IOException e) {

        }
        if(!flag) {
            addMember(m, 0);
            bal.add(0l);
            bal.add(0l);
            bal.add(100l);
        }
        return bal;
    }
    public static void addGold(Member m, long num) {
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    long bank = Long.parseLong(input.split(" ")[1]);
                    long x = Long.parseLong(input.split(" ")[2]);
                    long max = Long.parseLong(input.split(" ")[3]);
                    x += num;
                    if(x < 0) {
                        x = 0;
                    }
                    flag = true;
                    sb.append(mid).append(" ").append(bank).append(" ").append(x).append(" ").append(max).append("\n");
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/currency.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            addMember(m, num);
        }
    }
    public static void addMember(Member m, long num) {
        String mid = m.getUser().getId();

        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            StringBuilder sb = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null) {
                sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/currency.txt");
            String text = mid + " 0 " + num + " 100";
            sb.append(text);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
    }
    public static String secondsToTime(long timeseconds) {
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
    public static String withdraw(Member m, String n) {
        long num = 0;
        List<Long> bruh = getGold(m);
        try {
            num = Long.parseLong(n);
        } catch(Exception e) {
            if(n.equalsIgnoreCase("all")) {
                num = bruh.get(0);
            } else {
                return "**" + n + " is not an amount that I can withdraw from the bank!**";
            }
        }
        if(bruh.get(0) == 0) {
            return "**There is nothing to withdraw!**";
        }
        if(num > bruh.get(0)) {
            return "**You can't deposit more than you already have!**";
        }
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    long bank = Long.parseLong(input.split(" ")[1]);
                    bank -= num;
                    long x = Long.parseLong(input.split(" ")[2]);
                    long max = Long.parseLong(input.split(" ")[3]);
                    x += num;
                    flag = true;
                    sb.append(mid).append(" ").append(bank).append(" ").append(x).append(" ").append(max).append("\n");
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/currency.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            addMember(m, num);
        }
        return "**" + num + " gold has been withdrawn!**";
    }
    public static String deposit(Member m, String n) {
        long num = 0;
        List<Long> bruh = getGold(m);
        long max = bruh.get(2);
        try {
            num = Long.parseLong(n);
            if(max < num) {
                return "**Your bank can't hold that much! Try buying some credit cards to add some space to your bank**";
            }
        } catch(Exception e) {
            if(n.equalsIgnoreCase("all")) {
                num = bruh.get(1);
            } else {
                return "**" + n + " is not an amount that I can deposit in the bank!**";
            }
        }
        if(num > (max - bruh.get(0))) {
            num = max - bruh.get(0);
            if(num == 0) {
                return "**Nothing was deposited because you have have a full bank!**";
            }
        }
        if(bruh.get(1) == 0) {
            return "**There is nothing to deposit!**";
        }
        if(num > bruh.get(1)) {
            return "**You can't deposit more than you already have!**";
        }
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    long bank = Long.parseLong(input.split(" ")[1]);
                    bank += num;
                    long x = Long.parseLong(input.split(" ")[2]);
                    x -= num;
                    flag = true;
                    sb.append(mid).append(" ").append(bank).append(" ").append(x).append(" ").append(max).append("\n");
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/currency.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            addMember(m, num);
        }
        return "**" + num + " gold has been deposited!**";
    }
    public static MessageEmbed giveGold(Member m1, Member m2, long num) {
        if(num < 0) {
            return new EmbedBuilder()
                    .setDescription("**You can't give a negative amount of gold!**")
                    .setAuthor(m1.getUser().getName(), m1.getUser().getAvatarUrl(), m1.getUser().getEffectiveAvatarUrl())
                    .build();
        }
        if(num == 0) {
            return new EmbedBuilder()
                    .setDescription("**You can't give 0 gold!**")
                    .setAuthor(m1.getUser().getName(), m1.getUser().getAvatarUrl(), m1.getUser().getEffectiveAvatarUrl())
                    .build();
        }
        if(getGold(m1).get(1) == 0) {
            return new EmbedBuilder()
                    .setDescription("**You do not have enough gold!**")
                    .setAuthor(m1.getUser().getName(), m1.getUser().getAvatarUrl(), m1.getUser().getEffectiveAvatarUrl())
                    .build();
        }
        Tools.addGold(m1, -num);
        Tools.addGold(m2, num);
        return new EmbedBuilder()
                .setDescription("**" + m1.getAsMention() + "** just gave **" + m2.getAsMention() + "** **" + num + " gold**")
                .setAuthor(m1.getUser().getName(), m1.getUser().getAvatarUrl(), m1.getUser().getEffectiveAvatarUrl())
                .build();
    }
    public static void expandBank(Member m, long space) {
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/currency.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    long bank = Long.parseLong(input.split(" ")[1]);
                    long x = Long.parseLong(input.split(" ")[2]);
                    long max = Long.parseLong(input.split(" ")[3]);
                    max += space;
                    flag = true;
                    sb.append(mid).append(" ").append(bank).append(" ").append(x).append(" ").append(max).append("\n");
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/currency.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            addMember(m, 0);
        }
    }
    public static void createMemberInv(Member m, String item) {
        String mid = m.getUser().getId();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/inventory.txt"));
            StringBuilder sb = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null) {
                sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/inventory.txt");
            String text = mid + " " + item;
            sb.append(text);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
    }
    public static void addInv(Member m, String item) {
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/inventory.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    if(!flag) {
                        String inv = input.substring(input.indexOf(' ')).trim();
                        String toAppend = "";
                        boolean contains = false;
                        for(String s : inv.split(" ")) {
                            if(s.toLowerCase().startsWith(item.toLowerCase())) {
                                if(s.indexOf("x") > 0) {
                                    int num = Integer.parseInt(s.substring(s.length()-1)) + 1;
                                    toAppend += item + "x" + num + " ";
                                } else {
                                    toAppend += item + "x2 ";
                                }
                                contains = true;
                            } else {
                                toAppend += s + " ";
                            }
                        }
                         if(!contains) {
                             toAppend += item;
                         }
                        sb.append(mid).append(" ").append(toAppend.trim()).append("\n");
                        flag = true;
                    }
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/inventory.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            createMemberInv(m, item);
        }
    }
    public static void removeFromInv(Member m, String item) {
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/inventory.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    if(!flag) {
                        String inv = input.substring(input.indexOf(' ')).trim();
                        String toAppend = "";
                        boolean contains = false;
                        for(String s : inv.split(" ")) {
                            if(s.toLowerCase().startsWith(item.toLowerCase())) {
                                if(s.indexOf("x") > 0) {
                                    int num = Integer.parseInt(s.substring(s.length()-1)) - 1;
                                    if(num == 1) {
                                        toAppend += item + " ";
                                    } else if(num > 0) {
                                        toAppend += item + "x" + num + " ";
                                    }

                                }
                                contains = true;
                            } else {
                                toAppend += s + " ";
                            }
                        }
                        if(!contains) {
                            toAppend += item;
                        }
                        sb.append(mid).append(" ").append(toAppend.trim()).append("\n");
                        flag = true;
                    }
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/inventory.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(!flag) {
            createMemberInv(m, item);
        }
    }
    public static List<String> getInv(Member m) {
        String mid = m.getUser().getId();
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        List<String> toReturn = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/username/Desktop/alchemist/inventory.txt"));
            String input;
            while ((input = br.readLine()) != null) {
                if (input.substring(0, input.indexOf(' ')).equals(mid)) {
                    String inv = String.join(" ", input.substring(input.indexOf(' ')).trim().split(" "));
                    toReturn = Arrays.asList(inv.split(" "));
                    sb.append(mid).append(" ").append(inv).append("\n");
                    flag = true;
                } else sb.append(input).append('\n');
            }
            br.close();
            FileWriter fw = new FileWriter("/home/username/Desktop/alchemist/inventory.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {

        }
        if(flag) {
            return toReturn;
        } else {
            createMemberInv(m, "");
            return new ArrayList<>();
        }
    }
    public static String trimX(String item) {
        if(item.indexOf("x") >= 0) {
            return item.substring(0, item.length()-2);
        }
        return item;
    }
}
