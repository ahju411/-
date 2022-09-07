package com.example.kotlintest3

import android.app.Application

/**
 * 2. 전역 App을 통해 Context를 전달하기 위해 만듦.
 * App.instance를 통해 Context를 가져옴
 * 매니페스트를 가서 기본 application name을 .App으로 설정한다.
 */

class App : Application() {
    companion object{
        lateinit var instance: App //자기 자신을 가져옴
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}