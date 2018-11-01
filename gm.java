/**
* Created by SungBin on 2018-10-21.
*/

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import android.content.ClipboardManager;

public class gm extends Application{

    public static final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static Context ctx;

    @Override
    public void onCreate(){
        super.onCreate();
        ctx = getApplicationContext();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    public static void createFolder(String name){
        new File(sdcard+"/WordChain Online/"+name+"/").mkdirs();
    }

    public static String read(String name) {
        try {
            File file = new File(sdcard+"/WordChain Online/"+name+".txt");
            if(!file.exists()) return "";
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            String line = "";
            while ((line = br.readLine()) != null) {
                str += "\n" + line;
            }
            fis.close();
            isr.close();
            br.close();
            return str;
        } catch (Exception e) {
            toast(e.toString());
        }
        return "";
    }

    public static void save(String name, String str) {
        try {
            File file = new File(sdcard+"/WordChain Online/"+name+".txt");
            FileOutputStream fos = new java.io.FileOutputStream(file);
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    public static String getHtml(String link) {
        try {
            URLConnection con = new URL(link).openConnection();
            if (con != null) {
                con.setConnectTimeout(5000);
                con.setUseCaches(false);
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String str = br.readLine();
                String str2 = "";
                while (true) {
                    str2 = br.readLine();
                    if (str2 != null) {
                        str = str + "\n" + str2;
                    } else {
                        br.close();
                        isr.close();
                        return str;
                    }
                }
            }
        } catch (Exception e) {
            toast(e.toString());
        }
        return null;
    }

    public static void toast(String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context ctx2, String msg) {
        Toast.makeText(ctx2, msg, Toast.LENGTH_SHORT).show();
    }

    public static int Number(String num){
        return Integer.parseInt(num);
    }

    public static boolean toBoolean(String tf){
        return tf.equals("true");
    }

    public static int dip2px(Context ctx, int dips){
        return (int)Math.ceil(dips*ctx.getResources().getDisplayMetrics().density);
    }

    public static String readData(Context ctx, String name, String _null){
        SharedPreferences pref = ctx.getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(name, _null);
    }

    public static void saveData(Context ctx, String name, String value){
        SharedPreferences pref = ctx.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(name, value);
        editor.commit();
    }

    public static void copy(Context ctx, String text){
      ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData clip = ClipData.newPlainText("label", text);
      clipboard.setPrimaryClip(clip);
      Toast.makeText(ctx, "클립보드에 복사되었습니다.", 1).show();
  }

    public static BitmapDrawable getImageDrawable(String link){
      try {
        URL url = new URL(link);
        URLConnection con = url.openConnection();
        con.setUseCaches(false);
        con.setConnectTimeout(5000);
        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
        BitmapDrawable image = new BitmapDrawable(BitmapFactory.decodeStream(bis));
        bis.close();
        return image;
      }
      catch(Exception e) {
        Toast.makeText(ctx, e.toString(), 1).show();
     }
     return new BitmapDrawable();
    }
}
