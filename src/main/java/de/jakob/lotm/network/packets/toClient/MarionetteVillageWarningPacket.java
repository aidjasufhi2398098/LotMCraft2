package de.jakob.lotm.network.packets.toClient;

import de.jakob.lotm.LOTMCraft;
import de.jakob.lotm.network.packets.handlers.ClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MarionetteVillageWarningPacket(int ticks) implements CustomPacketPayload {

    public static final Type<MarionetteVillageWarningPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(LOTMCraft.MOD_ID, "marionette_village_warning"));

    public static final StreamCodec<ByteBuf, MarionetteVillageWarningPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MarionetteVillageWarningPacket::ticks,
            MarionetteVillageWarningPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MarionetteVillageWarningPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                ClientHandler.handleMarionetteVillageWarning(packet);
            }
        });
    }
}
