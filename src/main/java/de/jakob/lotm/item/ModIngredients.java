package de.jakob.lotm.item;

import de.jakob.lotm.LOTMCraft;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

public class ModIngredients {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LOTMCraft.MOD_ID);

    public static final DeferredItem<Item> LAVOS_SQUID_BLOOD = registerMainIngredient("lavos_squid_blood", 9, "fool");
    public static final DeferredItem<Item> HORNACIS_GRAY_MOUNTAIN_GOAT_HORN = registerMainIngredient("hornacis_gray_mountain_goat_horn", 8, "fool");
    public static final DeferredItem<Item> ROOT_OF_MIST_TREANT = registerMainIngredient("true_root_of_mist_treant", 7, "fool");
    public static final DeferredItem<Item> THOUSAND_FACED_HUNTER_BLOOD = registerMainIngredient("thousand_faced_hunter_blood", 6, "fool");
    public static final DeferredItem<Item> ANCIENT_WRAITH_DUST = registerMainIngredient("ancient_wraith_dust", 5, "fool");
    public static final DeferredItem<Item> BIZARRO_BANE_EYE = registerMainIngredient("bizarro_bane_eye", 4, "fool");


    public static final DeferredItem<Item> CRYSTAL_SUNFLOWER = registerMainIngredient("crystal_sunflower", 9, "sun");
    public static final DeferredItem<Item> POWDER_OF_DAZZLING_SOUL = registerMainIngredient("powder_of_dazzling_soul", 8, "sun");
    public static final DeferredItem<Item> SPIRIT_PACT_TREE_FRUIT = registerMainIngredient("spirit_pact_tree_fruit", 7, "sun");
    public static final DeferredItem<Item> CRYSTALLIZED_ROOTS = registerMainIngredient("crystallized_roots", 6, "sun");
    public static final DeferredItem<Item> PURE_WHITE_BRILLIANT_ROCK = registerMainIngredient("pure_white_brilliant_rock", 5, "sun");
    public static final DeferredItem<Item> GOLDEN_BLOOD = registerMainIngredient("golden_blood", 4, "sun");
    public static final DeferredItem<Item> SUN_ORB = registerMainIngredient("sun_orb", 3, "sun");


    public static final DeferredItem<Item> ILLUSION_CRYSTAL = registerMainIngredient("illusion_crystal", 9, "door");
    public static final DeferredItem<Item> SPIRIT_EATER_STOMACH_POUCH = registerMainIngredient("spirit_eater_stomach_pouch", 8, "door");
    public static final DeferredItem<Item> METEORITE_CRYSTAL = registerMainIngredient("meteorite_crystal", 7, "door");
    public static final DeferredItem<Item> ANCIENT_WRAITH_ARTIFACT = registerMainIngredient("ancient_wraith_artifact", 6, "door");
    public static final DeferredItem<Item> SHADOWLESS_DEMONIC_WOLF_HEART = registerMainIngredient("shadowless_demonic_wolf_heart", 5, "door");
    public static final DeferredItem<Item> GOLDEN_PHOENIX_EYE = registerMainIngredient("golden_phoenix_eyes", 4, "door");
    public static final DeferredItem<Item> MIST_WATCHER_CRYSTAL = registerMainIngredient("mist_watcher_crystal", 3, "door");


    public static final DeferredItem<Item> MURLOC_BLADDER = registerMainIngredient("murloc_bladder", 9, "tyrant");
    public static final DeferredItem<Item> DRAGON_EYED_CONDOR_EYEBALL = registerMainIngredient("dragon_eyed_condor_eyeball", 8, "tyrant");
    public static final DeferredItem<Item> ANCIENT_LOGBOOK = registerMainIngredient("ancient_logbook", 7, "tyrant");
    public static final DeferredItem<Item> BLUE_SHADOW_FALCON_FEATHERS = registerMainIngredient("blue_shadow_falcon_feathers", 6, "tyrant");
    public static final DeferredItem<Item> SIREN_VOCAL_SAC = registerMainIngredient("siren_vocal_sac", 5, "tyrant");
    public static final DeferredItem<Item> WHALE_OF_PUNISHMENT_STOMACH = registerMainIngredient("whale_of_punishment_stomach", 4, "tyrant");
    public static final DeferredItem<Item> KING_OF_GREEN_WINGS_EYE = registerMainIngredient("king_of_green_wings_eye", 3, "tyrant");


    public static final DeferredItem<Item> MIDNIGHT_BEAUTY_FLOWER = registerMainIngredient("midnight_beauty_flower", 9, "darkness");
    public static final DeferredItem<Item> SOUL_SNARING_BELL_FLOWER = registerMainIngredient("soul_snaring_bell_flower", 8, "darkness");
    public static final DeferredItem<Item> DREAM_EATING_RAVEN_HEART = registerMainIngredient("dream_eating_raven_heart", 7, "darkness");
    public static final DeferredItem<Item> DEEP_SLEEPER_SKULL = registerMainIngredient("deep_sleeper_skull", 6, "darkness");
    public static final DeferredItem<Item> SOURCE_OF_MAD_DREAMS = registerMainIngredient("source_of_mad_dreams", 5, "darkness");


    public static final DeferredItem<Item> RED_CHESTNUT_FLOWER = registerMainIngredient("red_chestnut_flower", 9, "red_priest");
    public static final DeferredItem<Item> REDCROWN_BALSAM_POWDER = registerMainIngredient("redcrown_balsam_powder", 8, "red_priest");
    public static final DeferredItem<Item> MAGMA_ELF_CORE = registerMainIngredient("magma_elf_core", 7, "red_priest");
    public static final DeferredItem<Item> SPHINX_BRAIN = registerMainIngredient("sphinx_brain", 6, "red_priest");
    public static final DeferredItem<Item> BLACK_HUNTING_SPIDER_COMPOSITE_EYES = registerMainIngredient("black_hunting_spider_composite_eyes", 5, "red_priest");
    public static final DeferredItem<Item> MAGMA_GIANT_CORE = registerMainIngredient("magma_giant_core", 4, "red_priest");
    public static final DeferredItem<Item> WAR_COMET_CORE = registerMainIngredient("war_comet_core", 3, "red_priest");

    public static final DeferredItem<Item> GOAT_HORNED_BLACKFISH_BLOOD = registerMainIngredient("goat_horned_blackfish_blood", 9, "visionary");
    public static final DeferredItem<Item> RAINBOW_SALAMANDER_PITUITARY_GLAND = registerMainIngredient("rainbow_salamander_pituitary_gland", 8, "visionary");
    public static final DeferredItem<Item> TREE_OF_ELDERS_FRUIT = registerMainIngredient("tree_of_elders_fruit", 7, "visionary");
    public static final DeferredItem<Item> ILLUSORY_CHIME_TREES_FRUIT = registerMainIngredient("illusory_chime_tree_fruit", 6, "visionary");
    public static final DeferredItem<Item> DREAM_CATCHERS_HEART = registerMainIngredient("dream_catcher_heart", 5, "visionary");

    public static final DeferredItem<Item> BLACK_FEATHER_OF_MONSTER_BIRD = registerMainIngredient("black_feather_of_monster_bird", 9, "demoness");
    public static final DeferredItem<Item> ABYSS_DEMONIC_FISH_BLOOD = registerMainIngredient("abyss_demonic_fish_blood", 8, "demoness");
    public static final DeferredItem<Item> AGATE_PEACOCK_EGG = registerMainIngredient("agate_peacock_egg", 7, "demoness");
    public static final DeferredItem<Item> SUCCUBUS_EYES = registerMainIngredient("succubus_eyes", 6, "demoness");
    public static final DeferredItem<Item> SHADOW_LIZARD_SCALES = registerMainIngredient("shadow_lizard_scales", 5, "demoness");


    private static DeferredItem<Item> registerMainIngredient(String name, int sequence, String... pathways) {
        DeferredItem<Item> ingredient = ITEMS.registerItem(name,
                properties -> new PotionIngredient(properties, sequence, true, pathways),
                new Item.Properties());

        registerEnergizedIngredient(name, sequence, pathways);
        return ingredient;
    }

    private static void registerEnergizedIngredient(String baseName, int sequence, String... pathways) {
        ITEMS.registerItem(baseName + "_energized",
                properties -> new EnergizedPotionIngredientItem(properties, sequence, pathways),
                new Item.Properties().food(EnergizedPotionIngredientItem.defaultFoodProperties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static List<PotionIngredient> getAllOfPathway(String pathway) {
        return ITEMS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(i -> i instanceof PotionIngredient)
                .map(i -> ((PotionIngredient) i))
                .filter(i -> i.getPathways() != null && Arrays.asList(i.getPathways()).contains(pathway))
                .toList();
    }

    public static List<PotionIngredient> getAll() {
        return ITEMS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(i -> i instanceof PotionIngredient)
                .map(i -> ((PotionIngredient) i))
                .toList();
    }


    public static PotionIngredient selectRandomIngredient(Random random) {
        List<PotionIngredient> ingredients = ITEMS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(i -> i instanceof PotionIngredient)
                .map(i -> ((PotionIngredient) i))
                .toList();

        if (ingredients.isEmpty()) {
            return null;
        }

        // Calculate weights for each potion
        // Higher sequence = more common = higher weight
        // Weight formula: sequence + 1 makes sequence 9 -> weight 10, sequence 0 -> weight 1
        Map<PotionIngredient, Integer> weights = new HashMap<>();
        int totalWeight = 0;

        for (PotionIngredient ingredient : ingredients) {
            int weight = ingredient.getSequence() + 1; // Higher sequence = more common = higher weight
            weights.put(ingredient, weight);
            totalWeight += weight;
        }

        // Generate random number between 0 and totalWeight-1
        int randomValue = random.nextInt(totalWeight);

        // Find the selected potion based on cumulative weights
        int cumulativeWeight = 0;
        for (Map.Entry<PotionIngredient, Integer> entry : weights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue < cumulativeWeight) {
                return entry.getKey();
            }
        }

        // Fallback (should never reach here with valid input)
        return ingredients.get(ingredients.size() - 1);
    }

    public static PotionIngredient selectRandomIngredientOfPathway(Random random, String pathway) {
        List<PotionIngredient> ingredients = ITEMS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(i -> i instanceof PotionIngredient)
                .map(i -> ((PotionIngredient) i))
                .filter(i -> i.getPathways() != null && Arrays.asList(i.getPathways()).contains(pathway))
                .toList();

        if (ingredients.isEmpty()) {
            return null;
        }

        // Calculate weights for each potion
        // Higher sequence = more common = higher weight
        // Weight formula: sequence + 1 makes sequence 9 -> weight 10, sequence 0 -> weight 1
        Map<PotionIngredient, Integer> weights = new HashMap<>();
        int totalWeight = 0;

        for (PotionIngredient ingredient : ingredients) {
            int weight = ingredient.getSequence() + 1; // Higher sequence = more common = higher weight
            weights.put(ingredient, weight);
            totalWeight += weight;
        }

        // Generate random number between 0 and totalWeight-1
        int randomValue = random.nextInt(totalWeight);

        // Find the selected potion based on cumulative weights
        int cumulativeWeight = 0;
        for (Map.Entry<PotionIngredient, Integer> entry : weights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue < cumulativeWeight) {
                return entry.getKey();
            }
        }

        // Fallback (should never reach here with valid input)
        return ingredients.get(ingredients.size() - 1);
    }

    public static PotionIngredient selectRandomIngredientOfPathwayAndSequence(Random random, String pathway, int sequence) {
        List<PotionIngredient> ingredients = ITEMS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(i -> i instanceof PotionIngredient)
                .map(i -> ((PotionIngredient) i))
                .filter(i -> i.getPathways() != null && Arrays.asList(i.getPathways()).contains(pathway))
                .filter(i -> i.getSequence() == sequence)
                .toList();

        if (ingredients.isEmpty()) {
            return null;
        }

        return ingredients.get(random.nextInt(ingredients.size()));
    }
}

