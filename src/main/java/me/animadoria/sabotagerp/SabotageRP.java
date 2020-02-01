package me.animadoria.sabotagerp;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import scala.collection.parallel.ParSeqLike;

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
    public boolean waitingGameNameChange;
    public String currentServer;
    public String currentGame;

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
                    .setDetails(I18n.format("sabotagerp.presence.room", currentServer))
                    .setState(I18n.format("sabotagerp.presence.game", I18n.format("sabotagerp.game." + currentGame)))
                    .setStartTimestamp(OffsetDateTime.now())
                    .setSmallImage("gamemods512c", "sabotador.com")
                    .setLargeImage("sabotador");
            if (currentServer.toLowerCase().contains("hub"))
                builder.setDetails(I18n.format("sabotagerp.presence.hub")).setState(null);

            discord.sendRichPresence(builder.build());
        }
    }

    public void setMainMenuPresence() {
        if (discordEnabled)
            discord.sendRichPresence(new RichPresence.Builder().setDetails(I18n.format("sabotagerp.presence.mainmenu")).build());
    }

    public void setOtherPresence() {
        if (discordEnabled)
            discord.sendRichPresence(new RichPresence.Builder().setDetails(I18n.format("sabotagerp.presence.otherserver")).build());
    }
}
