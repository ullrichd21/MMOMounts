package me.fallenmoons.mmomounts.items;

import me.fallenmoons.mmomounts.Mmomounts;
import me.fallenmoons.mmomounts.util.Scheduler;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;


public class EntityCaptureItem extends Item implements ICurioItem {

    public EntityCaptureItem(Properties properties) {
        super(properties);
    }

    public static boolean canStoreEntityInItem(ItemStack itemStack, Entity entity, Player player) {
        if (entity instanceof Saddleable) {
            if (((Saddleable) entity).isSaddled()){
                return itemStack.getTag() == null || !itemStack.getTag().contains("EntityData");
            }
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        System.out.println("Use on called");
        if (unstoreEntityInItem(context.getLevel(), context.getPlayer(), context.getItemInHand(), context.getClickLocation())) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean toggleStore(Entity entity, Player player, ItemStack itemInHand) {
        if (canStoreEntityInItem(itemInHand, entity, player)) {
            storeEntityInItem(itemInHand, entity, player);

            return true;
        } else {
            unstoreEntityInItem(player.level(), player, itemInHand, player.position());
        }
        return false;
    }

    public static boolean unstoreEntityInItem(Level level, Player player, ItemStack itemInHand, Vec3 pos) {
        System.out.println("Unstore entity in item called");
        if (!level.isClientSide && player != null) {
            System.out.println("Not client side");
            CompoundTag itemTag = itemInHand.getTag();
            System.out.println("Item tag: " + itemTag);

            if (itemTag != null && itemTag.contains("EntityData")) {
                // Deserialize entity data
                System.out.println("Item tag contains entity data");
                CompoundTag entityData = itemTag.getCompound("EntityData");
                EntityType<?> entityType = EntityType.byString(entityData.getString("id")).orElse(null);
                if (entityType != null) {
                    Entity entity = entityType.create(level);
                    System.out.println("Entity type: " + entityType.toString());
                    if (entity != null) {
                        entity.load(entityData);
                        entity.setPos(pos.x, pos.y, pos.z);
                        level.addFreshEntity(entity);
                        player.startRiding(entity);
                        itemInHand.removeTagKey("EntityData"); // Remove the entity data after spawning
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void storeEntityInItem(ItemStack itemStack, Entity entity, Player player) {
        CompoundTag itemTag = itemStack.getOrCreateTag();
        CompoundTag entityData = new CompoundTag();
        entity.saveWithoutId(entityData);
        entityData.putString("id", EntityType.getKey(entity.getType()).toString());
        itemTag.put("EntityData", entityData);

        Scheduler.schedule(tickEvent -> {
            entity.remove(Entity.RemovalReason.DISCARDED);
            player.sendSystemMessage(Component.literal("Entity removed after dismounting!"));
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override

}