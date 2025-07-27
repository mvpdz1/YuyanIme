# Quest系统专用输入法

## 概述
本输入法专为Quest系统设计，实现了真正的系统级悬浮键盘，解决Quest系统中键盘显示不正常的问题。保持原有键盘的所有功能和外观，仅改变显示方式为悬浮窗口。

## 修改内容

### 1. AndroidManifest.xml
- 添加了悬浮窗口权限 `SYSTEM_ALERT_WINDOW`
- 添加了设备信息检测权限 `READ_PHONE_STATE`

### 2. BaseApplication.kt
- 移除了设备检测功能，默认就是Quest系统
- 在应用启动时自动启用悬浮键盘模式
- 设置了Quest系统的优化配置

### 3. ImeService.kt
- 实现了真正的系统级悬浮窗功能
- 保持原有键盘的所有功能和外观
- 添加了悬浮窗权限检查和管理
- 修改了窗口显示逻辑以支持悬浮模式

### 4. EnvironmentSingleton.kt
- 强制启用悬浮键盘模式
- 移除了悬浮模式的开关功能

### 3. quest_config.xml
- 创建了Quest系统的配置文件
- 定义了悬浮键盘的默认参数

## 核心功能
本输入法专为Quest系统设计，实现了以下功能：
- **真正的系统级悬浮窗**：使用WindowManager创建悬浮窗口
- **保持原有功能**：键盘的所有功能和外观完全保持不变
- **自动权限管理**：自动检查和处理悬浮窗权限
- **Quest优化**：专为VR环境优化的显示效果
- **无缝集成**：与原有输入法代码完美集成

## 使用说明
1. 安装输入法后，系统默认启用悬浮模式
2. 用户无需手动设置，键盘将以悬浮窗口形式显示
3. 键盘支持拖拽移动，位置会自动保存
4. 专为Quest系统优化，提供最佳VR输入体验

## 兼容性
- 专为Quest系统设计
- 支持Quest 1、Quest 2、Quest Pro等所有Quest系列设备
- 支持Meta Quest系列设备

## 注意事项
1. 首次使用时可能需要授予悬浮窗权限
2. 专为Quest系统设计，默认悬浮模式提供最佳体验
3. 悬浮键盘的透明度和位置可根据需要调整

## 技术实现
- **系统级悬浮窗**：使用WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
- **权限管理**：自动检查Settings.canDrawOverlays()权限
- **窗口管理**：在onWindowShown/onWindowHidden中控制悬浮窗显示
- **触摸事件**：通过onComputeInsets优化触摸区域
- **兼容性**：保持与原有代码的完全兼容
- **Quest优化**：专为VR环境设计的显示参数
