package www.branch.com.permission;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.location).setOnClickListener(this);
    findViewById(R.id.phone).setOnClickListener(this);
    findViewById(R.id.camera).setOnClickListener(this);
    findViewById(R.id.storage).setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.location:

        if (PermissionUtil.hasLocationPermission(getApplicationContext())) {

          Toast.makeText(getApplicationContext(), "已经有定位权限", Toast.LENGTH_SHORT).show();
        } else {
          PermissionUtil.requestLocationPermission(this, PermissionUtil.REQUESTCODE);

        }

        break;
      case R.id.phone:
        if (PermissionUtil.hasPhoneStatePermission(getApplicationContext())) {

          Toast.makeText(getApplicationContext(), "已经有拨打电话权限", Toast.LENGTH_SHORT).show();
        } else {
          PermissionUtil.requestPhoneStatePermission(this, PermissionUtil.REQUESTCODE);

        }
        break;
      case R.id.camera:
        if (PermissionUtil.hasCameraPermission(getApplicationContext())) {

          Toast.makeText(getApplicationContext(), "已经有相机权限", Toast.LENGTH_SHORT).show();
        } else {
          PermissionUtil.requestCameraPermission(this, PermissionUtil.REQUESTCODE);

        }
        break;
      case R.id.storage:
        if (PermissionUtil.hasExternalPermission(getApplicationContext())) {

          Toast.makeText(getApplicationContext(), "已经有外置存储卡权限", Toast.LENGTH_SHORT).show();
        } else {
          PermissionUtil.requestExternalPermission(this, PermissionUtil.REQUESTCODE);

        }
        break;
    }
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionUtil.checkPermission(grantResults)) {
      //如果用户授权
      Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(getApplicationContext(), "拒绝授权", Toast.LENGTH_SHORT).show();
    }
  }
}
