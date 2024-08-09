package me.fallenmoons.mmomounts.network.packets;

import me.fallenmoons.mmomounts.items.EntityCaptureItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

import static me.fallenmoons.mmomounts.items.EntityCaptureItem.unstoreEntityInItem;

public class SSpawnMountPacket {
    public SSpawnMountPacket() {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static SSpawnMountPacket decode(FriendlyByteBuf buf) {
        return new SSpawnMountPacket();
    }

    public void handle(Supplier<NetworkEvent.Context> event) {
        ServerPlayer player = event.get().getSender();
        ItemStack mountItem = getCuriosMountItem(player);
        System.out.println("Mount item: " + mountItem.toString());
        if (!mountItem.isEmpty()) {
            System.out.println("Using mount item");
            BlockPos blockBelow = player.blockPosition().below();
            BlockHitResult blockHitResult = new BlockHitResult(player.position(), Direction.DOWN, blockBelow, false);
            if (mountItem.getItem() instanceof EntityCaptureItem) {

                ((EntityCaptureItem) mountItem.getItem()).unstoreEntityInItem(player.level(), player, mountItem, blockHitResult.getLocation());
            }
        }
    }

    private static ItemStack getCuriosMountItem(Player player) {
        return CuriosApi.getCuriosHelper().getCuriosHandler(player)
                .map(handler -> handler.getStacksHandler("mount")
                        .map(stacksHandler -> stacksHandler.getStacks().getStackInSlot(0))
                        .orElse(ItemStack.EMPTY))
                .orElse(ItemStack.EMPTY);
    }
}
