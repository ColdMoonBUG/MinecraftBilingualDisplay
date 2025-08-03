// 文件路径: src/main/java/top/lyhc/mcmod/item/client/JadePlugin.java
package top.lyhc.mcmod.item.client;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadePlugin implements IWailaPlugin, IBlockComponentProvider, IEntityComponentProvider {

    public JadePlugin() {}

    private static final Style CHINESE_STYLE = Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false);
    private static final Identifier TRANSLATION_PROVIDER_ID = new Identifier("translationtheitem", "chinese_translation_providers");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(this, Block.class);
        registration.registerEntityComponent(this, Entity.class);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        System.out.println(">>> JadePlugin: appendTooltip(Block) 被调用!"); // 步骤1：确认方法被调用
        try {
            System.out.println(">>> JadePlugin: 正在创建ItemStack...");
            ItemStack stack = new ItemStack(accessor.getBlock());
            System.out.println(">>> JadePlugin: ItemStack创建成功 -> " + stack.toString()); // 步骤2

            if (!stack.isEmpty()) {
                System.out.println(">>> JadePlugin: 正在获取TranslationKey...");
                String translationKey = stack.getTranslationKey();
                System.out.println(">>> JadePlugin: TranslationKey获取成功 -> " + translationKey); // 步骤3

                System.out.println(">>> JadePlugin: 正在查询中文名...");
                String chineseName = ChineseTranslationHelper.getChineseName(translationKey);
                System.out.println(">>> JadePlugin: 中文名查询成功 -> " + chineseName); // 步骤4

                if (!chineseName.equals("未找到翻译")) {
                    System.out.println(">>> JadePlugin: 准备向Tooltip添加中文名...");
                    tooltip.add(Text.literal(chineseName).setStyle(CHINESE_STYLE));
                    System.out.println(">>> JadePlugin: 中文名添加成功!"); // 步骤5
                }
            }
        } catch (Throwable t) { // 使用Throwable可以捕获包括Error在内的所有异常
            System.err.println("!!!!!! JadePlugin: 在处理方块时发生严重崩溃 !!!!!!"); // 步骤X：捕获崩溃
            t.printStackTrace(); // 打印完整的错误堆栈
        }
    }

    // 我们暂时只诊断方块，实体的逻辑先保持原样
    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        addTranslation(tooltip, accessor.getEntity().getType().getTranslationKey());
    }

    private void addTranslation(ITooltip tooltip, String translationKey) {
        if (translationKey == null || translationKey.isEmpty()) {
            return;
        }
        String chineseName = ChineseTranslationHelper.getChineseName(translationKey);
        if (!chineseName.equals("未找到翻译")) {
            tooltip.add(Text.literal(chineseName).setStyle(CHINESE_STYLE));
        }
    }

    @Override
    public Identifier getUid() {
        return TRANSLATION_PROVIDER_ID;
    }
}