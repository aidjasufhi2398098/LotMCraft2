package de.jakob.lotm.util.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class PotionRitualsUtil {
    private PotionRitualsUtil() {}

    private static final String ROOT = "lotm_rituals";

    private static final String ERROR3_RAID_PLAINS = "error_3_raid_plains";
    private static final String ERROR3_RAID_TAIGA = "error_3_raid_taiga";
    private static final String ERROR3_RAID_SAVANNA = "error_3_raid_savanna";

    private static final String DOOR3_VISIT_OVERWORLD = "door_3_visit_overworld";
    private static final String DOOR3_VISIT_NETHER = "door_3_visit_nether";
    private static final String DOOR3_VISIT_END = "door_3_visit_end";
    private static final String DOOR3_OVERRIDE_ELYTRA = "door_3_override_elytra";
    private static final String DOOR3_OVERRIDE_WITHER_SKULL = "door_3_override_wither_skull";
    private static final String DOOR3_OVERRIDE_DIAMOND_BLOCK = "door_3_override_diamond_block";
    private static final String DOOR3_OVERRIDE_DEEP_DARK = "door_3_override_deep_dark";

    private static final String FOOL3_END_STAY_TICKS = "fool_3_end_stay_ticks";
    private static final String FOOL3_READY_UNTIL = "fool_3_ready_until";

    private static final long SCHOLAR_REQUIRED_TICKS_IN_END = 5L * 24_000L;
    private static final long SCHOLAR_WINDOW_TICKS = 33L * 20L;

    public static void tickPlayerRituals(ServerPlayer player) {
        CompoundTag ritualTag = getRitualTag(player);
        Level level = player.level();

        if (level.dimension().equals(Level.OVERWORLD)) {
            ritualTag.putBoolean(DOOR3_VISIT_OVERWORLD, true);
        } else if (level.dimension().equals(Level.NETHER)) {
            ritualTag.putBoolean(DOOR3_VISIT_NETHER, true);
        } else if (level.dimension().equals(Level.END)) {
            ritualTag.putBoolean(DOOR3_VISIT_END, true);
        }

        if (level.dimension().equals(Level.END)) {
            long ticks = ritualTag.getLong(FOOL3_END_STAY_TICKS) + 1L;
            ritualTag.putLong(FOOL3_END_STAY_TICKS, ticks);
            if (ticks >= SCHOLAR_REQUIRED_TICKS_IN_END && ritualTag.getLong(FOOL3_READY_UNTIL) < level.getGameTime()) {
                ritualTag.putLong(FOOL3_READY_UNTIL, level.getGameTime() + SCHOLAR_WINDOW_TICKS);
            }
        } else {
            ritualTag.putLong(FOOL3_END_STAY_TICKS, 0L);
        }

        if (player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE) && player.serverLevel().isVillage(player.blockPosition())) {
            var biome = level.getBiome(player.blockPosition());
            if (isPlainsBiome(biome)) {
                ritualTag.putBoolean(ERROR3_RAID_PLAINS, true);
            }
            if (isTaigaBiome(biome)) {
                ritualTag.putBoolean(ERROR3_RAID_TAIGA, true);
            }
            if (isSavannaBiome(biome)) {
                ritualTag.putBoolean(ERROR3_RAID_SAVANNA, true);
            }
        }
    }

    public static boolean canSafelyDrinkPotion(ServerPlayer player, String pathway, int sequence) {
        CompoundTag ritualTag = getRitualTag(player);

        if ("error".equals(pathway) && sequence == 3) {
            return ritualTag.getBoolean(ERROR3_RAID_PLAINS)
                    && ritualTag.getBoolean(ERROR3_RAID_TAIGA)
                    && ritualTag.getBoolean(ERROR3_RAID_SAVANNA);
        }

        if ("door".equals(pathway) && sequence == 3) {
            boolean visited = ritualTag.getBoolean(DOOR3_VISIT_OVERWORLD)
                    && ritualTag.getBoolean(DOOR3_VISIT_NETHER)
                    && ritualTag.getBoolean(DOOR3_VISIT_END);
            if (!visited) {
                return false;
            }

            boolean hasElytra = ritualTag.getBoolean(DOOR3_OVERRIDE_ELYTRA) || hasItem(player, Items.ELYTRA, 1);
            boolean hasWitherSkull = ritualTag.getBoolean(DOOR3_OVERRIDE_WITHER_SKULL) || hasItem(player, Items.WITHER_SKELETON_SKULL, 1);
            boolean hasDiamondBlock = ritualTag.getBoolean(DOOR3_OVERRIDE_DIAMOND_BLOCK) || hasItem(player, Items.DIAMOND_BLOCK, 1);
            boolean inDeepDark = ritualTag.getBoolean(DOOR3_OVERRIDE_DEEP_DARK)
                    || player.level().getBiome(player.blockPosition()).is(Biomes.DEEP_DARK);
            return hasElytra && hasWitherSkull && hasDiamondBlock && inDeepDark;
        }

        if ("fool".equals(pathway) && sequence == 3) {
            return ritualTag.getLong(FOOL3_READY_UNTIL) >= player.level().getGameTime();
        }

        return true;
    }

    public static void onPotionConsumed(ServerPlayer player, String pathway, int sequence) {
        if ("fool".equals(pathway) && sequence == 3) {
            CompoundTag ritualTag = getRitualTag(player);
            ritualTag.putLong(FOOL3_READY_UNTIL, 0L);
            ritualTag.putLong(FOOL3_END_STAY_TICKS, 0L);
        }
    }

    public static boolean setRitual(ServerPlayer player, String pathway, int sequence, String ritual, boolean value) {
        String normalized = ritual.toLowerCase(Locale.ROOT);
        Set<String> supportedRituals = getSupportedRituals(pathway, sequence);
        if (supportedRituals.isEmpty()) {
            return false;
        }

        if ("all".equals(normalized)) {
            for (String supportedRitual : supportedRituals) {
                if (!"all".equals(supportedRitual)) {
                    applySingleRitual(player, pathway, sequence, supportedRitual, value);
                }
            }
            return true;
        }

        if (!supportedRituals.contains(normalized)) {
            return false;
        }

        return applySingleRitual(player, pathway, sequence, normalized, value);
    }

    private static boolean applySingleRitual(ServerPlayer player, String pathway, int sequence, String normalizedRitual, boolean value) {
        CompoundTag ritualTag = getRitualTag(player);

        if ("error".equals(pathway) && sequence == 3) {
            return switch (normalizedRitual) {
                case "raid_plains" -> setBool(ritualTag, ERROR3_RAID_PLAINS, value);
                case "raid_taiga" -> setBool(ritualTag, ERROR3_RAID_TAIGA, value);
                case "raid_savanna" -> setBool(ritualTag, ERROR3_RAID_SAVANNA, value);
                default -> false;
            };
        }

        if ("door".equals(pathway) && sequence == 3) {
            return switch (normalizedRitual) {
                case "visit_overworld" -> setBool(ritualTag, DOOR3_VISIT_OVERWORLD, value);
                case "visit_nether" -> setBool(ritualTag, DOOR3_VISIT_NETHER, value);
                case "visit_end" -> setBool(ritualTag, DOOR3_VISIT_END, value);
                case "has_elytra" -> setBool(ritualTag, DOOR3_OVERRIDE_ELYTRA, value);
                case "has_wither_skull" -> setBool(ritualTag, DOOR3_OVERRIDE_WITHER_SKULL, value);
                case "has_diamond_block" -> setBool(ritualTag, DOOR3_OVERRIDE_DIAMOND_BLOCK, value);
                case "drink_in_deep_dark" -> setBool(ritualTag, DOOR3_OVERRIDE_DEEP_DARK, value);
                default -> false;
            };
        }

        if ("fool".equals(pathway) && sequence == 3) {
            return switch (normalizedRitual) {
                case "end_5_days" -> {
                    if (value) {
                        ritualTag.putLong(FOOL3_END_STAY_TICKS, SCHOLAR_REQUIRED_TICKS_IN_END);
                        ritualTag.putLong(FOOL3_READY_UNTIL, player.level().getGameTime() + SCHOLAR_WINDOW_TICKS);
                    } else {
                        ritualTag.putLong(FOOL3_END_STAY_TICKS, 0L);
                        ritualTag.putLong(FOOL3_READY_UNTIL, 0L);
                    }
                    yield true;
                }
                case "scholar_window_open" -> {
                    ritualTag.putLong(FOOL3_READY_UNTIL, value ? player.level().getGameTime() + SCHOLAR_WINDOW_TICKS : 0L);
                    yield true;
                }
                default -> false;
            };
        }

        return false;
    }

    public static Set<String> getSupportedRituals(String pathway, int sequence) {
        if ("error".equals(pathway) && sequence == 3) {
            return Set.of("all", "raid_plains", "raid_taiga", "raid_savanna");
        }
        if ("door".equals(pathway) && sequence == 3) {
            return Set.of("all", "visit_overworld", "visit_nether", "visit_end", "has_elytra", "has_wither_skull", "has_diamond_block", "drink_in_deep_dark");
        }
        if ("fool".equals(pathway) && sequence == 3) {
            return Set.of("all", "end_5_days", "scholar_window_open");
        }
        return new HashSet<>();
    }

    private static boolean isPlainsBiome(net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biome) {
        return biome.is(Biomes.PLAINS) || biome.is(Biomes.SUNFLOWER_PLAINS) || biome.is(Biomes.SNOWY_PLAINS);
    }

    private static boolean isTaigaBiome(net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biome) {
        return biome.is(Biomes.TAIGA)
                || biome.is(Biomes.SNOWY_TAIGA)
                || biome.is(Biomes.OLD_GROWTH_PINE_TAIGA)
                || biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
    }

    private static boolean isSavannaBiome(net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biome) {
        return biome.is(Biomes.SAVANNA)
                || biome.is(Biomes.SAVANNA_PLATEAU)
                || biome.is(Biomes.WINDSWEPT_SAVANNA);
    }

    private static boolean hasItem(ServerPlayer player, Item item, int amount) {
        int found = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                found += stack.getCount();
                if (found >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean setBool(CompoundTag tag, String key, boolean value) {
        tag.putBoolean(key, value);
        return true;
    }

    private static CompoundTag getRitualTag(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(ROOT)) {
            data.put(ROOT, new CompoundTag());
        }
        return data.getCompound(ROOT);
    }
}
