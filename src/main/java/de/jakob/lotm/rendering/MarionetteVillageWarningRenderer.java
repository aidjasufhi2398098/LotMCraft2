package de.jakob.lotm.rendering;

import de.jakob.lotm.LOTMCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = LOTMCraft.MOD_ID, value = Dist.CLIENT)
public class MarionetteVillageWarningRenderer {

    private static long warningEndMillis = 0L;

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, ResourceLocation.fromNamespaceAndPath(LOTMCraft.MOD_ID, "marionette_village_warning"), (guiGraphics, deltaTracker) -> renderOverlay(guiGraphics));
    }

    public static void setWarningTicks(int ticks) {
        long durationMs = Math.max(0, ticks) * 50L;
        warningEndMillis = System.currentTimeMillis() + durationMs;
    }

    private static void renderOverlay(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || warningEndMillis <= System.currentTimeMillis()) {
            return;
        }

        int remainingTicks = (int) Math.ceil((warningEndMillis - System.currentTimeMillis()) / 50.0);
        int remainingSeconds = Math.max(0, (int) Math.ceil(remainingTicks / 20.0));

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        Component title = Component.translatable("lotm.marionette_village.warning");
        float titleScale = 2.2f;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(titleScale, titleScale, 1.0f);
        int scaledX = (int) ((screenWidth / 2f) / titleScale);
        int scaledY = (int) ((screenHeight / 3f) / titleScale);
        guiGraphics.drawCenteredString(mc.font, title, scaledX, scaledY, 0xFFFF0000);
        guiGraphics.pose().popPose();

        Component cooldown = Component.literal(remainingSeconds + "s");
        int cooldownY = screenHeight - 49;
        guiGraphics.drawCenteredString(mc.font, cooldown, screenWidth / 2, cooldownY, 0xFFFF2A2A);
    }
}
