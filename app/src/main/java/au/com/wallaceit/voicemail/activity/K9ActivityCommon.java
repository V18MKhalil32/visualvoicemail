package au.com.wallaceit.voicemail.activity;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;

import au.com.wallaceit.voicemail.VisualVoicemail;
import au.com.wallaceit.voicemail.activity.*;
import au.com.wallaceit.voicemail.activity.K9Activity;
import au.com.wallaceit.voicemail.activity.K9ListActivity;
import au.com.wallaceit.voicemail.activity.misc.SwipeGestureDetector;
import au.com.wallaceit.voicemail.activity.misc.SwipeGestureDetector.OnSwipeGestureListener;


/**
 * This class implements functionality common to most activities used in K-9 Mail.
 *
 * @see K9Activity
 * @see K9ListActivity
 */
public class K9ActivityCommon {
    /**
     * Creates a new instance of {@link au.com.wallaceit.voicemail.activity.K9ActivityCommon} bound to the specified activity.
     *
     * @param activity
     *         The {@link Activity} the returned {@code K9ActivityCommon} instance will be bound to.
     *
     * @return The {@link au.com.wallaceit.voicemail.activity.K9ActivityCommon} instance that will provide the base functionality of the
     *         "VisualVoicemail" activities.
     */
    public static au.com.wallaceit.voicemail.activity.K9ActivityCommon newInstance(Activity activity) {
        return new au.com.wallaceit.voicemail.activity.K9ActivityCommon(activity);
    }

    public static void setLanguage(Context context, String language) {
        Locale locale;
        if (TextUtils.isEmpty(language)) {
            locale = Locale.getDefault();
        } else if (language.length() == 5 && language.charAt(2) == '_') {
            // language is in the form: en_US
            locale = new Locale(language.substring(0, 2), language.substring(3));
        } else {
            locale = new Locale(language);
        }

        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = context.getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


    /**
     * Base activities need to implement this interface.
     *
     * <p>The implementing class simply has to call through to the implementation of these methods
     * in {@link au.com.wallaceit.voicemail.activity.K9ActivityCommon}.</p>
     */
    public interface K9ActivityMagic {
        void setupGestureDetector(OnSwipeGestureListener listener);
    }


    private Activity mActivity;
    private GestureDetector mGestureDetector;


    private K9ActivityCommon(Activity activity) {
        mActivity = activity;
        setLanguage(mActivity, VisualVoicemail.getK9Language());
        mActivity.setTheme(VisualVoicemail.getK9ThemeResourceId());
    }

    /**
     * Call this before calling {@code super.dispatchTouchEvent(MotionEvent)}.
     */
    public void preDispatchTouchEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
    }

    /**
     * Get the background color of the theme used for this activity.
     *
     * @return The background color of the current theme.
     */
    public int getThemeBackgroundColor() {
        TypedArray array = mActivity.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.colorBackground });

        int backgroundColor = array.getColor(0, 0xFF00FF);

        array.recycle();

        return backgroundColor;
    }

    /**
     * Call this if you wish to use the swipe gesture detector.
     *
     * @param listener
     *         A listener that will be notified if a left to right or right to left swipe has been
     *         detected.
     */
    public void setupGestureDetector(OnSwipeGestureListener listener) {
        mGestureDetector = new GestureDetector(mActivity,
                new SwipeGestureDetector(mActivity, listener));
    }
}
