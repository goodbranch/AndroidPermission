package www.branch.com.permission;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Branch on 16/7/24.
 */
public class PreferenceUtil {

  private static volatile PreferenceUtil mInstance;

  private SharedPreferences mSharedPreferences;

  public static PreferenceUtil getInstance(Context context) {

    if (mInstance == null) {
      synchronized (PreferenceUtil.class) {
        if (mInstance == null) {
          mInstance = new PreferenceUtil(context);
        }

      }

    }
    return mInstance;
  }

  private PreferenceUtil(Context context) {

    mSharedPreferences = context.getSharedPreferences("permission", Context.MODE_PRIVATE);

  }


  public SharedPreferences getSharedPreferences() {
    return mSharedPreferences;
  }
}
