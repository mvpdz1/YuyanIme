# Quest VR模式使用说明

## 概述

本输入法现在支持两种显示模式：
1. **传统悬浮窗模式**：使用悬浮窗显示键盘界面
2. **Quest VR模式**：使用独立Activity作为键盘界面，专为VR设备优化

## Quest VR模式特性

### 核心技术要点

1. **独立Activity键盘界面**
   - 使用`QuestKeyboardActivity`作为独立的键盘界面
   - 设置特殊窗口标志防止抢占焦点
   - 保持原输入框的InputConnection有效

2. **调用者追踪机制**
   - `KeyboardCallerTracker`记录调用输入法的应用信息
   - 支持跨Activity的输入操作
   - 自动过滤自身应用，防止循环调用

3. **实时输入功能**
   - 立即预览更新，用户立即看到反馈
   - 延迟执行实际输入，给系统处理时间
   - 使用保存的InputConnection避免焦点问题

## 使用方法

### 1. 启用Quest模式

在输入法设置中：
1. 打开"键盘设置"
2. 找到"Quest VR模式"分类
3. 启用"启用Quest模式"选项

### 2. 模式切换

- **启用Quest模式**：输入法将使用独立Activity显示键盘
- **禁用Quest模式**：输入法将使用传统悬浮窗模式

### 3. 权限要求

Quest模式需要以下权限：
- 悬浮窗权限（用于显示独立Activity）
- 输入法服务权限

## 技术实现

### 架构组件

1. **QuestKeyboardActivity**
   - 独立的键盘Activity
   - 不抢占焦点的窗口设置
   - 复用现有InputView界面

2. **KeyboardCallerTracker**
   - 单例模式的调用者追踪器
   - 记录调用者包名和InputConnection
   - 提供跨Activity输入操作

3. **ImeService集成**
   - 在输入方法中检测Quest模式
   - 自动切换输入处理逻辑
   - 支持两种模式的无缝切换

### 关键代码位置

```
yuyansdk/src/main/java/com/yuyan/imemodule/quest/
├── QuestKeyboardActivity.kt          # 独立键盘Activity
├── KeyboardCallerTracker.kt          # 调用者追踪器
├── QuestImeServiceAdapter.kt         # 输入服务适配器
├── QuestInputInterface.kt            # 输入接口定义
└── QuestModeTest.kt                  # 测试工具
```

### 配置文件

- `AppPrefs.kt`: 添加Quest模式配置选项
- `strings.xml`: Quest模式相关字符串资源
- `AndroidManifest.xml`: QuestKeyboardActivity注册

## 故障排除

### 常见问题

1. **Quest模式无法启动**
   - 检查悬浮窗权限是否已授予
   - 确认输入法服务是否正常运行
   - 查看日志中的错误信息

2. **主题错误 (Theme.AppCompat required)**
   - 已修复：使用自定义`Theme.QuestKeyboardActivity`主题
   - 基于`Theme.AppCompat.Light.NoActionBar`，支持透明背景
   - 如遇到主题问题，检查AndroidManifest.xml中的主题设置

3. **窗口类型错误 (Window type can not be changed)**
   - 已修复：调整窗口标志设置时机
   - 在`super.onCreate()`之前设置窗口标志
   - 移除动态窗口类型设置，依赖主题配置

4. **键盘高度显示不全**
   - 已修复：添加Quest模式专用的环境参数设置
   - 键盘占满整个Activity界面（85%键盘 + 15%候选词）
   - 使用`EnvironmentSingleton.initQuestModeData()`方法

4. **输入无响应**
   - 检查KeyboardCallerTracker是否正确记录调用者
   - 确认InputConnection是否有效
   - 查看Quest模式是否正确启用

5. **模式切换失败**
   - 重启输入法服务
   - 清除应用缓存
   - 重新设置输入法

### 调试工具

使用`QuestModeTest`进行功能验证：

```kotlin
// 运行所有测试
QuestModeTest.runAllTests()

// 验证集成
val isValid = QuestModeTest.validateQuestModeIntegration()
```

### 日志标签

监控以下日志标签：
- `QuestKeyboardActivity`: Activity相关日志
- `KeyboardCallerTracker`: 调用者追踪日志
- `QuestImeServiceAdapter`: 输入适配器日志
- `ImeService`: 输入法服务日志

## 开发说明

### 添加新功能

1. 在`QuestInputInterface`中定义新的输入操作
2. 在`QuestImeServiceAdapter`中实现具体逻辑
3. 在`ImeService`中添加Quest模式的处理分支

### 测试建议

1. 测试两种模式的切换
2. 验证不同应用中的输入功能
3. 检查内存泄漏和性能影响
4. 测试异常情况的处理

## 版本历史

- v1.0: 初始Quest VR模式实现
  - 独立Activity键盘界面
  - 调用者追踪机制
  - 实时输入功能
  - 模式切换设置

## 技术支持

如遇到问题，请：
1. 查看日志输出
2. 运行测试工具验证
3. 检查配置是否正确
4. 参考故障排除指南
