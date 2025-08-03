# TranslationTheItem - 一个用于语言学习的Minecraft辅助Mod

你好！这是一个非常简单的小工具，旨在帮助正在学习一门新语言（尤其是英语/中文）的Minecraft玩家。

它的核心功能很简单：在游戏中，当你指向一个方块、一个实体，或者在背包里悬停在一个物品上时，它会**同时显示该物品的原文名称和你的母语翻译**，从而在潜移默化中帮助你记忆单词。

## ✨ 主要功能

* **实时双语显示 (HUD):** 当你的准星指向世界中的一个方块或实体时，会在屏幕上显示它的中英文名称。
* **手持物品提示:** 当你切换手中的物品且准星无目标时，会在屏幕下方短暂显示该物品的中英文名称，随后自动消失。
* **GUI内翻译:** 在背包、箱子、工作台等任何界面中，当你的鼠标指针悬停在一个物品上时，弹出的信息框(Tooltip)里会自动加入中文翻译。
* **智能环境适应:**
    * **独立运行:** 在没有安装Jade Mod的情况下，本Mod会启用自带的HUD来显示所有信息。
    * **与Jade共存:** 当检测到 [Jade](https://www.curseforge.com/minecraft/mc-mods/jade) Mod存在时，本Mod会自动禁用自己的HUD，以避免任何UI冲突。同时，它会通过精巧的方式，将自己的翻译功能**无缝集成**到Jade的用户界面中。
![图片](https://github.com/ColdMoonBUG/PictureBed/raw/main/Hand.png)
![图片](https://github.com/ColdMoonBUG/PictureBed/blob/main/GUI.png)
## ⚙️ 使用环境

* **Minecraft版本:** `1.20.1`
* **Mod加载器:** `Fabric Loader` (需要 `0.16.14` 或更高版本)
* **前置依赖:**
    * [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) (必需)
    * [Jade](https://www.curseforge.com/minecraft/mc-mods/jade) (强烈建议！为获得最佳体验，请务必一同安装)

## 🙏 对Jade的致敬

我们必须在此向伟大的 **Jade** 及其所有开发者致以最崇高的敬意。

`TranslationTheItem` **不是，也永远不会是Jade的替代品。** 相反，我们是Jade的一个卑微的“学习伴侣”插件。Jade以其无与伦比的强大功能、稳定性和生态兼容性，早已成为现代Mod整合包中不可或缺的基石。

我们花费了大量的精力，不是去“替换”Jade，而是去“融入”它。我们最高级的特性（Mixin Hook），其唯一目的就是在不干扰Jade任何原生功能的前提下，将我们的翻译信息，小心翼翼地、无缝地呈现在它那精美专业的UI之中。

感谢Jade为社区提供了如此强大的平台。

## 💬 一些心里话

你好，我是这个Mod的作者。坦白说，我只是一个Mod开发领域的新人，这个项目源于我自己想要在玩Minecraft时背单词的一个小小想法。在开发的路上，我遇到了数不清的困难，这个Mod的诞生，本身就是一个不断学习和试错的过程。

它肯定不完美，甚至可能有些粗糙，所以我把它叫做一个“垃圾Mod”。我将它发布到GitHub，只是希望能找到一些和我有同样需求的、正在学习语言的“同好”。如果这个小工具恰好也能帮到你，那将是我最大的荣幸。

如果你有任何想法，或者发现了什么问题，请随时提出，让我们一起学习，一起进步。谢谢！

## 🛠️ 安装

1.  确保你已经安装了正确版本的Minecraft, Fabric Loader和Fabric API。
2.  (强烈建议) 安装 [Jade](https://www.curseforge.com/minecraft/mc-mods/jade) Mod。
3.  从 [Releases](https://github.com/你的用户名/你的仓库名/releases) 页面下载最新的 `.jar` 文件。
4.  将下载的 `.jar` 文件放入你游戏目录下的 `mods` 文件夹中。
5.  启动游戏！
