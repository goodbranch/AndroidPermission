package www.branch.com.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by branch on 2016-7-24.
 */
public class PermissionUtil {

  public static final int REQUESTCODE = 110;


  /**
   * 一组权限
   */
  public static boolean checkPermission(int[] grantResults) {
    if (grantResults == null) {
      return false;
    }

    for (int i = 0; i < grantResults.length; i++) {
      if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }

    return true;
  }


  public static boolean hasPhoneStatePermission(Context mContext) {
    return checkHasPermission(mContext, Manifest.permission.READ_PHONE_STATE);
  }

  public static boolean hasLocationPermission(Context mContext) {
    return checkHasPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
  }

  public static boolean hasExternalPermission(Context mContext) {
    return checkHasPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
  }

  public static boolean hasCameraPermission(Context mContext) {
    return checkHasPermission(mContext, Manifest.permission.CAMERA);
  }

  /**
   * @return true 已经有相应的权限，不用弹窗  false 没有权限会弹窗
   */
  public static boolean requestPhoneStatePermission(Activity activity, int requestCode) {
    if (!hasPhoneStatePermission(activity)) {
      openPermissionDialog(activity, requestCode, Manifest.permission.READ_PHONE_STATE);

      return false;
    }
    return true;
  }


  /**
   * @return true 已经有相应的权限，不用弹窗  false 没有权限会弹窗
   */
  public static boolean requestExternalPermission(Activity activity, int requestCode) {
    if (!hasExternalPermission(activity)) {
      openPermissionDialog(activity, requestCode, Manifest.permission.WRITE_EXTERNAL_STORAGE);

      return false;
    }
    return true;
  }

  /**
   * @return true 已经有相应的权限，不用弹窗  false 没有权限会弹窗
   */
  public static boolean requestLocationPermission(Activity activity, int requestCode) {

    if (!hasLocationPermission(activity)) {
      openPermissionDialog(activity, requestCode, Manifest.permission.ACCESS_FINE_LOCATION);

      return false;
    }
    return true;
  }

  /**
   * @return true 已经有相应的权限，不用弹窗  false 没有权限会弹窗
   */
  public static boolean requestCameraPermission(Activity activity, int requestCode) {
    if (!hasCameraPermission(activity)) {
      openPermissionDialog(activity, requestCode, Manifest.permission.CAMERA);
      return false;
    }

    return true;
  }


  public static boolean checkHasPermission(Context mContext, String permission) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }

    boolean[] grantends = checkHasPermission(mContext, new String[]{permission});
    if (grantends == null || grantends.length == 0) {
      return false;
    }
    return grantends[0];
  }

  public static boolean[] checkHasPermission(Context mContext, String... permissions) {
    if (permissions == null || permissions.length == 0) {
      return null;
    }
    boolean[] result = new boolean[permissions.length];
    if (mContext == null) {
      return result;
    }
    for (int i = 0; i < permissions.length; i++) {
      if (!TextUtils.isEmpty(permissions[i]) && ContextCompat.checkSelfPermission(mContext, permissions[i]) == PackageManager.PERMISSION_GRANTED) {
        result[i] = true;
      } else {
        result[i] = false;
      }
    }

    return result;
  }


  public static void openPermissionDialog(Activity activity, int requestCode, String permission) {
    if (activity == null || TextUtils.isEmpty(permission)) {

      return;
    }
    //没有相应的权限
    if (!requestPermission(activity, requestCode, new String[]{permission})) {

    }
  }


  public static boolean requestPermission(Activity activity, int requestCode, String... permissions) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return false;
    }

    if (activity == null || permissions == null || permissions.length == 0) {
      return false;
    }

    ArrayList<String> shouldList = new ArrayList<String>(permissions.length);

    for (String p : permissions) {

      boolean should = ActivityCompat.shouldShowRequestPermissionRationale(activity, p);
      Log.e("branch", "requestPermission->" + should + " | " + p);
      if (should) {
        //是否需要给用户一个解释,当弹窗被拒绝后，should会变成true

        Toast.makeText(activity, "被拒绝后给用户一个解释", Toast.LENGTH_SHORT).show();

      } else {
        shouldList.add(p);
      }
    }


    if (!shouldList.isEmpty()) {

      String[] ps = new String[shouldList.size()];

      shouldList.toArray(ps);

      ActivityCompat.requestPermissions(activity, ps, requestCode);
      return true;
    }

    return false;
  }

}
