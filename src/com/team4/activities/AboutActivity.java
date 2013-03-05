package com.team4.activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.team4.lawyertools.R;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		showDetail();
	}
	
	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-5 上午9:36:56 
	*  @Description 显示“关于”详情信息
	*
	*/
	private void showDetail() {
		TextView tvVersion = (TextView)findViewById(R.id.tv_about_version);
		String version = getVersion();
		tvVersion.setText(version);
		
		TextView tvRD = (TextView)findViewById(R.id.tv_about_rd);
		tvRD.setText("陈小辉\n徐淮杰");	
		
		TextView tvEmail = (TextView)findViewById(R.id.tv_about_email);
		tvEmail.setText("knop0211@gmail.com");
		
		TextView tvCopyright = (TextView)findViewById(R.id.tv_about_copyright);
		tvCopyright.setText("版权所有 team4.us");		
	}
	
	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-5 上午9:36:42 
	*  @return
	*  @Description 获取版本信息
	*
	*/
	private String getVersion() {
		PackageManager pm = getPackageManager();
		String version = getResources().getString(R.string.text_none);
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			version = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
}
