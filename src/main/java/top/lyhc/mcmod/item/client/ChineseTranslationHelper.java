// 文件路径: src/main/java/top/lyhc/mcmod/item/client/ChineseTranslationHelper.java
package top.lyhc.mcmod.item.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.ResourceManager;

import java.util.Collections;

public class ChineseTranslationHelper {

    // 1. 我们创建一个静态变量来“缓存”我们的中文词典
    //    这样我们就不需要每次都重新加载它，只在第一次查询时加载一次。
    private static TranslationStorage chineseTranslationStorage = null;

    public static String getChineseName(String translationKey) {
        if (translationKey == null || translationKey.isEmpty()) {
            return "未找到翻译";
        }

        try {
            // 2. 检查我们的词典是否已经被加载过
            if (chineseTranslationStorage == null) {
                System.out.println(">>> ChineseTranslationHelper: 首次加载中文词典...");
                // a. 获取游戏当前的资源管理器
                ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();
                // b. 调用游戏自身的静态加载方法，只加载“zh_cn”语言
                chineseTranslationStorage = TranslationStorage.load(resourceManager, Collections.singletonList("zh_cn"), false);
                System.out.println(">>> ChineseTranslationHelper: 中文词典加载成功！");
            }

            // 3. 使用这个加载好的词典来查找翻译
            return chineseTranslationStorage.get(translationKey, "未找到翻译");

        } catch (Exception e) {
            System.err.println("!!!!!! ChineseTranslationHelper: 在加载或查询中文翻译时发生严重错误 !!!!!!");
            e.printStackTrace();
            // 如果发生任何错误，提供一个明确的错误提示，并将词典重置以便下次重试
            chineseTranslationStorage = null;
            return "翻译出错";
        }
    }
}