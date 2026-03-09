package de.jakob.lotm.events;

import de.jakob.lotm.LOTMCraft;
import de.jakob.lotm.attachments.ModAttachments;
import de.jakob.lotm.entity.custom.goals.*;
import de.jakob.lotm.util.helper.marionettes.MarionetteComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = LOTMCraft.MOD_ID)
public class MarionetteEventHandler {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob mob && !event.getLevel().isClientSide) {
            MarionetteComponent component = mob.getData(ModAttachments.MARIONETTE_COMPONENT.get());

            // Re-add marionette goals if this entity is a marionette
            if (component.isMarionette()) {
                mob.targetSelector.removeAllGoals(goal ->
                        goal instanceof StrollThroughVillageGoal ||
                        goal instanceof BreedGoal ||
                        goal instanceof MoveToBlockGoal ||
                        goal instanceof PanicGoal ||
                        goal instanceof RandomStrollGoal ||
                        goal instanceof TargetGoal
                );

                mob.goalSelector.addGoal(0, new MarionetteFollowGoal(mob));
                mob.goalSelector.addGoal(0, new MarionetteLoadChunksGoal(mob));
                mob.goalSelector.addGoal(1, new MarionetteStayGoal(mob));
                mob.goalSelector.addGoal(1, new MarionetteUseAbilityGoal(mob));
                mob.targetSelector.addGoal(0, new MarionetteTargetGoal(mob));
                mob.goalSelector.addGoal(10, new MarionetteLifelinkGoal(mob));
                mob.setTarget(null);

                if(!(event.getLevel() instanceof ServerLevel serverLevel)) {
                    return;
                }

                loadChunksAroundEntity(serverLevel, mob, 2);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Mob mob) || mob.level().isClientSide) {
            return;
        }

        MarionetteComponent component = mob.getData(ModAttachments.MARIONETTE_COMPONENT.get());
        if (!component.isMarionette()) {
            return;
        }

        if (component.isForcedWalking()) {
            Vec3 target = component.getWalkTarget();
            Vec3 delta = target.subtract(mob.position());
            double horizontalDistance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

            if (horizontalDistance < 0.6) {
                component.stopForcedWalk();
                mob.setDeltaMovement(Vec3.ZERO);
                mob.getNavigation().stop();
                return;
            }

            double speed = 0.12;
            Vec3 movement = new Vec3(delta.x / Math.max(horizontalDistance, 0.0001) * speed, mob.getDeltaMovement().y, delta.z / Math.max(horizontalDistance, 0.0001) * speed);
            mob.setDeltaMovement(movement);
            mob.hurtMarked = true;
            return;
        }

        // Default marionette behavior: no independent movement/AI-like wandering.
        if (component.isMovementLocked()) {
            mob.setDeltaMovement(Vec3.ZERO);
            mob.getNavigation().stop();
            mob.setTarget(null);
        }
    }

    private static void loadChunksAroundEntity(ServerLevel level, Mob mob, int radius) {
        ChunkPos center = new ChunkPos(mob.blockPosition());

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos pos = new ChunkPos(center.x + dx, center.z + dz);
                level.setChunkForced(pos.x, pos.z, true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTeleport(EntityTeleportEvent event) {
        if(!(event.getEntity() instanceof Mob mob) || mob.level().isClientSide || !(mob.level() instanceof ServerLevel level)) {
            return;
        }

        MarionetteComponent component = mob.getData(ModAttachments.MARIONETTE_COMPONENT.get());
        if(!component.isMarionette()) {
            return;
        }

        loadChunksAroundEntity(level, mob, 2);
    }
}
