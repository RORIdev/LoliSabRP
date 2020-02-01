package me.animadoria.sabotagerp;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import net.minecraftforge.client.ClientCommandHandler;
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
    public static SabotageRP instance;

    public boolean discordEnabled = true;

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
        ClientCommandHandler.instance.registerCommand(new SabotageRPCommand());
    }


    public void updatePresence() {
        if (discordEnabled) {
            RichPresence.Builder builder = new RichPresence.Builder()
                    .setDetails("Na sala " + currentServer)
                    .setStartTimestamp(OffsetDateTime.now())
                    .setSmallImage("gamemods512c", "sabotador.com")
                    .setLargeImage("sabotador");
            if (currentServer.toLowerCase().contains("hub"))
                builder.setDetails("No Hub");

            discord.sendRichPresence(builder.build());
        }
    }

    public void setMainMenuPresence() {
        if (discordEnabled)
            discord.sendRichPresence(new RichPresence.Builder().setDetails("No menu principal").build());
    }

    public void setOtherPresence() {
        if (discordEnabled)
            discord.sendRichPresence(new RichPresence.Builder().setDetails("Em outro servidor").build());
    }
}
