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
import java.util.*;

public class l extends Activity implements OnClickListener,Runnable
{
	LinearLayout l,lo,ll,li,l0,l1,l2,l3,l4,l5;
	ScrollView sc;
	LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1);
	Animation a1,aa;
	Button b;
	boolean bl,bl2;
	TextView t;
	float d,z;
	int w,h,p,m;
	String ver,lib=null,
	s1="请ROOT后再试！",
	p1="/system/etc/libnfc-nxp.conf",
	p2="/system/vendor/etc/libnfc-nxp.conf",
	rv="mount -o rw,remount /vendor",
	rw="mount -o rw,remount /system",
	ro="mount -o ro,remount /system",
	cp="cp -rf %s %s",
	mv="mv %s %s ",
	rm="rm -rf %s",
	chmod="chmod -R 0755 %s",
	sed1="sed -i s/NXP_DEFAULT_SE=0x0[0-9]/NXP_DEFAULT_SE=0x0%s/ ",
	sed2="sed -i s/DEFAULT_AID_ROUTE=0x0[0-9]/DEFAULT_AID_ROUTE=0x0%s/ ",
	sed3="sed -i s/DEFAULT_DESFIRE_ROUTE=0x0[0-9]/DEFAULT_DESFIRE_ROUTE=0x0%s/ ",
	sed4="sed -i s/DEFAULT_MIFARE_CLT_ROUTE=0x0[0-9]/DEFAULT_MIFARE_CLT_ROUTE=0x0%s/ ",
	nxp=""
	;

	List<PackageInfo> pi;
	
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

		aa=new AlphaAnimation(0,1);
		aa.setDuration(500);
		l.startAnimation(aa);
		
		
		lo=new LinearLayout(this);
		lo.setPadding(p,p,p,p);
		lo.setOrientation(LinearLayout.VERTICAL);
		lo.setBackgroundDrawable(d(w/10,0xddcccccc));
		lo.setLayoutParams(lp);
		l.addView(lo);
		
		sc=new ScrollView(this);
		sc.setVerticalScrollBarEnabled(false);
		lo.addView(sc);
		
		ll=new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sc.addView(ll);
		
		try
		{
			ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{}
		
		t(sb("OnePlusNFC",ver));
		t(Build.MODEL);
		t(Build.DEVICE+"/"+Build.VERSION.RELEASE+"("+Build.VERSION.SDK+")");
		
		l0=new LinearLayout(this);
		l0.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l0);
		
		li=new LinearLayout(this);
		li.setOrientation(LinearLayout.VERTICAL);
		l0.addView(li);
		
		l1=new LinearLayout(this);
		l1.setVisibility(8);
		l1.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l1);
		
		l2=new LinearLayout(this);
		l2.setVisibility(8);
		l2.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l2);
		
		l3=new LinearLayout(this);
		l3.setVisibility(8);
		l3.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l3);
		
		l4=new LinearLayout(this);
		l4.setVisibility(8);
		l4.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l4);

		l5=new LinearLayout(this);
		l5.setVisibility(8);
		l5.setOrientation(LinearLayout.VERTICAL);
		ll.addView(l5);
		
		t("模块："+(oat()?"官方模块":"公交模块"),li);
		
		b(1,"☆模块☆",l0);
		b(2,"☆配置☆",l0);
		b(3,"☆授权☆",l0);
		b(4,"☆修复☆",l0);
		b(5,"☆更多☆",l0);
		
		
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
		
		if (new File(p1).exists())
			lib=p1;
		else 
		if (new File(p2).exists())
			lib=p2;
		
		if(lib!=null)
		new Handler().postDelayed(this,900);
	
	}

	@Override
	public void run()
	{
		t(cf(),li);
	}
	
	
	@Override
	public void onClick(View v)
	{
		aa=new AlphaAnimation(1, 0);
		aa.setRepeatCount(1);
		aa.setRepeatMode(2);
		aa.setDuration(1000);
		v.startAnimation(aa);
		
		switch(v.getId()/100)
		{
			case 0:
				switch(v.getId())
				{
					case 1:
						l0.setVisibility(8);
						l1.setVisibility(0);
						
						t("模块："+(oat()?"官方模块":"公交模块"),l1);
						b(11,"公交模块",l1);
						b(11,"还原模块",l1);
						
						break;
					case 2:
						l0.setVisibility(8);
						l2.setVisibility(0);
						
						t(cf(),l2);
						t(lib,l2);
						b(25,"兼容模式",l2);
						findViewById(25).setBackground(d(w/16,0));

						t("(点亮绿色可开启兼容模式！)",l2);
						
						b(21,"公交配置",l2);
						b(22,"默认配置",l2);
						b(23,"调试配置",l2);
						
			
						break;
					case 3:
						if(!oat())
						{
							l0.setVisibility(8);
							l3.setVisibility(0);
							
							t("★授权管理★\n(点击应用名即授权)",l3);
							b(31,"暴力模式",l3);
							findViewById(31).setBackground(d(w/16,0));
							
							t("(暴力授权会清除数据)",l3);
							
							if(pi==null)
								pi=getAppListByPermission("org.simalliance.openmobileapi.SMARTCARD");

							for (int i=0;i < pi.size();i++)
							{
								b(100+i,"["+(i+1)+"]"+pi.get(i).applicationInfo.loadLabel(getPackageManager()),l3);

							}
						}
						else
						{
							T("请先切换模块到公交模块");
						}
						break;
					case 4:
						if(Build.BRAND.equals("OnePlus"))
						{
							l0.setVisibility(8);
							l4.setVisibility(0);
							
							b(41,"修复配置",l4);
							b(42,"修复模块",l4);
							
						}
						else
						{
							T("这功能只有一渣手机能用！");
						}
						
						break;
					case 5:
						l0.setVisibility(8);
						l5.setVisibility(0);
						t("在酷安上更新",l5);
						b(51,"更新",l5);
						t("群内隐藏着各路大神",l5);
						b(52,"进群",l5);
						t("捐赠请留言，并加上NFC",l5);
						b(53,"捐赠",l5);
						
						break;
					case 11:
						try
						{
							if (!new File("/sdcard/OnePlusNFC/SmartcardService.apk").exists())
								c("SmartcardService.apk", "/sdcard/OnePlusNFC/SmartcardService.apk");


							s(rw);
							s(String.format(cp, "/system/priv-app/SmartcardService", "/sdcard/OnePlusNFC/Backup"));
							s(String.format(rm, "/system/priv-app/SmartcardService/*"));
							s(String.format(cp, "/sdcard/OnePlusNFC/SmartcardService.apk", "/system/priv-app/SmartcardService"));

							s(String.format(chmod, "/system/priv-app/SmartcardService"));
							s("reboot");
							s("svc power reboot");
						}
						catch (IOException e)
						{}

						break;
					case 12:
						try
						{
							s(rw);

							if(new File("/sdcard/OnePlusNFC/Backup/SmartcardService").exists())
							{
								s(String.format(rm, "/system/priv-app/SmartcardService/*"));
								s(String.format(cp, "/sdcard/OnePlusNFC/Backup/SmartcardService/*", "/system/priv-app/SmartcardService"));

							}
							else
							{
								T("备份文件不存在！");
							}
							s(String.format(chmod, "/system/priv-app/SmartcardService"));
							s("reboot");
							s("svc power reboot");
						}
						catch (IOException e)
						{}

						break;

					case 21:
						lib(2,2,2,2);
						
						
						break;
					case 22:
						lib(1,1,1,1);
						break;
					case 23:
						lib(1,1,2,2);
						break;
					case 25:
						if(!bl2)
						{
							c2(2,2,2,2,"/sdcard/OnePlusNFC/2222/libnfc-nxp.conf");
							c2(1,1,1,1,"/sdcard/OnePlusNFC/1111/libnfc-nxp.conf");
							c2(1,1,2,2,"/sdcard/OnePlusNFC/1122/libnfc-nxp.conf");

							bl2=true;
							findViewById(25).setBackground(d(w/16,0xff55aa55));
						}
						else
						{
							bl2=false;
							findViewById(25).setBackground(d(w/16,0));
						}

						break;
					case 31:
						if(!bl)
						{
							bl=true;
							findViewById(31).setBackground(d(w/16,0xff55aa55));
						}
						else
						{
							bl=false;
							findViewById(31).setBackground(d(w/16,0));
						}
						
						break;


					case 41:
						try
						{
							if (Build.PRODUCT.equals("OnePlus3"))
								c("OnePlus3/libnfc-nxp.conf", "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf");
							if (Build.PRODUCT.equals("OnePlus5"))
								c("OnePlus5/libnfc-nxp.conf", "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf");

							s(rw);
							s(rv);
							s(String.format(cp, "/sdcard/OnePlusNFC/" + Build.DEVICE+"/libnfc-nxp.conf",lib));
							s(String.format(chmod, lib));
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
					case 42:
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
					case 51:
						cool(getPackageName());
						
						break;
					case 52:
						try
						{
							String s;
							//s="mqqapi://card/show_pslcard?card_type=group&uin=2243930";
							s="bXFxYXBpOi8vY2FyZC9zaG93X3BzbGNhcmQ/Y2FyZF90eXBlPWdyb3VwJnVpbj0yMjQzOTMw";
							s=new String(android.util.Base64.decode(s,android.util.Base64.DEFAULT));
							startActivity(new Intent(null,Uri.parse(s)));

						}
						catch(Exception e)
						{
							T("打开QQ失败!");
						}
						startActivity(new Intent(null,Uri.parse("mqqapi://card/show_pslcard?card_type=group&uin=2243930")));
						break;
					case 53:
						try
						{
							String s;
							//s="alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https://qr.alipay.com/tsx03791nki4qabwu92vi97";
							s="YWxpcGF5czovL3BsYXRmb3JtYXBpL3N0YXJ0YXBwP3NhSWQ9MTAwMDAwMDcmY2xpZW50VmVyc2lvbj0zLjcuMC4wNzE4JnFyY29kZT1odHRwczovL3FyLmFsaXBheS5jb20vdHN4MDM3OTFua2k0cWFid3U5MnZpOTc";
							s=new String(android.util.Base64.decode(s,android.util.Base64.DEFAULT));
							startActivity(new Intent(null,Uri.parse(s)));

						}
						catch(Exception e)
						{
							T("打开支付宝失败");
						}
						break;
					default:
						if(l0.isShown())finish();
						else recreate();
				}
				break;
			case 1:
				String pn=pi.get(v.getId()%100).packageName;
				try
				{
					if(bl)
					{
						if(r(String.format("pm clear %s",pn)).equals("Success"))
						{
							s(String.format("pm grant %s org.simalliance.openmobileapi.SMARTCARD",pn));
						}
					}
					else
					{
						s(String.format("pm grant %s org.simalliance.openmobileapi.SMARTCARD",pn));
						
					}
					T("授权成功！");
				}
				catch (Exception e)
				{
					T("授权失败！");
				}

				try
				{
					startActivity(getPackageManager().getLaunchIntentForPackage(pn));
				}
				catch(Exception e)
				{
					T("请从该插件的主程序中进入！");
				}
				break;
		}
		
		
	}

	@Override
	public void finish()
	{
		if(l0.isShown())
		super.finish();
		else recreate();
	}
	
	
	void cool(String s)
	{
		Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("https://www.coolapk.com/apk/%s",s)));
		try
		{
			startActivity(i.setPackage("com.coolapk.market"));
		}
		catch (Exception e)
		{
			startActivity(i.setPackage(null));
		}
	}
	
	public  List<PackageInfo> getAppListByPermission(String permissionName){
		List<PackageInfo> appinfos = new ArrayList<PackageInfo>();
		PackageManager mPackageManager = getPackageManager();
		List<PackageInfo>  mPackageInfo =  
			mPackageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);

		for(int i=0;i< mPackageInfo.size(); i++) {
			String[] mPermissions;

			if((mPackageInfo.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 )
			{  
				continue; 
			}          
			
			try {
	            mPermissions = mPackageManager.getPackageInfo(mPackageInfo.get(i).packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
				if( mPermissions != null)
				{
					for(int j=0; j< mPermissions.length; j++)   
					{
						if(permissionName.equals(mPermissions[j])) 
						{
							appinfos.add(mPackageInfo.get(i));
						}
					}
				}
			}catch (Exception e) {
	        }
		}
		return appinfos;
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
	
	void c2(int $1,int $2,int $3,int $4, String fn) 
	{
		try
		{
        File f = new File(fn);
        if (f.exists())
            f.delete();
        f = new File(fn);
		new File(fn.substring(0,fn.lastIndexOf("/"))).mkdirs();
        f.createNewFile();
		InputStream is=new FileInputStream(lib);
        OutputStream O = new FileOutputStream(fn);
		
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String st = null;
		while ((st = br.readLine()) != null) 
		{
			if(st.indexOf("NXP_DEFAULT_SE=0x0")!=-1)
				st="NXP_DEFAULT_SE=0x0"+$1;
			if(st.indexOf("DEFAULT_AID_ROUTE=0x0")!=-1)
				st="DEFAULT_AID_ROUTE=0x0"+$2;
			if(st.indexOf("DEFAULT_DESFIRE_ROUTE=0x0")!=-1)
				st="DEFAULT_DESFIRE_ROUTE=0x0"+$3;
			if(st.indexOf("DEFAULT_MIFARE_CLT_ROUTE=0x0")!=-1)
				st="DEFAULT_MIFARE_CLT_ROUTE=0x0"+$4;
			
			O.write(st.getBytes());
			O.write("\n".getBytes());
		}
		
        O.flush();
        O.close();
		}
		catch(Exception e)
		{}
    }
	
	void lib2(int $1,int $2,int $3,int $4)
	{
		try
		{
			s(rw);
			s(rv);
			s(String.format(cp,"/sdcard/OnePlusNFC/"+$1+$2+$3+$4+"/libnfc-nxp.conf",lib));
			s(String.format(chmod, lib));
			s("service call nfc 7");
			s("service call nfc 6");
			s("service call nfc 7");
			s("service call nfc 8");
			T("切换成功！快去试试吧！");
			recreate();
		}
		catch (IOException e)
		{
			T("切换失败！"+e.getMessage());
		}
	}
	void lib(int $1,int $2,int $3,int $4)
	{
		if(bl2)
			lib2($1,$2,$3,$4);
		else
		try
		{
			s(rw);
			s(rv);
			s(new StringBuilder(String.format(sed1,$1)).append(lib).toString());
			s(new StringBuilder(String.format(sed2,$2)).append(lib).toString());
			s(new StringBuilder(String.format(sed3,$3)).append(lib).toString());
			s(new StringBuilder(String.format(sed4,$4)).append(lib).toString());
			s("service call nfc 7");
			s("service call nfc 6");
			s("service call nfc 7");
			s("service call nfc 8");
			T("切换成功！快去试试吧！");
			recreate();
		}
		catch (IOException e)
		{
			T("切换失败！");
		}
	}
	
	String cf()
	{
		try
		{
			String 
				n=new StringBuilder("sed -n /%s/p ").append(lib).toString(),
				n1="NXP_DEFAULT_SE=0x0",
				n2="DEFAULT_AID_ROUTE=0x0",
				n3="DEFAULT_DESFIRE_ROUTE=0x0",
				n4="DEFAULT_MIFARE_CLT_ROUTE=0x0"
			;
			n = r(String.format(n, n1)).replace(n1, "") +
				r(String.format(n, n2)).replace(n2, "") +
				r(String.format(n, n3)).replace(n3, "") +
				r(String.format(n, n4)).replace(n4, "");
				
			if(n.length()!=4)
				return "配置已损坏，请修复！";
			switch(n)
			{
				case "1111":
					n="默认配置/"+n;
					break;
				case "2222":
					n="公交配置/"+n;
					break;
				default:
					n="其它配置/"+n;
			}
			return n;
			
		}
		catch (IOException e)
		{}
		return "获取数据失败！";
	}
	boolean oat()
	{
		return new File("/system/priv-app/SmartcardService/oat").exists();
	}
	
	void t(CharSequence s)
	{
		t = new TextView(this);
		t.setText(s); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setTextIsSelectable(true);
		t.setGravity(Gravity.CENTER);
		ll.addView(t);
	}
	void t(CharSequence s,LinearLayout l)
	{
		t = new TextView(this);
		t.setText(s); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setTextIsSelectable(true);
		t.setGravity(Gravity.CENTER);
		l.addView(t);
	}
	
	void b(int i,CharSequence s,LinearLayout l)
	{
		b=new Button(this);
		b.setId(i);
		b.setText(s);
		b.setTextColor(0xffffffff);
		b.setTextSize(z);
		b.setBackgroundDrawable(d(w/16,0xffff5556));
		b.setPadding(p,p,p,p);
		b.setLayoutParams(lp);
		b.setOnClickListener(this);
		l.addView(b);
	}
	void T(CharSequence s)
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
