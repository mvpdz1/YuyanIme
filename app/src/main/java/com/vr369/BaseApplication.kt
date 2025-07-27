package com.vr369

import android.app.Application
import android.content.Context
import com.yuyan.imemodule.application.Launcher

/**
 * Applicaiton入口
 * @since 2019/6/18
 */
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        // 默认启用悬浮键盘模式（专为Quest系统设计）
        setupFloatingKeyboard()

        Launcher.instance.initData(baseContext)
    }

    /**
     * 设置悬浮键盘模式
     * 默认启用悬浮键盘模式，专为Quest系统优化
     */
    private fun setupFloatingKeyboard() {
        val prefs = getSharedPreferences("com.vr369.imemodule_preferences", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // 默认启用悬浮键盘模式（竖屏和横屏都启用）
        editor.putBoolean("keyboard_mode_float", true)
        editor.putBoolean("keyboard_mode_float_landscape", true)

        // Quest系统标识
        editor.putBoolean("is_quest_device", true)

        editor.apply()
    }


}

