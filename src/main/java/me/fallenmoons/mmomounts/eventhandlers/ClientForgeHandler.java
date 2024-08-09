package me.fallenmoons.mmomounts.eventhandlers;

import me.fallenmoons.mmomounts.Mmomounts;
import me.fallenmoons.mmomounts.init.Keybindings;
import me.fallenmoons.mmomounts.items.EntityCaptureItem;
import me.fallenmoons.mmomounts.network.PacketHandler;
import me.fallenmoons.mmomounts.network.packets.SSpawnMountPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Mmomounts.MODID, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if(Keybindings.INSTANCE.toggleMount.consumeClick()) {
            System.out.println("Toggle mount key pressed");
            PacketHandler.sendToServer(new SSpawnMountPacket());
        }
    }
}
