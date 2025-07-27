# 基于模式的回车键处理逻辑

## 🎯 设计目标

根据当前输入法模式（Quest模式 vs 悬浮框模式）来决定是否对浏览器的回车键做特殊处理：
- **悬浮框模式**：对浏览器回车键做特殊处理，提供更好的用户体验
- **Quest模式**：使用标准处理，不需要特殊的浏览器处理

## 🔧 技术实现

### 核心逻辑

```kotlin
/**
 * Quest VR兼容的回车键处理 - 根据模式决定是否对浏览器做特殊处理
 */
private fun questCompatibleEnterKey() {
    try {
        val ic = service.currentInputConnection
        if (ic != null) {
            val editorInfo = service.currentInputEditorInfo
            if (editorInfo != null) {
                val action = editorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
                val packageName = editorInfo.packageName
                val fieldName = editorInfo.fieldName
                
                // 检查当前是否为Quest模式
                val isQuestMode = getInstance().internal.questModeEnabled.getValue()
                
                when (action) {
                    EditorInfo.IME_ACTION_GO -> {
                        if (!isQuestMode && packageName == "com.oculus.browser") {
                            // 悬浮框模式下对Quest浏览器做特殊处理
                            handleQuestBrowserGo(ic)
                        } else {
                            // Quest模式或其他浏览器的标准处理
                            val result = ic.performEditorAction(EditorInfo.IME_ACTION_GO)
                            if (!result) service.sendEnterKeyEvent()
                        }
                    }
                    // 其他动作的处理...
                }
            }
        }
    } catch (e: Exception) {
        service.sendEnterKeyEvent()
    }
}
```

### 模式判断机制

**Quest模式检测**：
```kotlin
val isQuestMode = getInstance().internal.questModeEnabled.getValue()
```

**处理逻辑分支**：
```kotlin
if (!isQuestMode && packageName == "com.oculus.browser") {
    // 悬浮框模式 + Quest浏览器 = 特殊处理
    handleQuestBrowserGo(ic)
} else {
    // Quest模式 或 其他情况 = 标准处理
    standardHandling()
}
```

## 📊 处理逻辑表

| 模式 | 应用 | 动作 | 处理方式 |
|------|------|------|----------|
| 悬浮框模式 | Quest浏览器 | IME_ACTION_GO | 特殊Intent处理 |
| 悬浮框模式 | 其他浏览器 | IME_ACTION_GO | 标准performEditorAction |
| 悬浮框模式 | 非浏览器 | 任何动作 | 标准处理 |
| Quest模式 | 任何应用 | 任何动作 | 标准处理 |

## 🎮 Quest模式处理

### Quest模式特点

**标准化处理**：
- 使用Android标准的`performEditorAction`
- 不进行复杂的Intent启动
- 简化的错误处理逻辑

**代码示例**：
```kotlin
EditorInfo.IME_ACTION_GO -> {
    android.util.Log.d("InputView", "Quest mode: Standard GO action")
    val result = ic.performEditorAction(EditorInfo.IME_ACTION_GO)
    if (!result) {
        service.sendEnterKeyEvent()
    }
}
```

### Quest模式优势

- ✅ **性能优化**：避免复杂的Intent处理
- ✅ **稳定性高**：使用标准Android API
- ✅ **兼容性好**：适用于所有应用
- ✅ **维护简单**：逻辑清晰，易于调试

## 🖥️ 悬浮框模式处理

### 悬浮框模式特点

**Quest浏览器特殊处理**：
- 检测Quest浏览器包名
- 获取地址栏URL内容
- 使用Intent直接启动浏览器

**代码示例**：
```kotlin
private fun handleQuestBrowserGo(ic: InputConnection) {
    try {
        // 获取地址栏文本
        val currentText = ic.getTextBeforeCursor(1000, 0)?.toString() ?: ""
        val afterText = ic.getTextAfterCursor(1000, 0)?.toString() ?: ""
        val fullText = currentText + afterText

        if (fullText.isNotEmpty()) {
            var url = fullText.trim()
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            // 使用Intent启动浏览器
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.oculus.browser")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            
            service.startActivity(intent)
            service.requestHideSelf(0)
            return
        }

        // 备用方案：标准处理
        val result = ic.performEditorAction(EditorInfo.IME_ACTION_GO)
        if (!result) service.sendEnterKeyEvent()
    } catch (e: Exception) {
        service.sendEnterKeyEvent()
    }
}
```

