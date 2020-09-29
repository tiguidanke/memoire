package com.example.memo.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.memo.App;
import com.example.memo.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.VISIBLE;


public class Utils {

    private Context _context;
   // private InterstitialAd mInterstitial;
    /*private InterAdListener interAdListener;*/


    // constructor
    public Utils() {
        this._context = App.getAppContext();
    }


    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (activeNetwork.isConnected())
                    status = "Connecté";
                else
                    status = "Wifi activé, Pas d'accés internet";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (activeNetwork.isConnected())
                    status = "Connecté";
                else
                    status = "Données mobiles activées, Pas d'accés internet";
                return status;
            }
        } else {
            status = "Pas d'accés internet";
            return status;
        }
        return status;
    }
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(App.getAppContext()).load(imageURL).error(Glide.with(App.getAppContext()).load(R.drawable.logo)).into(imageView);
    }
    public static void loadResource(ImageView imageView, int imageURL) {
        Glide.with(App.getAppContext()).load(imageURL).error(Glide.with(App.getAppContext()).load(R.drawable.logo)).into(imageView);
    }
    public static void SnapText(TextView textView, String text) {
        if (text != null) {
            Spannable spannable = new SpannableString(text);
            spannable.setSpan(new BackgroundColorSpan(0xFF1A5D80), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannable);
        }
    }

    public void viewGoneAnimator(View view) {

        view.animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });

    }

    public void viewVisibleAnimator(View view) {

        view.animate()
                .alpha(1f)
                .setDuration(10)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(VISIBLE);
                    }
                });

    }
    public static void inform(Activity mView, String string, int color) {
        Snackbar snack = Snackbar.make(mView.findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        view.setBackgroundColor(mView.getResources().getColor(color));
        snack.show();
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

    public static String format(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

   /* public void forceRTLIfSupported(Window window) {
        if (_context.getResources().getString(R.string.isRTL).equals("true")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
    }*/

    public void setStatusColor(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void removeStatusColor(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.BLACK);
        }
    }
 /*   public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(App.getAppContext()).load(imageURL).error(Glide.with(App.getAppContext()).load(R.drawable.logo)).into(imageView);
    }
    public static void loadResource(ImageView imageView, int imageURL) {
        Glide.with(App.getAppContext()).load(imageURL).error(Glide.with(App.getAppContext()).load(R.drawable.logo)).into(imageView);
    }*/

    public void showToast(String msg) {
        Toast.makeText(_context, msg, Toast.LENGTH_LONG).show();
    }

    /*private void showPersonalizedAds(LinearLayout linearLayout) {
        if (Constant.isBannerAd) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdUnitId(Constant.ad_banner_id);
            adView.setAdSize(AdSize.BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    private void showNonPersonalizedAds(LinearLayout linearLayout) {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        if (Constant.isBannerAd) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
            adView.setAdUnitId(Constant.ad_banner_id);
            adView.setAdSize(AdSize.BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    public void showBannerAd(LinearLayout linearLayout) {
        if (isNetworkAvailable(_context)) {
            if (ConsentInformation.getInstance(_context).getConsentStatus() == ConsentStatus.NON_PERSONALIZED) {
                showNonPersonalizedAds(linearLayout);
            } else {
                showPersonalizedAds(linearLayout);
            }
        }
    }
*/
    /*public void loadInter() {
        mInterstitial = new InterstitialAd(_context);
        mInterstitial.setAdUnitId(new SharedPref().getApiDetails().getInterstitalAdId());
        AdRequest.Builder builder = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            builder.addTestDevice("A874C908DC31771ADE4593386A366C66");
            builder.addTestDevice("7E458935F6E8B9A885980BE84165B466");
            builder.addTestDevice("FAE39991490ADEF5214BC3CC2C8F8AC2");
        }
        GDPRChecker.Request request = GDPRChecker.getRequest();
        if (request == GDPRChecker.Request.NON_PERSONALIZED) {
            // load non Personalized ads
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            builder.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        }
        mInterstitial.loadAd(builder.build());
    }

    public void showInterAd() {
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
        //loadInter();
    }*/

    public GradientDrawable getGradientDrawable(int first, int second) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{
                first,
                second
        });
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.mutate();
        gd.setCornerRadius(10);
        return gd;
    }



    /*public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String hourString = "";
        String secondsString = "";
        String minutesString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        long temp_milli = (long) calculateTime(Constant.arrayList_radio.get(Constant.pos).getDuration());
        int temp_hour = (int) (temp_milli / (1000 * 60 * 60));
        if (temp_hour != 0) {
            hourString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        // Prepending 0 to minutes if it is one digit
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        finalTimerString = hourString + minutesString + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }*/

    public static String milliSecondsToTimerDownload(long milliseconds) {
        String finalTimerString;
        String hourString = "";
        String secondsString;
        String minutesString;

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there

        if (hours != 0) {
            hourString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        // Prepending 0 to minutes if it is one digit
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        finalTimerString = hourString + minutesString + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        double percentage;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return (int) percentage;
    }

    public static long getSeekFromPercentage(int percentage, long totalDuration) {

        long currentSeconds;
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        currentSeconds = (percentage * totalSeconds) / 100;

        // return percentage
        return currentSeconds * 1000;
    }

    public static int calculateTime(String duration) {
        int time, min, sec, hr = 0;
        try {
            StringTokenizer st = new StringTokenizer(duration, ".");
            if (st.countTokens() == 3) {
                hr = Integer.parseInt(st.nextToken());
            }
            min = Integer.parseInt(st.nextToken());
            sec = Integer.parseInt(st.nextToken());
        } catch (Exception e) {
            StringTokenizer st = new StringTokenizer(duration, ":");
            if (st.countTokens() == 3) {
                hr = Integer.parseInt(st.nextToken());
            }
            min = Integer.parseInt(st.nextToken());
            sec = Integer.parseInt(st.nextToken());
        }
        time = ((hr * 3600) + (min * 60) + sec) * 1000;
        return time;
    }

    public static long convert_long(String s) {

        long ms = 0;
        Pattern p;
        if (s.contains(("\\:"))) {
            p = Pattern.compile("(\\d+):(\\d+)");
        } else {
            p = Pattern.compile("(\\d+).(\\d+)");
        }
        Matcher m = p.matcher(s);
        if (m.matches()) {
            int h = Integer.parseInt(Objects.requireNonNull(m.group(1)));
            int min = Integer.parseInt(Objects.requireNonNull(m.group(2)));
            // int sec = Integer.parseInt(m.group(2));
            ms = (long) h * 60 * 60 * 1000 + min * 60 * 1000;
        }
        return ms;
    }

    public static void deleteCache(Context context, Activity activity) {
        try {
            File dir = context.getCacheDir();
            if (deleteDir(dir)) {
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}