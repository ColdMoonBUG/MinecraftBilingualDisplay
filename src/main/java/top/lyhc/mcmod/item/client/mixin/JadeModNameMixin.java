// 文件路径: src/main/java/top/lyhc/mcmod/item/client/mixin/JadeModNameMixin.java
package top.lyhc.mcmod.item.client.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.addon.core.ModNameProvider;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = ModNameProvider.class, remap = false)
public class JadeModNameMixin {

    private static final Map<String, String> MOD_NAME_TRANSLATIONS = new HashMap<>();

    static {
        MOD_NAME_TRANSLATIONS.put("Minecraft", "我的世界");
        MOD_NAME_TRANSLATIONS.put("Jade", "玉");
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void translationtheitem_finalInject(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config, CallbackInfo ci) {
        try {
            // 所有诊断步骤都已验证通过，现在我们直接执行最终逻辑
            Identifier blockId = Registries.BLOCK.getId(accessor.getBlock());
            String modId = blockId.getNamespace();
            String originalModName = FabricLoader.getInstance()
                    .getModContainer(modId)
                    .map(modContainer -> modContainer.getMetadata().getName())
                    .orElse(modId);

            String translatedName = MOD_NAME_TRANSLATIONS.get(originalModName);

            if (translatedName != null) {
                // 如果找到翻译，就添加中文名并取消原方法
                tooltip.add(Text.literal(translatedName).formatted(Formatting.BLUE, Formatting.ITALIC));
                ci.cancel();
            }
            // 如果没找到翻译，就什么也不做，让Jade自己显示英文名
        } catch (Exception e) {
            // 保留一个空的catch块，作为最后一道保险，防止任何意外情况导致游戏崩溃
        }
    }
}