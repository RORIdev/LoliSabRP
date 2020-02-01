package me.animadoria.sabotagerp;

import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentTranslation;

public class SabotageRPCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "sabotagerp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.sabotagerp.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new WrongUsageException("commands.sabotagerp.usage");
        }
        if (args[0].equals("on")) {
            if (SabotageRP.instance.discordEnabled) {
                sender.addChatMessage(new ChatComponentTranslation("commands.sabotagerp.enable.error"));
                return;
            }
            SabotageRP.instance.discordEnabled = true;
            SabotageRP.instance.waitingServerName = true;
            Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C01PacketChatMessage("/server"));

            sender.addChatMessage(new ChatComponentTranslation("commands.sabotagerp.enable.success"));

            try {
                SabotageRP.instance.discord.connect();
            } catch (NoDiscordClientException e) {
                e.printStackTrace();
            }
        }
        if (args[0].equals("off")) {
            if (!SabotageRP.instance.discordEnabled)
            {
                sender.addChatMessage(new ChatComponentTranslation("commands.sabotagerp.disable.error"));
                return;
            }
            SabotageRP.instance.discordEnabled = false;
            SabotageRP.instance.discord.close();
            sender.addChatMessage(new ChatComponentTranslation("commands.sabotagerp.disable.success"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
