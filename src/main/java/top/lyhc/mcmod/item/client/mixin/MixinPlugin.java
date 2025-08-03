// 文件路径: src/main/java/top/lyhc/mcmod/item/client/mixin/MixinPlugin.java
package top.lyhc.mcmod.item.client.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // 这是核心逻辑：只有当检测到Jade已加载时，我们才允许应用Mixin
        return FabricLoader.getInstance().isModLoaded("jade");
    }

    // --- 下面的方法我们用不到，但必须实现，保持为空即可 ---
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}