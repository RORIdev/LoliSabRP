package me.animadoria.sabotagerp;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Arrays;

public class SabotageListener {
    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {

        String ip = (Minecraft.getMinecraft().getCurrentServerData()).serverIP;
        if (!Minecraft.getMinecraft().isSingleplayer() && Arrays.asList(SabotageRP.INSTANCE.serverIPs).contains(ip)) {
            SabotageRP.INSTANCE.onServer = true;
            SabotageRP.INSTANCE.waitingServerName = true;
            e.manager.sendPacket(new C01PacketChatMessage("/server"));
        }
    }

    @SubscribeEvent
    public void onServerQuit(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        SabotageRP.INSTANCE.onServer = false;
        SabotageRP.INSTANCE.setMainMenuPresence();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if (SabotageRP.INSTANCE.onServer) {
            if (SabotageRP.INSTANCE.waitingServerName) {
                if (e.message.getUnformattedText().startsWith("Sala> Você está atualmente na sala: ")) {
                    SabotageRP.INSTANCE.currentServer = e.message.getUnformattedText().replace("Sala> Você está atualmente na sala: ", "");
                    e.setCanceled(true);
                    SabotageRP.INSTANCE.updatePresence();
                    SabotageRP.INSTANCE.waitingServerName = false;
                }
            }

            if (e.message.getUnformattedText().startsWith("Entrar> Você trocou de sala de ")) {
                SabotageRP.INSTANCE.currentServer = e.message.getUnformattedText().split(" para ")[1];
                SabotageRP.INSTANCE.updatePresence();
            }
        }


    }


}
