package me.fallenmoons.mmomounts.init;

import me.fallenmoons.mmomounts.Mmomounts;
import me.fallenmoons.mmomounts.items.EntityCaptureItem;
import me.fallenmoons.mmomounts.network.PacketHandler;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mmomounts.MODID);
    public static final RegistryObject<Item> ENTITY_CAPTURE_ITEM = ITEMS.register("entity_capture_item",
            () -> new EntityCaptureItem(new Item.Properties()));

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            System.out.println("Common setup called");
            System.out.println("Registering packet handler");
            PacketHandler.register();
        });
    }
}