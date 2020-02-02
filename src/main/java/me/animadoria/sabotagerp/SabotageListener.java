package me.animadoria.sabotagerp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Arrays;

public class SabotageListener {
    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {

        String ip = (Minecraft.getMinecraft().getCurrentServerData()).serverIP;
        if (!Minecraft.getMinecraft().isSingleplayer() && (Arrays.asList(SabotageRP.instance.serverIPs).contains(ip) || ip.endsWith("sabotador.com"))) { // if it's not single player AND it contains allowed ips or the ip ends with sabotador.com
            SabotageRP.instance.onServer = true;
            SabotageRP.instance.waitingServerName = true;
            e.manager.sendPacket(new C01PacketChatMessage("/server"));
        } else {
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
        if (SabotageRP.instance.onServer && SabotageRP.instance.discordEnabled) {
            if (SabotageRP.instance.waitingServerName) {
                if (e.message.getUnformattedText().startsWith("Sala> Você está atualmente na sala: "))
                    SabotageRP.instance.language = SabotageLanguage.PORTUGUESE;
                if (e.message.getUnformattedText().startsWith("Room> You are currently on room:"))
                    SabotageRP.instance.language = SabotageLanguage.ENGLISH;

                SabotageRP.instance.currentServer = e.message.getUnformattedText().replace(Translation.currentRoomTranslation.get(SabotageRP.instance.language), "");
                e.setCanceled(true);

                getCurrentGameByTab();
                SabotageRP.instance.waitingServerName = false;
            }
        }

        if (e.message.getUnformattedText().startsWith(Translation.switchedRoomTranslation.get(SabotageRP.instance.language))) {
            SabotageRP.instance.currentServer = e.message.getUnformattedText().split(Translation.propositionTranslation.get(SabotageRP.instance.language))[1];
            getCurrentGameByTab();
            SabotageRP.instance.updatePresence();
        }
    }


    void getCurrentGameByTab() {
        GuiPlayerTabOverlay tab = Minecraft.getMinecraft().ingameGUI.getTabList();
        IChatComponent header = ReflectionHelper.getPrivateValue(GuiPlayerTabOverlay.class, tab, "header", "field_175256_i");
        SabotageRP.instance.currentGame = header.getUnformattedText().replace("\n", "").split(Translation.tabGameMessageStart.get(SabotageRP.instance.language))[1].replace(Translation.tabGameMessageEnd.get(SabotageRP.instance.language), "");

        SabotageRP.instance.updatePresence();
    }
}



