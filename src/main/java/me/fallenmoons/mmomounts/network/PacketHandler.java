package me.fallenmoons.mmomounts.network;

import me.fallenmoons.mmomounts.Mmomounts;
import me.fallenmoons.mmomounts.network.packets.SSpawnMountPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(Mmomounts.MODID, "main"))
            .clientAcceptedVersions((version)->true)
            .serverAcceptedVersions((version)->true)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        int id = 0;
        INSTANCE.messageBuilder(SSpawnMountPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SSpawnMountPacket::encode)
                .decoder(SSpawnMountPacket::decode)
                .consumerMainThread(SSpawnMountPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }
//    public static void sendToServer(Object msg) {
//        INSTANCE.send(new PacketDistributor.PacketTarget(), PacketDistributor.SERVER.noArg());
//    }
//
//    public static void sendToPlayer(PacketDistributor.PacketTarget msg, ServerPlayer player) {
//        INSTANCE.send(msg, PacketDistributor.PLAYER.with(() -> player));
//    }
//
//    public static void sendToAll(PacketDistributor.PacketTarget msg) {
//        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
//    }
}
