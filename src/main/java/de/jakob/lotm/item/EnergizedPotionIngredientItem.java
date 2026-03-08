package de.jakob.lotm.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnergizedPotionIngredientItem extends Item {

    private final int sequence;
    private final String[] pathways;

    public EnergizedPotionIngredientItem(Properties properties, int sequence, String... pathways) {
        super(properties);
        this.sequence = sequence;
        this.pathways = pathways;
    }

    public int getSequence() {
        return sequence;
    }

    public String[] getPathways() {
        return pathways;
    }

    public static FoodProperties defaultFoodProperties() {
        return new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(3)
                .saturationModifier(0.4f)
                .build();
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            int sequencePower = Math.max(1, 10 - sequence);
            int amplifier = Math.max(0, Math.min(4, (sequencePower - 1) / 2));
            int duration = 20 * (6 + sequencePower * 2);

            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, false, true, true));
        }

        return result;
    }
}
