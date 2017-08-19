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

    int x0 = 450;
    int y0 = -530;
    int ylimit= -760;
    int xdelete = 0;
    int ydelete = 610;
    long time1 = 0;
    long time2 = 0;
    long Timer = 0;
    int ybind = 870;
    int xbind = 0;


    WindowManager wm;
    FrameLayout ll;
    FrameLayout llt;

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

        ll = new FrameLayout(this);
        LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackground(getResources().getDrawable(R.drawable.icone));
        ll.setLayoutParams(layoutParameters);

        final LayoutParams parametersll = new LayoutParams(
                180, 180, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        parametersll.gravity = Gravity.CENTER | Gravity.CENTER;

        parametersll.x = 450;
        parametersll.y = -530;

        wm.addView(ll, parametersll);

        llt = new FrameLayout(this);
        layoutParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        llt.setBackground(getResources().getDrawable(R.drawable.croix));
        llt.setLayoutParams(layoutParameters);

        final LayoutParams parametersllt = new LayoutParams(
                150, 150, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        parametersllt.gravity = Gravity.CENTER | Gravity.CENTER;

        parametersllt.x = 0;
        parametersllt.y = 610;
        llt.setVisibility(View.INVISIBLE);

        wm.addView(llt, parametersllt);


        ll.setOnTouchListener(new View.OnTouchListener() {
            LayoutParams updatedParameters = parametersll;
            double x;
            double y;
            double pressedX;
            double pressedY;


            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        time1 = System.currentTimeMillis();

                        updatedParameters.height = 170;
                        updatedParameters.width = 170;

                        x = updatedParameters.x;
                        y = updatedParameters.y;

                        parametersllt.x = 0;
                        parametersllt.y = 610;

                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        wm.updateViewLayout(ll, updatedParameters);

                        llt.setVisibility(View.VISIBLE);
                        animate(xbind, parametersllt.x, ybind, parametersllt.y, 400, llt, false);

                        break;

                    case MotionEvent.ACTION_MOVE:

                        parametersllt.height = 150;
                        parametersllt.width = 150;

                        updatedParameters.x = (int) (x + (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - pressedY));

                        parametersllt.x = ( updatedParameters.x / 10);
                        parametersllt.y = ( updatedParameters.y / 10) + 610;

                        if (updatedParameters.x <= xdelete + 200 && updatedParameters.x >= xdelete - 200 && updatedParameters.y <= ydelete + 200 && updatedParameters.y >= ydelete - 200) {

                            updatedParameters.x = parametersllt.x;
                            updatedParameters.y = parametersllt.y;

                            parametersllt.height = 200;
                            parametersllt.width = 200;

                        }

                        wm.updateViewLayout(ll, updatedParameters);
                        wm.updateViewLayout(llt, parametersllt);


                    default:
                        break;

                    case MotionEvent.ACTION_UP:

                        animate(parametersllt.x, xbind, parametersllt.y, ybind, 300, llt, true);

                        time2 = System.currentTimeMillis();

                        Timer = time2 - time1;

                        updatedParameters.height = 180;
                        updatedParameters.width = 180;

                        if (Timer < 200) {

                            double DistInit;
                            double Vitesse;

                            DistInit = (Math.sqrt((updatedParameters.y - y) * (updatedParameters.y - y) + (updatedParameters.x - x) * (updatedParameters.x - x)));
                            Vitesse = (DistInit / Timer);

                            double a;
                            double b;

                            a = (updatedParameters.y - y) / (updatedParameters.x - x);
                            b = (y - a * x);

                            if (y > updatedParameters.y) {x = (int) ((ylimit - b) / a);}
                            if (y < updatedParameters.y) {
                                x = (int) ((-ylimit - b) / a);
                            }
                            if (x > updatedParameters.x) {
                                y = (int) (a * x0 + b);
                            }
                            if (x < updatedParameters.x) {
                                y = (int) (a * -x0 + b);
                            }

                            if (y <= ylimit) {
                                y = ylimit;
                                if (x >= 0) {
                                    x = x0;
                                }
                                if (x <= 0) {
                                    x = -x0;
                                }
                            }
                            if (y >= -ylimit) {
                                y = -ylimit;
                                if (x >= 0) {
                                    x = x0;
                                }
                                if (x <= 0) {
                                    x = -x0;
                                }
                            }
                            if (x >= x0) {
                                x = x0;
                            }
                            if (x <= -x0) {
                                x = -x0;
                            }

                            double DistFinal;
                            double Timanim;

                            DistFinal = (Math.sqrt((y - updatedParameters.y) * (y - updatedParameters.y) + (x - updatedParameters.x) * (x - updatedParameters.x)));
                            Timanim = ((DistFinal / Vitesse) / 4 + 20);

                            animate(updatedParameters.x, (int) x, updatedParameters.y, (int) y, (int) Timanim, ll, false);

                        } else {

                            if (updatedParameters.x == parametersllt.x && updatedParameters.y == parametersllt.y) {

                                wm.removeView(ll);
                                stopSelf();
                                System.exit(0);
                            }

                            if (updatedParameters.x != x0 && updatedParameters.x >= 0) {
                                animate(updatedParameters.x, x0, updatedParameters.y, updatedParameters.y, 200, ll, false);
                            }
                            if (updatedParameters.x != -x0 && updatedParameters.x < 0) {
                                animate(updatedParameters.x, -x0, updatedParameters.y, updatedParameters.y, 200, ll, false);
                            }

                            updatedParameters.height = 180;
                            updatedParameters.width = 180;

                            wm.updateViewLayout(ll, updatedParameters);

                        }

                        break;

                }

                return false;
            }

        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void animate(int startX, int endX, int startY, int endY, int time, final FrameLayout layout, boolean remove) {

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("x", startX, endX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofInt("y", startY, endY);

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

        translator.setDuration(time);
        if (remove == true) {
            translator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        translator.start();
    }

}
