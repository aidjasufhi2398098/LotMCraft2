package de.jakob.lotm.abilities.error;

import de.jakob.lotm.abilities.core.SelectableAbility;
import de.jakob.lotm.attachments.ModAttachments;
import de.jakob.lotm.util.ControllingUtil;
import de.jakob.lotm.util.helper.AbilityUtil;
import de.jakob.lotm.util.helper.DamageLookup;
import de.jakob.lotm.util.helper.marionettes.MarionetteComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class HostControllingAbility extends SelectableAbility {
    public HostControllingAbility(String id) {
        super(id, .5f);
    }

    @Override
    public Map<String, Integer> getRequirements() {
        return new HashMap<>(Map.of("error", 4));
    }

    @Override
    protected float getSpiritualityCost() {
        return 0;
    }

    @Override
    protected String[] getAbilityNames() {
        return new String[]{"ability.lotmcraft.host_controlling.drain_health", "ability.lotmcraft.host_controlling.kill", "ability.lotmcraft.host_controlling.control_host"};
    }

    @Override
    protected void castSelectedAbility(Level level, LivingEntity entity, int abilityIndex) {
        if(!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        LivingEntity host = ParasitationAbility.getHostForEntity(serverLevel, entity);
        if(host == null) {
            AbilityUtil.sendActionBar(entity, Component.translatable("ability.lotmcraft.host_controlling.no_host").withColor(0x3240bf));
            return;
        }

        switch (abilityIndex) {
            case 0 -> {
                float healthToDrain = (float) (DamageLookup.lookupDamage(4, .75f) * multiplier(entity));

                host.hurt(entity.damageSources().magic(), healthToDrain);
                entity.heal(healthToDrain);
            }
            case 1 -> {
                host.setHealth(0.5f);
                host.hurt(entity.damageSources().magic(), 1000);
            }
            case 2 -> {
                if (!(entity instanceof ServerPlayer player)) {
                    return;
                }

                MarionetteComponent component = host.getData(ModAttachments.MARIONETTE_COMPONENT.get());
                if (!component.isMarionette()) {
                    AbilityUtil.sendActionBar(entity, Component.literal("Host is not a marionette").withColor(0xa26fc9));
                    return;
                }

                // Parasite can jump into and control the marionette like puppeteering control.
                ControllingUtil.possess(player, host);
            }
        }
    }
}
