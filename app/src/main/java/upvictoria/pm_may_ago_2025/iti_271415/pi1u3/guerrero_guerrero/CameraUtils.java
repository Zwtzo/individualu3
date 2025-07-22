package upvictoria.pm_may_ago_2025.iti_271415.pi1u3.guerrero_guerrero;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;

public class CameraUtils {

    public static boolean checkCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.CAMERA}, requestCode);
    }

    public static void showPermissionDeniedMessage(Activity activity) {
        Toast.makeText(activity, "Se requiere el permiso de cámara para usar esta función", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}
