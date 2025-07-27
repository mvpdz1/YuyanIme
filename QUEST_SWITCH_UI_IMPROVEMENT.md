# Quest VR模式开关界面改进

## 🎯 改进目标

1. 将Quest VR模式设置改为开关按钮形式，用户一眼就能看到是否开启
2. 删除键盘设置中的重复Quest模式设置，避免设置重复和混乱
3. 统一Quest模式设置管理，提供更好的用户体验

## ✅ 完整改进方案

### 1. 开关按钮界面实现

#### 修改前（普通按钮）
```kotlin
// 普通preference，点击切换状态
addPreference(
    R.string.quest_mode_enable,
    R.string.quest_mode_enable_summary,
    R.drawable.ic_menu_quest_vr
) {
    // 手动切换逻辑
    val currentValue = AppPrefs.getInstance().keyboardSetting.questModeEnabled.getValue()
    AppPrefs.getInstance().keyboardSetting.questModeEnabled.setValue(!currentValue)
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
```

#### 修改后（开关按钮）
```kotlin
// 开关preference，直观显示状态
addSwitchPreference(
    R.string.quest_mode_enable,
    R.string.quest_mode_enable_summary,
    R.drawable.ic_menu_quest_vr,
    AppPrefs.getInstance().keyboardSetting.questModeEnabled
)
```

### 2. 新增开关支持工具方法

在 `PreferenceScreen.kt` 中添加了 `addSwitchPreference` 方法：

```kotlin
fun PreferenceGroup.addSwitchPreference(
    @StringRes title: Int,
    @StringRes summary: Int? = null,
    @DrawableRes icon: Int? = null,
    managedPreference: ManagedPreference.PBool
) {
    val switchPreference = SwitchPreference(context).apply {
        key = managedPreference.key
        setTitle(title)
        if (summary != null) {
            setSummary(summary)
        }
        if (icon != null) {
            setIcon(context.drawable(icon)?.apply {
                setTint(context.styledColor(R.attr.colorControlNormal))
            })
        } else {
            isIconSpaceReserved = false
        }
        isChecked = managedPreference.getValue()
        
        setOnPreferenceChangeListener { _, newValue ->
            managedPreference.setValue(newValue as Boolean)
            true
        }
    }
    addPreference(switchPreference)
}
```

### 3. 删除重复设置

#### KeyboardSettingFragment 简化
```kotlin
// 修改前 - 包含Quest模式监听和处理逻辑
class KeyboardSettingFragment : ManagedPreferenceFragment(AppPrefs.getInstance().keyboardSetting){
    private val questModeEnabled = AppPrefs.getInstance().keyboardSetting.questModeEnabled
    private val questModeListener = ManagedPreference.OnChangeListener<Boolean> { _, enabled ->
        // 复杂的Quest模式处理逻辑
    }
    // ... 生命周期方法
}

// 修改后 - 简洁的Fragment
class KeyboardSettingFragment : ManagedPreferenceFragment(AppPrefs.getInstance().keyboardSetting)
```

#### AppPrefs.kt 设置整理
```kotlin
// KeyboardSetting类中删除了重复的Quest设置定义
// 改为引用Internal类中的设置
inner class KeyboardSetting : ManagedPreferenceCategory(...) {
    // ... 其他键盘设置
    
    // Quest VR模式设置 - 引用Internal类中的设置
    val questModeEnabled get() = getInstance().internal.questModeEnabled
}
```

### 4. 统一Quest模式管理

将Quest模式的监听逻辑移到 `ImeSettingsFragment` 中：

```kotlin
class ImeSettingsFragment : PreferenceFragmentCompat() {
    private val questModeEnabled = AppPrefs.getInstance().keyboardSetting.questModeEnabled

    private val questModeListener = ManagedPreference.OnChangeListener<Boolean> { _, enabled ->
        Log.d(TAG, "Quest模式设置变更: $enabled")

        if (enabled) {
            Log.i(TAG, "启用Quest VR模式 - 使用独立Activity作为键盘界面")
        } else {
            Log.i(TAG, "禁用Quest VR模式 - 使用传统悬浮窗模式")
            // 如果当前有Quest键盘Activity在运行，关闭它
            QuestKeyboardActivity.getCurrentInstance()?.closeKeyboard()
        }

        // 重新初始化环境和键盘
        EnvironmentSingleton.instance.initData()
        KeyboardLoaderUtil.instance.clearKeyboardMap()
        KeyboardManager.instance.clearKeyboard()
    }

    override fun onStart() {
        super.onStart()
        questModeEnabled.registerOnChangeListener(questModeListener)
    }

    override fun onStop() {
        super.onStop()
        questModeEnabled.unregisterOnChangeListener(questModeListener)
    }
}
```

