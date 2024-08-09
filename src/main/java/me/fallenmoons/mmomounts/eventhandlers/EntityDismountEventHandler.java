package me.fallenmoons.mmomounts.eventhandlers;

import me.fallenmoons.mmomounts.init.ModEventSubscriber;
import me.fallenmoons.mmomounts.items.EntityCaptureItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import me.fallenmoons.mmomounts.util.Scheduler;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = "mmomounts")
public class EntityDismountEventHandler {

    @SubscribeEvent
    public static void onEntityDismount(EntityMountEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        System.out.println("EntityMountEvent event fired & not client side");

        if (event.isMounting()) {
            return;
        }

        Entity entity = event.getEntityMounting();
        if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) entity;
            ItemStack itemStack = player.getMainHandItem();

            if (itemStack.getItem() != ModEventSubscriber.ENTITY_CAPTURE_ITEM.get()){
                itemStack = player.getOffhandItem();
            }

            if (itemStack.getItem() != ModEventSubscriber.ENTITY_CAPTURE_ITEM.get()) {
                itemStack = CuriosApi.getCuriosHelper().getCuriosHandler(player)
                        .map(handler -> handler.getStacksHandler("mount")
                                .map(stacksHandler -> stacksHandler.getStacks().getStackInSlot(0))
                                .orElse(ItemStack.EMPTY))
                        .orElse(ItemStack.EMPTY);
            }

            if (itemStack.getItem() == ModEventSubscriber.ENTITY_CAPTURE_ITEM.get()) {
                Entity dismountedEntity = event.getEntityBeingMounted();
                if (dismountedEntity == null) {
                    return;
                }
//
//                if (!EntityCaptureItem.canStoreEntityInItem(itemStack, dismountedEntity)) {
//                    player.sendSystemMessage(Component.literal("Entity cannot be stored in item!"));
//                    return;
//                }
//                System.out.println(dismountedEntity.toString());
                EntityCaptureItem.storeEntityInItem(itemStack, dismountedEntity, player);

//                player.sendSystemMessage(Component.literal("Entity stored in item!"));
            }
        }
    }
}