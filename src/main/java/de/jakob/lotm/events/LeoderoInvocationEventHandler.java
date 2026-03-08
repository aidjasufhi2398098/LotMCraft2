package de.jakob.lotm.events;

import de.jakob.lotm.LOTMCraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = LOTMCraft.MOD_ID)
public class LeoderoInvocationEventHandler {

    private static final long COOLDOWN_MS = 30_000L;
    private static final Map<UUID, Long> lastInvocationTime = new HashMap<>();

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        String message = event.getRawText().trim();
        if (!"leodero".equalsIgnoreCase(message)) {
            return;
        }

        ServerPlayer caster = event.getPlayer();
        long now = System.currentTimeMillis();
        long lastTime = lastInvocationTime.getOrDefault(caster.getUUID(), 0L);

        if (now - lastTime < COOLDOWN_MS) {
            long remainingSeconds = (long) Math.ceil((COOLDOWN_MS - (now - lastTime)) / 1000.0);
            caster.sendSystemMessage(Component.literal("Leodero is on cooldown for " + remainingSeconds + "s."));
            return;
        }

        lastInvocationTime.put(caster.getUUID(), now);

        if (!(caster.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if ("LEODERO".equals(message)) {
            for (ServerPlayer target : serverLevel.getPlayers(player -> player.distanceTo(caster) <= 15)) {
                for (int i = 0; i < 5; i++) {
                    summonLightning(serverLevel, target.getX(), target.getY(), target.getZ());
                }
            }
            return;
        }

        summonLightning(serverLevel, caster.getX(), caster.getY(), caster.getZ());
    }

    private static void summonLightning(ServerLevel level, double x, double y, double z) {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
        if (lightningBolt == null) {
            return;
        }

        lightningBolt.moveTo(x, y, z);
        level.addFreshEntity(lightningBolt);
    }
}
