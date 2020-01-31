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
        if (!Minecraft.getMinecraft().isSingleplayer() && (Arrays.asList(SabotageRP.instance.serverIPs).contains(ip) || ip.endsWith("sabotador.com"))) { // if it's not single player AND it contains allowed ips or the ip ends with sabotador.com
            SabotageRP.instance.onServer = true;
            SabotageRP.instance.waitingServerName = true;
            e.manager.sendPacket(new C01PacketChatMessage("/server"));
        }
        else {
            SabotageRP.instance.setOtherPresence();
        }
    }

    @SubscribeEvent
    public void onServerQuit(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        SabotageRP.instance.onServer = false;
        SabotageRP.instance.setMainMenuPresence();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if (SabotageRP.instance.onServer) {
            if (SabotageRP.instance.waitingServerName) {
                if (e.message.getUnformattedText().startsWith("Sala> Você está atualmente na sala: ")) {
                    SabotageRP.instance.currentServer = e.message.getUnformattedText().replace("Sala> Você está atualmente na sala: ", "");
                    e.setCanceled(true);
                    SabotageRP.instance.updatePresence();
                    SabotageRP.instance.waitingServerName = false;
                }
            }

            if (e.message.getUnformattedText().startsWith("Entrar> Você trocou de sala de ")) {
                SabotageRP.instance.currentServer = e.message.getUnformattedText().split(" para ")[1];
                SabotageRP.instance.updatePresence();
            }
        }


    }


}
