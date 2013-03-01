package com.team4.app;

import android.app.Application;

import com.team4.utils.util.T4Log;
import com.team4.utils.util.UncaughtCrashHandler;

/**
*  @Project	 LawyerTools
*  @Program  com.team4.app.T4Application.java
*  @Author   Xiaohui Chen
*  @Email    knop0211@gmail.com
*  @Creation 2013-3-1 下午3:00:37 
*  @Description 继承{@link Application}，实现一些在UI展示之前需要处理的事情，同时可以用这个类创建一些类似于全局的变量
*
*  @History  
*  Who            When          What 
*  ------------   -----------   ------------------------------------
*  Xiaohui Chen   2013-3-1      Create
*
*/
public class T4Application extends Application {
	@Override 
	public void onCreate() {
		super.onCreate();
		T4Log.v("T4Application.onCreate"); 
		
		//捕获未处理的Crash
        UncaughtCrashHandler.getInstance().init(getApplicationContext());  
	}

	@Override
    public void onTerminate() {
        super.onTerminate();
	}
}
