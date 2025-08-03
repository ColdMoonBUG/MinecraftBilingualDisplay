// 文件路径: src/main/java/top/lyhc/mcmod/item/client/ItemTooltipModifier.java
package top.lyhc.mcmod.item.client;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.text.Style;
import java.util.List;

public class ItemTooltipModifier {

    public static void register() {
        // 注册物品Tooltip回调事件
        ItemTooltipCallback.EVENT.register(ItemTooltipModifier::onItemTooltip);
    }

    private static void onItemTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (stack.isEmpty() || lines.isEmpty()) {
            return;
        }

        String translationKey = stack.getTranslationKey();
        String chineseNameString = ChineseTranslationHelper.getChineseName(translationKey);

        if (!chineseNameString.equals("未找到翻译")) {

            // 正确的做法：创建一个自定义样式（Style）对象
            // 1. .withColor(Formatting.YELLOW) 设置颜色为黄色
            // 2. .withItalic(false)       明确设置斜体为 false，以覆盖Tooltip的默认斜体效果
            Style customStyle = Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false);

            // 创建文本，并应用我们自定义的样式
            Text chineseNameText = Text.literal(chineseNameString).setStyle(customStyle);

            // 将格式化好的文本插入到正确的位置
            lines.add(1, chineseNameText);
        }
    }
}