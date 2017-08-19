package xeftax.kvtwcavz;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.lang.Integer;
import java.lang.Math;
import java.lang.Override;
import java.lang.System;


public class FloatingWindow extends Service {

    int xbord = 450;
    int ybord = 762;
    long time1 = 0;
    long time2 = 0;
    long Timer = 0;
    int ycroixinit = 870;
    int ycroixfinal = 610;
    int xcroixinit = 0;
    boolean finanim = false;



    WindowManager wm;
    FrameLayout Bulle;
    FrameLayout Croix;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);


        Bulle = new FrameLayout(this);
        LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Bulle.setBackground(getResources().getDrawable(R.drawable.icone));
        Bulle.setLayoutParams(layoutParameters);


        final LayoutParams paramsBubble = new LayoutParams(
                180, 180, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        paramsBubble.gravity = Gravity.CENTER | Gravity.CENTER;


        paramsBubble.x = 450;
        paramsBubble.y = -530;

        wm.addView(Bulle, paramsBubble);



        Croix = new FrameLayout(this);
        layoutParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Croix.setBackground(getResources().getDrawable(R.drawable.croix));
        Croix.setLayoutParams(layoutParameters);


        final LayoutParams paramsCroix = new LayoutParams(
                150, 150, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        paramsCroix.gravity = Gravity.CENTER | Gravity.CENTER;


        paramsCroix.x = xcroixinit;
        paramsCroix.y = ycroixinit;

        Croix.setVisibility(View.INVISIBLE);

        wm.addView(Croix, paramsCroix);



        Bulle.setOnTouchListener(new View.OnTouchListener() {

            LayoutParams modifparamsBulle = paramsBubble;
            LayoutParams modifparamsCroix = paramsCroix;


            double Xdepart;
            double Ydepart;
            double pressedX;
            double pressedY;



            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        time1 = System.currentTimeMillis();



                        modifparamsBulle.height = 170;

                        Xdepart = modifparamsBulle.x;
                        Ydepart = modifparamsBulle.y;

                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        homotecie(modifparamsBulle.height, 0, Bulle);



                        Croix.setVisibility(View.VISIBLE);

                        translation(xcroixinit, ycroixfinal, 400, Croix, false);

                        break;

                    case MotionEvent.ACTION_MOVE:

                        modifparamsBulle.x = (int) (Xdepart + (event.getRawX() - pressedX));
                        modifparamsBulle.y = (int) (Ydepart + (event.getRawY() - pressedY));

                        if (modifparamsBulle.x >= xbord){modifparamsBulle.x = xbord + 5;}
                        if (modifparamsBulle.x <= -xbord){modifparamsBulle.x = -xbord -5;}
                        if (modifparamsBulle.y >= ybord){modifparamsBulle.y = ybord + 5;}
                        if (modifparamsBulle.y <= -ybord){modifparamsBulle.y = -ybord - 5;}



                        modifparamsCroix.x = (modifparamsBulle.x / 10);
                        modifparamsCroix.y = (modifparamsBulle.y / 10) + ycroixfinal;

                        modifparamsCroix.height = 150;
                        modifparamsCroix.width = 150;



                            if (modifparamsBulle.x <= xcroixinit + 200 && modifparamsBulle.x >= xcroixinit - 200 && modifparamsBulle.y <= ycroixfinal + 200 && modifparamsBulle.y >= ycroixfinal - 200) {

                            modifparamsBulle.x = modifparamsCroix.x;
                            modifparamsBulle.y = modifparamsCroix.y;

                            modifparamsCroix.height = 200;
                            modifparamsCroix.width = 200;

                            }



                        translation(modifparamsBulle.x, modifparamsBulle.y, 0, Bulle, false);

                        homotecie(modifparamsCroix.height, 0, Croix);
                        if (finanim == true) {translation(modifparamsCroix.x, modifparamsCroix.y, 200, Croix, false);}


                    default:
                        break;

                    case MotionEvent.ACTION_UP:

                        time2 = System.currentTimeMillis();
                        Timer = time2 - time1;


                        modifparamsBulle.height = 180;
                        modifparamsBulle.width = 180;


                        translation(xcroixinit, ycroixinit, 300, Croix, true);



                        if (Timer < 200) {

                            double DistInit;
                            double Vitesse;

                            DistInit = (Math.sqrt((modifparamsBulle.y - Ydepart) * (modifparamsBulle.y - Ydepart) + (modifparamsBulle.x - Xdepart) * (modifparamsBulle.x - Xdepart)));
                            Vitesse = (DistInit / Timer);



                            double a;
                            double b;

                            a = (modifparamsBulle.y - Ydepart) / (modifparamsBulle.x - Xdepart);
                            b = (Ydepart - a * Xdepart);



                            if (Ydepart > modifparamsBulle.y) {Xdepart = (int) ((-ybord - b) / a);}
                            if (Ydepart < modifparamsBulle.y) {Xdepart = (int) ((ybord - b) / a);}
                            if (Xdepart > modifparamsBulle.x) {Ydepart = (int) (a * xbord + b);}
                            if (Xdepart < modifparamsBulle.x) {Ydepart = (int) (a * -xbord + b);}



                            if (Ydepart <= -ybord) {
                                Ydepart = -ybord;
                                if (Xdepart >= 0) {
                                    Xdepart = xbord;}
                                if (Xdepart <= 0) {
                                    Xdepart = -xbord;}
                            }
                            if (Ydepart >= ybord) {
                                Ydepart = ybord;
                                if (Xdepart >= 0) {
                                    Xdepart = xbord;}
                                if (Xdepart <= 0) {
                                    Xdepart = -xbord;}
                            }


                            if (Xdepart >= xbord) {Xdepart = xbord;}
                            if (Xdepart <= -xbord) {Xdepart = -xbord;}



                            double DistFinal;
                            double Timanim;

                            DistFinal = (Math.sqrt((Ydepart - modifparamsBulle.y) * (Ydepart - modifparamsBulle.y) + (Xdepart - modifparamsBulle.x) * (Xdepart - modifparamsBulle.x)));
                            Timanim = ((DistFinal / Vitesse) / 4 + 20);



                            translation((int) Xdepart, (int) Ydepart, (int) Timanim, Bulle, false);

                        } else {

                            if (modifparamsBulle.x == modifparamsCroix.x && modifparamsBulle.y == modifparamsCroix.y) {

                                stopSelf();
                                System.exit(0);
                            }



                            if (modifparamsBulle.x != xbord && modifparamsBulle.x >= 0) {translation(xbord, modifparamsBulle.y, 200, Bulle, false);}
                            if (modifparamsBulle.x != -xbord && modifparamsBulle.x < 0) {translation(-xbord, modifparamsBulle.y, 200, Bulle, false);}



                            modifparamsBulle.height = 180;
                            modifparamsBulle.width = 180;



                            translation(modifparamsBulle.x, modifparamsBulle.y, 0, Bulle, false);

                        }

                        break;

                }

                return false;
            }

        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void translation(int destinationX, int destinationY, int temps, final FrameLayout layout, final boolean invisibilite) {

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) layout.getLayoutParams();

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("x", layoutParams.x, destinationX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofInt("y", layoutParams.y, destinationY);

        ValueAnimator translator = ValueAnimator.ofPropertyValuesHolder(pvhX, pvhY);

        translator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) layout.getLayoutParams();
                layoutParams.x = (Integer) valueAnimator.getAnimatedValue("x");
                layoutParams.y = (Integer) valueAnimator.getAnimatedValue("y");
                wm.updateViewLayout(layout, layoutParams);
            }
        });

        translator.setDuration(temps);
        translator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationEnd(Animator animator) {
                if (invisibilite == true) {
                    layout.setVisibility(View.INVISIBLE);
                    finanim = false;
                } else {
                    finanim = true;}
            }
            @Override
            public void onAnimationCancel (Animator animator){}
            @Override
            public void onAnimationRepeat (Animator animator){}
        });
        translator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void homotecie(int taillefinal, int temps, final FrameLayout layout) {

        LayoutParams layoutParams = (LayoutParams) layout.getLayoutParams();

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("taille", layoutParams.width, taillefinal);

        ValueAnimator homotetor = ValueAnimator.ofPropertyValuesHolder(pvhX);

        homotetor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams = (LayoutParams) layout.getLayoutParams();
                layoutParams.width = (Integer) valueAnimator.getAnimatedValue("taille");
                layoutParams.height = (Integer) valueAnimator.getAnimatedValue("taille");
                wm.updateViewLayout(layout, layoutParams);
            }
        });

        homotetor.setDuration(temps);
        homotetor.start();
    }

}
