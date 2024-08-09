package me.fallenmoons.mmomounts.eventhandlers;

import me.fallenmoons.mmomounts.init.ModEventSubscriber;
import me.fallenmoons.mmomounts.items.EntityCaptureItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityCaptureEventHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        System.out.println("PlayerInteractEvent.EntityInteract event fired");
        if (event.getLevel().isClientSide()) {
            return;
        }

        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.getItem() == ModEventSubscriber.ENTITY_CAPTURE_ITEM.get()) {
            if (!EntityCaptureItem.canStoreEntityInItem(itemStack, entity, player)) {
                player.sendSystemMessage(Component.literal("Entity cannot be stored in item!"));
                return;
            }
            EntityCaptureItem.storeEntityInItem(itemStack, entity, player);
            System.out.println(entity.toString());
            entity.remove(Entity.RemovalReason.DISCARDED);
            player.sendSystemMessage(Component.literal("Entity stored in item!"));
            event.setCanceled(true);
        }
    }
}