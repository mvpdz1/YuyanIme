# 369VR输入法
369VR输入法基于非常优秀的开源输入法雨燕输入法(https://github.com/gurecn/YuyanIme/)实现的，非常感谢雨燕输入法，非常的好用，避免了我们重复造轮子，还造不出这么好用的输入法！ 369VR输入法，是首个专门为Quest系统开发的专用输入法，解决了Quest系统上长期以来没有中文输入法的问题，这是一段里程碑式的胜利，目前有两种模式，模式一：为悬浮输入法，通过悬浮框绕过系统对输入法的限制，缺点其他应用不显示，只显示输入法界面，部分输入框不兼容，模式二：通过键盘消息，不调起输入法，而是调起自定义键盘页面，绕过系统限制，自己处理键盘输入，这种模式除了弹出来的键盘位置不对之外，其他完美适配，默认使用这种模式！
以上2个思路皆为我们原创思路，经过不断的调试研究，才确定了这两个方向，有人抄袭我们的思路，非但不感恩，还敢说自己是原创，然后抹黑造谣我们，简直不要脸，恶心到家，不配为人，生孩子没*眼！
新增支持语音输入功能
## 安装使用：
点击[Releases](https://github.com/mvpdz1/369VRshurufa)，下载最新版本安装包直接安装使用即可。 
使用过程中任何问题可以创建issues、应用内反馈或通过邮件等方式反馈，本人会根据需求及时修复。
## 官网：www.369vr.com
-------------------以下为雨燕输入法的介绍，懒得写介绍------------------------------
## 设计原则：
### 纯输入功能，主打轻快。
喜欢简洁的我看到一个个拼音输入法工具软件逐渐趋向繁杂，软件内各种眼花缭乱的无用功能以及烦人的广告让我无法忍受。  
**因此我想要定制出一款简洁、实用、好用的输入法；**
### 最小、必要的权限原则，更安全。
当前主流输入法获取各类非必要权限，无视用户隐私，随意上传、分析用户数据。虽然大数据不会区别对待，但我仍然希望自己的数据只在自己的手机里，不要在我不知情、无意识的情况下，把所有数据上传。    
**369VR输入法谨遵循必要、最小化权限，只为输入而存在，纯净、安全、更高效。**  
用户可按需下载对应版本。
### 基于Rime引擎，但更易上手。
当前开放的输入法引擎中，[Rime引擎](https://github.com/rime/librime)已经趋向完善。然后对于小白用户来说，上手却并不容易：各种输入方案定制及兼容问题，各种键盘的界面效果优化问题。  
**因此我想要定制出一款基于Rime引擎的安装即用，哪怕没时间研究也能好用的输入法；**
### 输入模式更完善。
最早接触安卓平台的[同文输入法](https://github.com/osfans)，后面接触[小企鹅输入法](https://github.com/fcitx5-android/fcitx5-android)，均采用Rime方案进行定制，在输入层面已经满足大部分需求。但是小企鹅输入法九宫格键盘不支持，同文输入法候选词选择不便且无法选择拼音组合，使用起来确实需要勇气。  
369VR输入法内置多套优秀词库，优化Rime九宫输入方案、乱序输入方案，支持绝大部分输出场景，提升输入效率。  
**因此我想定制出一款支持对小白用户来说使用更普及的九宫格，同时结合全键、双拼、手写、语音等多种方案的输入法。**  
### 个性化定制更贴心。
手机屏幕越来越大，但是在走路时，一手提东西，一手打字回复消息对我来说是个头疼地问题，选择候选词够不到、选择出错屡屡出现，因此我定制了单手模式、悬浮键盘。  
输入数字要么切换到数字键盘，要么长按按键输入，对输入来说都不便捷，因此我定制了键盘数字行。  
夜间输入时，屏幕刺眼，因此我定制了深色主题自动切换功能。更多贴心定制项正在进行中。

## 实现功能：
+ 方案内置：全拼（九宫格、全键）、双拼(小鹤、智能ABC、自然码、紫光、微软、搜狗、乱序17)、手写、五笔画；支持简拼、全拼；
+ 英文输入：智能全键英文输入；
+ 词库拓展：支持雾凇词库、白霜词库等多种词库拓展，输入体验良好；
+ 符号输入：中文、英文、数学、颜文字、EMOJI表情输入、微信特效表情；
+ 数字输入：数字键盘输入、键盘数字行输入； 
+ 键盘自定义：自定义菜单栏、主题、深色模式、键盘调节、键盘数字行、键盘位置移动； 
+ 单手键盘：左、右手模式切换；
+ 悬浮键盘：悬浮键盘模式，键盘拖拽、移动；
+ 花漾字输入：火星文（焱暒妏）、 花藤字（ζั͡花ั͡藤ั͡字ั͡✾）、凌乱字（"҉҉҉凌҉҉҉乱҉҉҉字҉҉҉）、发芽字（发ོ芽ོ字ོ）、雾霾字（҈҈҈҈雾҈҈҈҈霾҈҈҈҈字҈҈҈҈）、禁止查看（禁⃠止⃠查⃠看⃠）、长草字（"҈长҉҉҈草҉҉҈字҉）、起风了（=͟͟͞͞风=͟͟͞͞太=͟͟͞͞大=͟͟͞͞）花漾输入； 
+ 拼音输入扩展：支持繁体、简体，支持中英文混输，支持表情描述输入；
+ 剪切板：支持剪切板联想显示、剪切板及清空操作；
+ 常用语：支持自定义常用语、常用语快捷输入、编辑、删除等操作；
+ 全面屏键盘优化：支持全面屏键盘优化导航栏功能；
+ 隐藏输入法图标：支持隐藏输入法图标功能。

## 已知问题：
* 小米手机中键盘菜单点击设置等无反应:  
  由于小米手机中键盘跳转应用界面需借助`后台弹出界面`权限，该权限需用户手动开启：设置-应用管理-369VR输入法-权限管理-开启`后台弹出界面`权限即可。
* 三星手机按键音量调节无效:  
  369VR输入法使用系统`通知`音量作为按键默认音量，但不同手机表现不同。输入法会以手机系统音量设置为前提，当手机静音时，无输入法按键音。当手机未静音时，以`通知`音量大小为基准进行调节。在三星手机中，基于`系统`音量大小进行调解。
* 在输入一半内容时切换横竖屏，较大概率导致横屏模式屏幕触摸无效，仅能点击键盘按键。
  临时方案：切换横竖屏前，确保输入框内容为空。

## 开发环境：
> Android SDK: minSdk 23, [app/build.gradle](./app/build.gradle)  
> 第三方库: [build.gradle](./build.gradle)  
> JDK: OpenJDK version "17.0.11" 2024-04-16

## 构建项目：
### 1. 克隆此项目并拉取所有子模块。
```sh
git clone git@github.com:gurecn/YuyanIme.git
git submodule update --init --recursive
```
### 2. 导入Android Studio
建议使用最新、稳定版本，本人使用`Android Studio Iguana | 2023.2.1 Patch 1`版本，按照常规项目导入即可，`Android Studio`会自动安装并配置 Android 开发环境。

## 键盘预览：
| 九宫键盘 | 全拼键盘 | 乱序17 |
| - | - | - |
| ![九宫格拼音键盘](./images/t9_pinyin.jpg) | ![全键拼音键盘](./images/qwerty_pinyin.jpg) | ![乱序17拼音](./images/double_lx17.jpg) |

| 双拼键盘 | 笔画键盘 | 手写键盘 |
| - | - | - |
| ![双拼键盘](./images/double_pinyin.jpg) | ![笔画键盘](./images/stroke_pinyin.jpg) | ![手写键盘](./images/writing_pinyin.jpg) |

| 英语键盘 | 数字键盘 | 编辑键盘 |
| - | - | - |
| ![英语键盘](./images/qwerty.jpg) |  ![数字键盘](./images/number.jpg) | ![编辑键盘](./images/textedit.jpg) |

| 剪切板 | 单手键盘 | 悬浮键盘 |
| - | - | - |
| ![剪切板](./images/clipboard.jpg) | ![单手键盘](./images/onehand.jpg) | ![悬浮键盘](./images/float.jpg) |

| 表情键盘 | 微信特效 | 数字行 |
| - | - | - |
| ![表情键盘](./images/emoji.jpg) | ![微信特效](./images/emoji_wechat.jpg) | ![数字行](./images/number_line.jpg) |

| 深色主题 | 设置菜单 |
| - | - |
| ![深色主题](./images/dark.jpg) | ![设置菜单](./images/setting.jpg) |

## 鸣谢：
感谢以下优秀的开源社区贡献：
-[雨燕输入法]https://github.com/gurecn/YuyanIme
- [RIME](http://rime.im)
- [同文输入法](https://github.com/osfans)
- [小企鹅输入法](https://github.com/fcitx5-android/fcitx5-android)
- [雾凇拼音](https://github.com/iDvel/rime-ice)
- [白霜拼音](https://github.com/gaboolic/rime-frost)




