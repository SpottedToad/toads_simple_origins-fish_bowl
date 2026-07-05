package net.spottedtoad.toads_simple_origins;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.block.entity.EmptyFishBowlBlockEntityRenderer;
import net.spottedtoad.toads_simple_origins.block.entity.FilledFishBowlBlockEntityRenderer;
import net.spottedtoad.toads_simple_origins.block.entity.ModBlockEntities;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import net.spottedtoad.toads_simple_origins.item.armor.EmptyFishBowlRenderer;
import net.spottedtoad.toads_simple_origins.item.armor.FilledFishBowlRenderer;

public class TSOModClient implements ClientModInitializer {
    //Register texture path
    private static final Identifier OXYGEN_TEXTURE = Identifier.of("toads_simple_origins", "textures/gui/oxygen_meter.png");

    @Override
    public void onInitializeClient() {

        //Register armor renderers on initialize
        AzArmorRendererRegistry.register(EmptyFishBowlRenderer::new,
                ModItems.EMPTY_FISH_BOWL);
        AzArmorRendererRegistry.register(FilledFishBowlRenderer::new,
                ModItems.FILLED_FISH_BOWL);

        //Register block entity renderers on initialize
        BlockEntityRendererFactories.register(
                ModBlockEntities.EMPTY_FISH_BOWL_BLOCK_ENTITY,
                ctx -> new EmptyFishBowlBlockEntityRenderer());
        BlockEntityRendererFactories.register(
                ModBlockEntities.FILLED_FISH_BOWL_BLOCK_ENTITY,
                ctx -> new FilledFishBowlBlockEntityRenderer());


        //Register hud element for fish bowl
        HudRenderCallback.EVENT.register((drawContext, deltaTracker) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            //Disable hud element when loading, dead, or in a menu
            if (client.player == null || client.world == null || client.options.hudHidden) {
                return;
            }
            //Display when wearing armor
            ItemStack helmet = client.player.getEquippedStack(EquipmentSlot.HEAD);
            if (helmet.isEmpty() || !helmet.getRegistryEntry().getKey().map(key -> key.getValue().toString()).orElse("").equals("toads_simple_origins:filled_fish_bowl")) {
                return;
            }
            //Read oxygen nbt data and convert to percentage
            NbtComponent nbtComponent = helmet.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
            NbtCompound nbt = nbtComponent.copyNbt();
            int maxOxygen = 1200;
            int oxygen = nbt.contains("oxygenLevel") ? nbt.getInt("oxygenLevel") : maxOxygen;

            //Set pixel size of meter drain with 16x16 texture space
            float ratio = (float) oxygen / maxOxygen;
            int pixelHeight = (int) (16 * ratio);

            //Set hud element position with left and top edges
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();
            int drawX = (screenWidth / 2) - 8;
            int drawY = screenHeight - 53;

            //Place bottom texture from left side of 32x16 texture
            drawContext.drawTexture(
                    OXYGEN_TEXTURE,
                    drawX, drawY,
                    0, 0,
                    16, 16,
                    32, 16
            );

            //Place top texture from right side of 32x16 texture and remove texture as oxygen drains
            int missingHeight = 16 - pixelHeight;
            if (pixelHeight > 0) {
                drawContext.drawTexture(
                        OXYGEN_TEXTURE,
                        drawX, drawY + missingHeight,
                        16, (float) missingHeight,
                        16, pixelHeight,
                        32, 16
                );
            }
        });
    }
}
