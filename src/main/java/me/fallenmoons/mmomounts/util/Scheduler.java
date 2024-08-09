package me.fallenmoons.mmomounts.util;

import me.fallenmoons.mmomounts.Mmomounts;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Mmomounts.MODID)
public class Scheduler {

    private static final List<Consumer<TickEvent.ServerTickEvent>> tasks = new ArrayList<>();

    public static void schedule(Consumer<TickEvent.ServerTickEvent> task) {
        tasks.add(task);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<Consumer<TickEvent.ServerTickEvent>> tasksToRun = new ArrayList<>(tasks);
            tasks.clear();
            tasksToRun.forEach(task -> task.accept(event));
        }
    }
}