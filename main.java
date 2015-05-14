package io.github.FoxInFlame.MessageAnnouncer;

import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class main extends JavaPlugin {
    private FileConfiguration fc;
    private int max;
    private int lastmsg;
    private int cd;

    public main() {
        this.fc = this.getConfig();
        this.max = 1;
        this.lastmsg = 0;
    }

    public void onEnable() {
      this.getConfig().options().copyDefaults(true);
      this.saveConfig();
      this.load();
      this.getLogger().info(ChatColor.YELLOW + "MessageAnnouncer version 0.2 has been enabled!");
    }
    
    public void onDisable() {
      this.getLogger().info(ChatColor.YELLOW + "MessageAnnouncer version 0.2 has been disabled!");
    }

    public void load() {
        this.cd = this.fc.getInt("interval");
        this.max = this.fc.getConfigurationSection("Announcements").getKeys(false).size();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable(){

            @Override
            public void run() {
                if (main.this.cd == 0) {
                    main.access$2(main.this, main.this.fc.getInt("interval"));
                    int msg = 1;
                    if (main.this.lastmsg < main.this.max) {
                        msg = main.this.lastmsg + 1;
                    }
                    if (main.this.lastmsg == main.this.max) {
                        msg = 1;
                    }
                    main.access$5(main.this, msg);
                    for (String s : main.this.fc.getStringList("Announcements." + msg + ".msgs")) {
                        main.this.sendMessage(s);
                    }
                } else if (main.this.cd > 0) {
                    main main = main.this;
                    main.access$2(main, main.cd - 1);
                }
            }
        }, 0, 20);
    }

    public void sendMessage(String msg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            String replacePlayerName = (String)msg.replace((CharSequence)"{playername}", (CharSequence)p.getName());
            String replaceServerName = (String)replacePlayerName.replace((CharSequence)"{servername}",(CharSequence)this.getServer().getName());
            String FinalMSG = (String)replaceServerName.replace((CharSequence)"{version}",(CharSequence)this.getConfiguration().getVersion();
            p.sendMessage(ChatColor.RED + "[Announcement]" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes((char)'&', FinalMSG));
        }
    }

    static synthetic void access$2(main main, int n) {
        main.cd = n;
    }

    static synthetic void access$5(main main, int n) {
        main.lastmsg = n;
    }

}

