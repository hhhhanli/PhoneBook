package com.liye.mycontacts.myContacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.liye.QrCode.zxing.profile.ShowQrCodeActivity;
import com.liye.mycontacts.R;
import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.utils.CommonUtil;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsUtil;
import com.liye.onlineVoice.GlobalApplication;
import com.liye.onlineVoice.OnlineVoiceManager;
import com.liye.onlineVoice.VoiceControlActivity;

import java.util.List;

public class XiangxiActivity extends Activity implements OnClickListener, PopupMenu.OnMenuItemClickListener{
	TextView mReturn;
	ImageView mIcon;
	private PopupMenu popupMenu;
	TextView mName, mPhone, mEmail, mAddress, mCallPhone;
	ContactsUtil mContactsUtil;
	List<ContactInfo> contacts;
	////////////////begin
	TextView mEditContact, mDeleteContact, mSendDesk,mShowQrCode,mOnlineVoice;
	//////////////end
	ContactInfo contactInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangxi);
		Intent intent = getIntent();
		contactInfo = intent.getParcelableExtra("contact");
//		Log.e(this + "",
//				contactInfo + " contactInfo=" + contactInfo.getAddress());
		initData();

	}

	public void show(View v){
		//实例化一个弹出式菜单，传入上下文和控件
		popupMenu = new PopupMenu(this,v);
		//根据菜单填充器获得菜单的布局
		popupMenu.getMenuInflater().inflate(R.menu.menu_a,popupMenu.getMenu());
		//设置菜单的点击事件
		popupMenu.setOnMenuItemClickListener(this);
		//显示菜单
		popupMenu.show();
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu1:
				Intent editContact = new Intent(XiangxiActivity.this,
						EditContactActivity.class);
				editContact.putExtra("contact", contactInfo);
				startActivity(editContact);
				break;
			case R.id.menu2:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("删除");
				builder.setMessage("确定要删除联系人吗?");

				builder.setPositiveButton("删除",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								mContactsUtil.delete(contactInfo.getContactId());
								Intent delete = new Intent(XiangxiActivity.this,
										TelephoneActivity.class);
								startActivity(delete);
								finish();
							}
						}

				).show();

				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								finish();
								// dialog.dismiss();
							}
						}).show();
				break;
			case R.id.menu3:
				Intent show_qr = new Intent(XiangxiActivity.this,
						ShowQrCodeActivity.class);
				show_qr.putExtra("contact", contactInfo);
				startActivity(show_qr);
				break;
			case R.id.menu4:
				// 打电话的意图
				Intent intent = new Intent();
				// Intent.ACTION_CALL打电话的动作
				intent.setAction(Intent.ACTION_CALL);
				// uri统一资源标示符
				intent.setData(Uri.parse("tel:" + contactInfo.getPhone()));
				// 开启一个新的界面
				startActivity(intent);
				break;
		}
		return true;
	}

	public void initData() {

		mReturn = (TextView) this.findViewById(R.id.txt_return3);
		mReturn.setOnClickListener(this);
		mIcon = (ImageView) this.findViewById(R.id.img_show_photo3);
		Bitmap smallBitmap = Bitmap.createScaledBitmap(contactInfo.getIcon(), 100, 80, true);
		Bitmap circleBitmap = CommonUtil.createCircleImage(smallBitmap);
		mIcon.setImageBitmap(circleBitmap);

		mName = (TextView) this.findViewById(R.id.txt_show_name3);
		mName.setText(contactInfo.getName());
		mPhone = (TextView) this.findViewById(R.id.txt_show_phone3);
		mPhone.setText(contactInfo.getPhone());
		mEmail = (TextView) this.findViewById(R.id.txt_show_email3);
		mEmail.setText(contactInfo.getEmail());
		mAddress = (TextView) this.findViewById(R.id.txt_show_address3);
		mAddress.setText(contactInfo.getAddress());


		mEditContact = (TextView) this.findViewById(R.id.edit_contact3);
		mEditContact.setOnClickListener(this);
		mDeleteContact = (TextView) this.findViewById(R.id.delete_contact3);
		mDeleteContact.setOnClickListener(this);
//		mSendDesk = (TextView) this.findViewById(R.id.send_desk3);
//		mSendDesk.setOnClickListener(this);

		///////////////begin
		mShowQrCode = (TextView) this.findViewById(R.id.show_QR_code);
		mShowQrCode.setOnClickListener(this);
		/////////////end

		mCallPhone = (TextView) this.findViewById(R.id.callPhone);
		mCallPhone.setOnClickListener(this);
		mContactsUtil = new ContactsUtil(this);


		mOnlineVoice = (TextView)this.findViewById(R.id.online_vioce);
		mOnlineVoice.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.txt_return3:
				finish();
				break;
			case R.id.edit_contact3:
				Intent editContact = new Intent(XiangxiActivity.this,
						EditContactActivity.class);
				editContact.putExtra("contact", contactInfo);
				startActivity(editContact);
				break;
			case R.id.delete_contact3:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("删除");
				builder.setMessage("确定要删除联系人吗?");

				builder.setPositiveButton("删除",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								mContactsUtil.delete(contactInfo.getContactId());
								Intent delete = new Intent(XiangxiActivity.this,
										TelephoneActivity.class);
								startActivity(delete);
								finish();
							}
						}

				).show();

				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								finish();
								// dialog.dismiss();
							}
						}).show();

				break;
			case R.id.show_QR_code:

				Intent show_qr = new Intent(XiangxiActivity.this,
						ShowQrCodeActivity.class);
				show_qr.putExtra("contact", contactInfo);
				startActivity(show_qr);
				break;
			case R.id.online_vioce:
				if(GlobalApplication.isLogin){
					if(! OnlineVoiceManager.getInstance().call_up(contactInfo) ){
						Toast.makeText(XiangxiActivity.this,"对方未上线",Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(XiangxiActivity.this,"请先打开网络电话功能",Toast.LENGTH_SHORT).show();;
				}

				break;

			case R.id.callPhone:
				// 打电话的意图
				Intent intent = new Intent();
				// Intent.ACTION_CALL打电话的动作
				intent.setAction(Intent.ACTION_CALL);
				// uri统一资源标示符
				intent.setData(Uri.parse("tel:" + contactInfo.getPhone()));
				// 开启一个新的界面
				startActivity(intent);
				break;

		}

	}
}