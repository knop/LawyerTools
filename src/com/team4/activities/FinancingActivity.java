package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.team4.common.CommonDef;
import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TFinancingEntity;

public class FinancingActivity extends Activity {

	private TFinancingEntity mFinancingEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financing);
		Intent intent = getIntent();
		mFinancingEntity = (TFinancingEntity) intent
				.getSerializableExtra(TFinancingEntity.class.getName());
		showDetail(mFinancingEntity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		MenuInflater inflater = new MenuInflater(this);  
		inflater.inflate(R.menu.communication_menu, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) { 
		switch(item.getItemId()){
		case R.id.communication:
			showCommunicationPage();
			break;
		case R.id.match:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDetail(final TFinancingEntity entity) {
		if (entity == null)
			return;

		// 公司名称
		TextView tvTitle = (TextView) findViewById(R.id.tv_financing_title);
		tvTitle.setText(entity.getName());

		// 信息来源
		TextView tvInfoSource = (TextView) findViewById(R.id.tv_financing_info_source);
		tvInfoSource.setText(entity.getInfoSource());

		// 公司类型
		TextView tvCompanyType = (TextView) findViewById(R.id.tv_financing_company_type);
		tvCompanyType.setText(entity.getCompanyType());

		// 邮箱
		TextView tvEmail = (TextView) findViewById(R.id.tv_financing_email);
		tvEmail.setText(entity.getEmail());

		// 联系人姓名
		TextView tvContactPerson = (TextView) findViewById(R.id.tv_financing_contact_person);
		tvContactPerson.setText(entity.getContactPerson());

		// 电话
		TextView tvTelephone = (TextView) findViewById(R.id.tv_financing_telephone);
		tvTelephone.setText(entity.getTelePhone());

		// 评级
		TextView tvRate = (TextView) findViewById(R.id.tv_financing_rate);
		tvRate.setText(entity.getRate());

		// 融资行业
		TextView tvIndustry = (TextView) findViewById(R.id.tv_financing_industry);
		tvIndustry.setText(entity.getIndustry());

		// 融资方式
		TextView tvStyle = (TextView) findViewById(R.id.tv_financing_style);
		tvStyle.setText(entity.getStyle());

		// 融资额度
		TextView tvQuota = (TextView) findViewById(R.id.tv_financing_quota);
		tvQuota.setText(entity.getQuotaFloor() + "-" + entity.getQuotaUpper() + "千万");
		
		// 融资期限
		TextView tvHorizon = (TextView) findViewById(R.id.tv_financing_horizon);
		tvHorizon.setText(entity.getHorizonFloor() + "-" + entity.getHorizonFloor() + "个月");
		
		// 融资成本
		TextView tvCost = (TextView) findViewById(R.id.tv_financing_cost);
		tvCost.setText("");
		
		// 担保方式
		TextView tvGuaranteeType = (TextView) findViewById(R.id.tv_financing_guarantee_type);
		tvGuaranteeType.setText(entity.getGuaranteeType());
		
		// 还款方式
		TextView tvPayment = (TextView) findViewById(R.id.tv_financing_payment);
		tvPayment.setText(entity.getPayment());
		
		// 项目所在地
		TextView tvArea = (TextView) findViewById(R.id.tv_financing_area);
		tvArea.setText(entity.getArea());
		
		// 是否有佣金
		TextView tvHasCommission = (TextView) findViewById(R.id.tv_financing_has_commission);
		tvHasCommission.setText(entity.getHasCommission()?R.string.yes:R.string.no);

		// 佣金比例
		TextView tvCommissionProportion = (TextView) findViewById(R.id.tv_financing_commission_proportion);
		tvCommissionProportion.setText(entity.getCommissionProportion());

		// 备注
//		TextView tvComment = (TextView) findViewById(R.id.tv_financing_comment);
//		tvComment.setText(entity.getComment());
		String data = entity.getComment();
		if (data == null || data.length() <= 0)
			data = this.getResources().getString(R.string.text_none);
		else
			data = data.replaceAll("%","%25");//当字符串中包含%时，会出现无法显示的问题，所以要替换成%25
		WebView wvComment = (WebView) findViewById(R.id.wv_financing_comment);
		wvComment.getSettings().setDefaultTextEncodingName(CommonDef.ENCODING);
		wvComment.loadData(data, CommonDef.MINE_TYPE, null);
	}
	
	private void showCommunicationPage() {
		if (mFinancingEntity == null)
			return;
		Intent intent = new Intent();
		intent.setClass(this, CommunicationActivity.class);
		intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, mFinancingEntity.getId());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, mFinancingEntity.getName());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_FINANCING);
		startActivity(intent);
	}
}
