package top.lyhc.mcmod.item.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
    public class ItemNameHud implements HudRenderCallback {

        private ItemStack lastDisplayedHeldItem = ItemStack.EMPTY;
        private long heldItemDisplayStartTime = 0;
        private static final long DISPLAY_DURATION_MS = 3000;

        @Override
        public void onHudRender(DrawContext drawContext, float tickDelta) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.currentScreen != null) {
                return;
            }

            // 在渲染的每一帧都检查Jade是否存在
            boolean isJadeLoaded = FabricLoader.getInstance().isModLoaded("jade");

            // --- 核心判断逻辑 ---
            if (isJadeLoaded) {
                // **情况一：Jade已加载**
                // 我们只负责处理手持物品的显示，把准星目标完全交给Jade
                handleHeldItemDisplay(drawContext, client);
            } else {
                // **情况二：Jade未加载**
                // 我们自己负责所有事情，恢复到我们之前的完整逻辑
                handleFullDisplay(drawContext, client);
            }
        }

        // 新增一个方法，专门用于处理Jade不存在时的完整显示逻辑
        private void handleFullDisplay(DrawContext drawContext, MinecraftClient client) {
            HitResult target = client.crosshairTarget;
            boolean isTargeting = (target != null && target.getType() != HitResult.Type.MISS);

            if (isTargeting) {
                // 瞄准时，重置手持物品的计时器，避免UI重叠
                heldItemDisplayStartTime = 0;
                lastDisplayedHeldItem = ItemStack.EMPTY;

                Text englishName = null;
                String translationKey = null;

                if (target.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) target;
                    ItemStack itemStack = new ItemStack(client.world.getBlockState(blockHitResult.getBlockPos()).getBlock());
                    englishName = itemStack.getName();
                    translationKey = itemStack.getTranslationKey();
                } else if (target.getType() == HitResult.Type.ENTITY) {
                    englishName = ((EntityHitResult) target).getEntity().getName();
                    translationKey = ((EntityHitResult) target).getEntity().getType().getTranslationKey();
                }
                int yPos = client.getWindow().getScaledHeight() / 2 - 40;
                renderTranslatedText(drawContext, client, englishName, translationKey, yPos);
            } else {
                // 准星无目标时，才处理手持物品
                handleHeldItemDisplay(drawContext, client);
            }
        }

        // 负责处理手持物品显示的方法（这个方法本身不需要大改）
        private void handleHeldItemDisplay(DrawContext drawContext, MinecraftClient client) {
            ItemStack currentHeldItem = client.player.getMainHandStack();

            if (!ItemStack.areItemsEqual(currentHeldItem, lastDisplayedHeldItem)) {
                lastDisplayedHeldItem = currentHeldItem.copy();
                if (!currentHeldItem.isEmpty()) {
                    heldItemDisplayStartTime = System.currentTimeMillis();
                } else {
                    heldItemDisplayStartTime = 0;
                }
            }

            if (heldItemDisplayStartTime > 0) {
                if (System.currentTimeMillis() - heldItemDisplayStartTime < DISPLAY_DURATION_MS) {
                    if (!lastDisplayedHeldItem.isEmpty()) {
                        Text englishName = lastDisplayedHeldItem.getName();
                        String translationKey = lastDisplayedHeldItem.getTranslationKey();
                        // 手持物品信息绘制在下方（之前是+20，稍微上移一点到+15）
                        int yPos = client.getWindow().getScaledHeight() / 2 + 15;
                        renderTranslatedText(drawContext, client, englishName, translationKey, yPos);
                    }
                } else {
                    heldItemDisplayStartTime = 0;
                }
            }
        }


        // 修改渲染方法，使其可以接收一个Y坐标
    private void renderTranslatedText(DrawContext drawContext, MinecraftClient client, Text englishName, String translationKey, int yPos) {
        if (translationKey == null || englishName == null || translationKey.isEmpty()) {
            return;
        }

        String chineseNameString = ChineseTranslationHelper.getChineseName(translationKey);
        if (chineseNameString.equals("未找到翻译")) {
            return;
        }
        Text chineseName = Text.of(chineseNameString);

        int whiteColor = 0xFFFFFF;
        int yellowColor = 0xFFFF55;
        int screenWidth = client.getWindow().getScaledWidth();

        drawContext.drawTextWithShadow(client.textRenderer, chineseName, (screenWidth - client.textRenderer.getWidth(chineseName)) / 2, yPos + 10, yellowColor);
    }

    // 注册方法保持不变
    public static void registerHudRender() {
        HudRenderCallback.EVENT.register(new ItemNameHud());
    }

}