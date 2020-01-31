package me.animadoria.sabotagerp;

/*import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;*/

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.time.OffsetDateTime;

@Mod(modid = SabotageRP.MODID, version = SabotageRP.VERSION, clientSideOnly = true, name = SabotageRP.MODNAME)
public class SabotageRP {
    public static final String MODID = "sabotagerp";
    public static final String VERSION = "1.0";
    public static final String MODNAME = "Sabotage RP";

    @Mod.Instance
    public static SabotageRP INSTANCE;

    public IPCClient discord;
    public boolean onServer;
    public boolean waitingServerName;
    public String currentServer;

    public String[] serverIPs = new String[]{"sabotador.com", "mini.gamemods.com.br", "sab.sabotador.com", "jogar.gamemods.com.br"};

    @EventHandler
    public void init(FMLInitializationEvent event) {
        discord = new IPCClient(672105611588272129L);
        try {
            discord.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setMainMenuPresence();

        MinecraftForge.EVENT_BUS.register(new SabotageListener());
    }


    public void updatePresence() {
        RichPresence.Builder builder = new RichPresence.Builder()
                .setDetails("Na sala " + currentServer)
                .setStartTimestamp(OffsetDateTime.now())
                .setSmallImage("gamemods512c", "sabotador.com")
                .setLargeImage("sabotador");
        if (currentServer.contains("Hub"))
            builder.setDetails("No Hub");

        discord.sendRichPresence(builder.build());
    }

    public void setMainMenuPresence() {
        discord.sendRichPresence(new RichPresence.Builder().setDetails("No menu principal").build());
    }
}
