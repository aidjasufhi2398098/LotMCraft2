package de.jakob.lotm.util.helper.marionettes;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

public class MarionetteComponent {
    private boolean isMarionette = false;
    private String controllerUUID = "";
    private boolean followMode = true;
    private boolean shouldAttack = true;

    // New behavior: marionettes are mostly stationary unless explicitly ordered to walk.
    private boolean movementLocked = true;
    private boolean forcedWalking = false;
    private double walkTargetX = 0;
    private double walkTargetY = 0;
    private double walkTargetZ = 0;

    public MarionetteComponent() {}

    public MarionetteComponent(boolean isMarionette, String controllerUUID) {
        this.isMarionette = isMarionette;
        this.controllerUUID = controllerUUID;
    }

    public boolean isMarionette() { return isMarionette; }
    public void setMarionette(boolean marionette) { this.isMarionette = marionette; }
    public String getControllerUUID() { return controllerUUID; }
    public void setControllerUUID(String controllerUUID) { this.controllerUUID = controllerUUID; }
    public boolean isFollowMode() { return followMode; }
    public void setFollowMode(boolean followMode) { this.followMode = followMode; }
    public boolean shouldAttack() { return shouldAttack; }
    public void setShouldAttack(boolean shouldAttack) { this.shouldAttack = shouldAttack; }

    public boolean isMovementLocked() { return movementLocked; }
    public void setMovementLocked(boolean movementLocked) { this.movementLocked = movementLocked; }

    public boolean isForcedWalking() { return forcedWalking; }

    public Vec3 getWalkTarget() {
        return new Vec3(walkTargetX, walkTargetY, walkTargetZ);
    }

    public void startForcedWalkTo(Vec3 target) {
        this.forcedWalking = true;
        this.walkTargetX = target.x;
        this.walkTargetY = target.y;
        this.walkTargetZ = target.z;
    }

    public void stopForcedWalk() {
        this.forcedWalking = false;
    }

    public static final IAttachmentSerializer<CompoundTag, MarionetteComponent> SERIALIZER =
            new IAttachmentSerializer<>() {
                @Override
                public MarionetteComponent read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider lookup) {
                    MarionetteComponent component = new MarionetteComponent();
                    component.isMarionette = tag.getBoolean("isMarionette");
                    component.controllerUUID = tag.getString("controllerUUID");
                    component.followMode = tag.getBoolean("followMode");
                    component.shouldAttack = tag.getBoolean("shouldAttack");
                    component.movementLocked = tag.contains("movementLocked") ? tag.getBoolean("movementLocked") : true;
                    component.forcedWalking = tag.getBoolean("forcedWalking");
                    component.walkTargetX = tag.getDouble("walkTargetX");
                    component.walkTargetY = tag.getDouble("walkTargetY");
                    component.walkTargetZ = tag.getDouble("walkTargetZ");
                    return component;
                }

                @Override
                public CompoundTag write(MarionetteComponent component, HolderLookup.Provider lookup) {
                    CompoundTag tag = new CompoundTag();
                    tag.putBoolean("isMarionette", component.isMarionette);
                    tag.putString("controllerUUID", component.controllerUUID);
                    tag.putBoolean("followMode", component.followMode);
                    tag.putBoolean("shouldAttack", component.shouldAttack);
                    tag.putBoolean("movementLocked", component.movementLocked);
                    tag.putBoolean("forcedWalking", component.forcedWalking);
                    tag.putDouble("walkTargetX", component.walkTargetX);
                    tag.putDouble("walkTargetY", component.walkTargetY);
                    tag.putDouble("walkTargetZ", component.walkTargetZ);
                    return tag;
                }
            };
}