## 🔧 技术优势

### 用户体验提升

#### 直观的开关显示
- ✅ **状态可见**: 用户一眼就能看到Quest模式是否开启
- ✅ **操作简单**: 直接点击开关即可切换，无需额外确认
- ✅ **即时反馈**: 开关状态立即反映设置变化

#### 设置统一管理
- ✅ **避免重复**: 删除了键盘设置中的重复Quest设置
- ✅ **逻辑集中**: Quest模式相关逻辑集中在主设置页面
- ✅ **维护简单**: 只需在一个地方管理Quest模式设置

### 技术实现优势

#### 代码复用性
- ✅ **工具方法**: `addSwitchPreference` 可用于其他开关设置
- ✅ **标准化**: 使用Android标准的SwitchPreference组件
- ✅ **一致性**: 与系统设置界面风格保持一致

#### 架构清晰
- ✅ **职责分离**: 主设置页面负责Quest模式，键盘设置专注键盘功能
- ✅ **依赖简化**: 减少了组件间的重复依赖
- ✅ **扩展性**: 易于添加更多开关类型的设置

## 📊 界面对比

### 修改前
```
输入法设置首页
├── 输入法
│   ├── 输入设置
│   ├── 手写设置
│   └── Quest VR模式 [普通按钮] ← 点击切换，状态不明显
├── 键盘
│   ├── 主题
│   ├── 键盘反馈
│   ├── 键盘设置
│   │   └── Quest VR模式 [开关] ← 重复设置
│   └── ...
```

### 修改后
```
输入法设置首页
├── 输入法
│   ├── 输入设置
│   ├── 手写设置
│   └── Quest VR模式 [开关按钮] ← 状态直观，操作简单
├── 键盘
│   ├── 主题
│   ├── 键盘反馈
│   ├── 键盘设置 ← 专注键盘功能，无重复设置
│   └── ...
```

## 🎯 用户操作流程

### 查看Quest模式状态
1. 打开输入法设置
2. 在"输入法"分类中查看"启用Quest模式"
3. 开关状态直观显示当前是否启用

### 切换Quest模式
1. 点击"启用Quest模式"开关
2. 开关状态立即改变
3. Quest模式相关逻辑自动执行

### 设置管理
- ✅ **单一入口**: 只在主设置页面管理Quest模式
- ✅ **状态同步**: 所有相关组件自动同步状态
- ✅ **逻辑一致**: 统一的Quest模式处理逻辑

## 🔄 向后兼容性

### 设置数据兼容
- ✅ **数据保持**: 现有用户的Quest模式设置完全保持
- ✅ **键值不变**: SharedPreferences键值保持不变
- ✅ **默认值**: 新用户默认启用Quest模式

### 功能兼容
- ✅ **API不变**: Quest模式的启用/禁用逻辑保持不变
- ✅ **监听机制**: 保持原有的设置变更监听机制
- ✅ **生命周期**: 正确的监听器注册和注销

## 🎉 改进效果

### 用户体验
- 🚀 **直观性**: 开关状态一目了然
- 🎯 **简洁性**: 删除重复设置，界面更清爽
- ⭐ **一致性**: 与Android系统设置风格一致

### 开发维护
- 🔧 **代码简化**: 删除重复代码，逻辑更清晰
- 📈 **可维护性**: 集中管理，易于维护和扩展
- 🛡️ **稳定性**: 减少设置冲突，提高稳定性

### 技术架构
- ✅ **职责清晰**: 各组件职责明确，不重复
- ✅ **扩展性**: 易于添加新的开关设置
- ✅ **标准化**: 使用标准组件，符合Android规范

---

**改进状态**: ✅ 完成  
**编译状态**: ✅ 通过  
**用户体验**: ⭐⭐⭐⭐⭐ 显著提升
