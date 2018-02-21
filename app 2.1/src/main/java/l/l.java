package l;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.LinearLayout.*;
import java.io.*;
import android.*;

public class l extends Activity implements OnClickListener
{
	LinearLayout l,l1;
	LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1);
	Animation a1,aa;
	Button b;
	Boolean bl;
	TextView t,t1;
	float d,z;
	int w,h,p,m;
	String ver,
	s1="请ROOT后再试！",
	rw="mount -o rw,remount /system",
	ro="mount -o ro,remount /system",
	cp="cp -rf %s %s",
	mv="mv %s %s ",
	rm="rm -rf %s",
	chmod="chmod -R 0755 %s"
	;
	String[] s={"切换模式","授权和包","修复配置","修复模块"};
	
	String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if (checkSelfPermission(permissions[0]) != 0)
			requestPermissions(permissions, 321);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setNavigationBarColor(0);
			getWindow().setStatusBarColor(0);
		}else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
								 WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		d = dm.density;
		if(dm.widthPixels<dm.heightPixels)
		{
			w = dm.widthPixels;
			h = dm.heightPixels;
		}else{
			h = dm.widthPixels;
			w = dm.heightPixels;
		}
		p=w/36;
		m=w/60;
		z=w/d/20;

		lp.setMargins(m,m,m,m);

		l=new LinearLayout(this);
		l.setPadding(p,p,p,p);
		l.setGravity(Gravity.CENTER);
		l.setOnClickListener(this);

		a1=new TranslateAnimation(0, 0, h, 0);
		a1.setDuration(500);
		l.startAnimation(a1);

		l1=new LinearLayout(this);
		l1.setPadding(p,0,p,p);
		l1.setOrientation(LinearLayout.VERTICAL);
		l1.setBackgroundDrawable(d(w/10,0xddcccccc));
		l1.setLayoutParams(lp);
		l.addView(l1);
		
		try
		{
			ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{}
		
		t(sb("OnePlusNFC",ver));
		
		t(Build.MODEL);
		t(Build.DEVICE+"/"+Build.VERSION.RELEASE+"("+Build.VERSION.SDK+")");
		t(t1,oat()?"官方模式":"运营商模式");
		
		b(0);
		b(1);
		if(Build.BRAND.equals("OnePlus"))
		{
			b(2);
			b(3);
		}
		f("/sdcard/OnePlusNFC");
		f("/sdcard/OnePlusNFC/Backup");
		f("/sdcard/OnePlusNFC/SmartcardService");
		f("/sdcard/OnePlusNFC/SmartcardService/oat");
		f("/sdcard/OnePlusNFC/"+Build.DEVICE);
		
		addContentView(l, new WindowManager.LayoutParams());
		
		try
		{
			if (!new File("/sdcard/OnePlusNFC/SmartcardService.apk").exists())
				c("SmartcardService.apk", "/sdcard/OnePlusNFC/SmartcardService.apk");
			c("SmartcardService/SmartcardService.apk", "/sdcard/OnePlusNFC/SmartcardService/SmartcardService.apk");
		}
		catch (IOException e)
		{}

	
	}
	
	
	@Override
	public void onClick(View v)
	{
		aa=new AlphaAnimation(1, 0);
		aa.setDuration(1000);
		v.startAnimation(aa);
		
		switch(v.getId())
		{
			case 0:
				try
				{
					if (!new File("/sdcard/OnePlusNFC/SmartcardService.apk").exists())
					c("SmartcardService.apk", "/sdcard/OnePlusNFC/SmartcardService.apk");
					
					
					s(rw);
					if (oat())
					{
						s(String.format(cp, "/system/priv-app/SmartcardService", "/sdcard/OnePlusNFC/Backup"));
						s(String.format(rm, "/system/priv-app/SmartcardService/*"));
						s(String.format(cp, "/sdcard/OnePlusNFC/SmartcardService.apk", "/system/priv-app/SmartcardService"));
					}
					else
					{
						if(new File("/sdcard/OnePlusNFC/Backup/SmartcardService").exists())
						{
							s(String.format(rm, "/system/priv-app/SmartcardService/*"));
							s(String.format(cp, "/sdcard/OnePlusNFC/Backup/SmartcardService/*", "/system/priv-app/SmartcardService"));
							
						}
						else
						{
							T("备份文件缺失，正在修复模块");
							
							s(String.format(cp, "/sdcard/OnePlusNFC/SmartcardService", "/system/priv-app"));
						}
					}
					s(String.format(chmod, "/system/priv-app/SmartcardService"));
					s("reboot");
					s("svc power reboot");
				}
				catch (IOException e)
				{}
				
				
				t1.setText(oat()?"官方模式":"运营商模式");
				
				break;
			case 1:
				try
				{
					if(r("pm clear com.cmcc.hebao").equals("Success"))
					{
						s("pm grant com.cmcc.hebao org.simalliance.openmobileapi.SMARTCARD");
						startActivity(new Intent()
									  .setClassName("com.cmcc.hebao", "com.cmcc.wallet.LoadingActivity")
									  .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
									  );
					}
					
				}
				catch (IOException e)
				{}
				
				break;
			case 2:
				try
				{
					if (Build.PRODUCT.equals("OnePlus3"))
						c("OnePlus3/libnfc-nxp.conf", "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf");
					if (Build.PRODUCT.equals("OnePlus5"))
						c("OnePlus5/libnfc-nxp.conf", "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf");
						
					s(rw);
					s(String.format(cp, "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf","/system/etc"));
					s(String.format(chmod, "/system/etc/libnfc-nxp.conf"));
					s("service call nfc 7");
					s("service call nfc 6");
					s("service call nfc 7");
					s("service call nfc 8");
					T("修复完成！");
				}
				catch (IOException e)
				{
					T("修复失败！");
				}
				break;
			case 3:
				try
				{
					c("SmartcardService/SmartcardService.apk", "/sdcard/OnePlusNFC/SmartcardService/SmartcardService.apk");
					s(rw);
					s(String.format(cp, "/sdcard/OnePlusNFC/SmartcardService", "/system/priv-app"));
					s(String.format(chmod, "/system/priv-app/SmartcardService"));
					s("reboot");
					s("svc power reboot");
				}
				catch (IOException e)
				{
					T("修复失败！");
				}
				break;
			default:finish();
		}
		
	}

	

	SpannableStringBuilder sb(String b,String s)
	{
		SpannableStringBuilder sb = new SpannableStringBuilder();
		sb.append(b);
		sb.append("\n");
		sb.append(s);
		sb.setSpan(new RelativeSizeSpan(1.6f),0,b.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, b.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new RelativeSizeSpan(0.8f),b.length()+1,b.length()+1+s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new StyleSpan(Typeface.ITALIC), b.length()+1,b.length()+1+s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sb;
	}
	
	boolean oat()
	{
		return new File("/system/priv-app/SmartcardService/oat").exists();
	}
	void t(TextView t,CharSequence s)
	{
		t = new TextView(this);
		t.setText(s); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setTextIsSelectable(true);
		t.setGravity(Gravity.CENTER);
		l1.addView(t);
	}
	
	void t(CharSequence s)
	{
		t = new TextView(this);
		t.setText(s); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setTextIsSelectable(true);
		t.setGravity(Gravity.CENTER);
		l1.addView(t);
	}
	void b(int i)
	{
		b=new Button(this);
		b.setId(i);
		b.setText(s[i]);
		b.setTextColor(0xffffffff);
		b.setTextSize(z);
		b.setBackgroundDrawable(d(w/16,0xffff5556));
		b.setPadding(p,p,p,p);
		b.setLayoutParams(lp);
		b.setOnClickListener(this);
		l1.addView(b);
	}
	
	void T(String s)
	{
		Toast.makeText(this,s,50).show();
	}

	Drawable d(int r,int c)
	{
		GradientDrawable d=new GradientDrawable();
		d.setColor(c);
		d.setCornerRadius(r);
		d.setStroke(2, 0xffeeeeee);
		return d;
	}
	
	void f(String s)
	{
		File dir = new File(s);
		if (!dir.exists())
		{
			dir.mkdir();
		}
	}
	
	void c(String assetsFileName, String OutFileName) throws IOException 
	{
        File f = new File(OutFileName);
        if (f.exists())
            f.delete();
        f = new File(OutFileName);
        f.createNewFile();
        InputStream I = getAssets().open(assetsFileName);
        OutputStream O = new FileOutputStream(OutFileName);
        byte[] b = new byte[1024];
        int l = I.read(b);
        while (l > 0) 
		{
            O.write(b, 0, l);
            l = I.read(b);
        }
        O.flush();
        I.close();
        O.close();
    }
	
	java.lang.Process su;
	void s(String s) throws IOException
	{
		if (su == null)su = Runtime.getRuntime().exec("su");
		OutputStream o=su.getOutputStream();
		o.write((String.format("%s\n",s)).getBytes());
		o.flush();
	}
	String r(String s) throws IOException
	{
		s(s);
		byte[] b = new byte[4096];
		int i=su.getInputStream().read(b);
		
		s = new String(b, 0, i);
		return s.trim();
	}
	
	
}