### 悬浮框模式优势

- ✅ **用户体验**：直接启动浏览器，无需手动操作
- ✅ **功能完整**：支持URL自动补全和启动
- ✅ **智能检测**：只对Quest浏览器进行特殊处理

## 🔄 UNSPECIFIED/NONE动作处理

### 浏览器检测逻辑

```kotlin
EditorInfo.IME_ACTION_UNSPECIFIED, EditorInfo.IME_ACTION_NONE -> {
    if (!isQuestMode && (packageName?.contains("browser") == true ||
        packageName?.contains("chrome") == true ||
        fieldName?.contains("url") == true ||
        fieldName?.contains("address") == true)) {
        // 悬浮框模式下检测到浏览器，尝试GO动作
        val result = ic.performEditorAction(EditorInfo.IME_ACTION_GO)
        if (!result) {
            service.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER)
        }
    } else {
        // Quest模式或非浏览器的标准处理
        service.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER)
    }
}
```

### 检测条件

**悬浮框模式下的浏览器检测**：
- 包名包含"browser"
- 包名包含"chrome"
- 字段名包含"url"
- 字段名包含"address"

**Quest模式下的处理**：
- 直接发送ENTER键事件
- 不进行浏览器检测

## 🚀 性能优化

### 模式检测优化

**一次性检测**：
```kotlin
val isQuestMode = getInstance().internal.questModeEnabled.getValue()
```
- 在方法开始时检测一次
- 避免重复调用
- 提高执行效率

### 条件判断优化

**短路求值**：
```kotlin
if (!isQuestMode && packageName == "com.oculus.browser") {
    // 只有在非Quest模式时才检查包名
}
```
- Quest模式下直接跳过复杂检测
- 减少不必要的字符串比较

## 🧪 测试场景

### Quest模式测试

1. **浏览器测试**：
   - Quest浏览器地址栏回车
   - Chrome浏览器地址栏回车
   - 其他浏览器回车

2. **非浏览器测试**：
   - 文本框回车
   - 搜索框回车
   - 表单提交

### 悬浮框模式测试

1. **Quest浏览器特殊处理**：
   - 地址栏URL输入和跳转
   - Intent启动验证
   - 错误处理测试

2. **其他应用标准处理**：
   - 普通文本框回车
   - 搜索功能测试
   - 表单提交测试

## 🎉 实现效果

### 用户体验

**Quest模式**：
- ✅ **简洁高效**：标准的回车键处理，响应迅速
- ✅ **稳定可靠**：使用Android标准API，兼容性好
- ✅ **一致体验**：所有应用的回车键行为一致

**悬浮框模式**：
- ✅ **智能处理**：Quest浏览器自动跳转URL
- ✅ **功能增强**：提供更好的浏览体验
- ✅ **向后兼容**：其他应用保持标准行为

### 技术优势

**代码结构**：
- ✅ **逻辑清晰**：模式判断明确，处理路径清楚
- ✅ **易于维护**：分离的处理逻辑，便于调试
- ✅ **性能优化**：避免不必要的复杂处理

**兼容性**：
- ✅ **模式适配**：完美适配两种输入法模式
- ✅ **应用兼容**：支持各种类型的应用
- ✅ **系统兼容**：使用标准Android API

## 📝 总结

### 核心设计原则

1. **模式驱动**：根据输入法模式决定处理策略
2. **性能优先**：Quest模式使用简化处理，提高性能
3. **体验优化**：悬浮框模式提供增强功能
4. **向后兼容**：保持现有功能的稳定性

### 技术亮点

- ✅ **智能模式检测**：自动识别当前输入法模式
- ✅ **分层处理逻辑**：不同模式使用不同的处理策略
- ✅ **性能优化**：避免不必要的复杂处理
- ✅ **用户体验**：在合适的场景提供增强功能

---

**实现状态**: ✅ 完成  
**编译状态**: ✅ 通过  
**模式适配**: 🎯 完美适配

这个基于模式的回车键处理逻辑既保证了Quest模式的简洁高效，又保持了悬浮框模式的功能增强！
