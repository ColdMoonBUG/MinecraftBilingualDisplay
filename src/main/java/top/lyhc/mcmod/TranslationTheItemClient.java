// 文件路径: src/main/java/top/lyhc/mcmod/item/client/TranslationTheItemClient.java
package top.lyhc.mcmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import top.lyhc.mcmod.item.client.ItemNameHud;
import top.lyhc.mcmod.item.client.ItemTooltipModifier;

// 我们不再需要 FabricLoader 的 import，因为我们的HUD现在总是注册
// import net.fabricmc.loader.api.FabricLoader;

public class TranslationTheItemClient implements ClientModInitializer {

    private static boolean isInitialized = false;

    @Override
    public void onInitializeClient() {
        // 依然使用最稳妥的 ClientTickEvents 来进行初始化
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isInitialized) {
                initialize();
                isInitialized = true;
            }
        });
    }

    /**
     * 这是我们所有客户端功能的最终注册入口
     */
    private void initialize() {
        // 1. 无条件注册我们的HUD渲染器。
        //    HUD内部的逻辑 (ItemNameHud.java) 会自己判断Jade是否存在，
        //    并决定是只显示手持物品，还是显示全部信息。
        ItemNameHud.registerHudRender();

        // 2. 注册我们的GUI悬停提示功能。
        ItemTooltipModifier.register();

        // 3. ItemNameHud.registerResourceListener() 已被彻底删除，不再需要调用。
    }
}