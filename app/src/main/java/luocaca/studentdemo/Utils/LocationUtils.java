package luocaca.studentdemo.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by Administrator on 2018/1/21 0021.
 */

public class LocationUtils implements LocationListener {

    private static volatile LocationUtils instance;

    private CLocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Context context;
    private String mProviderName;

    //为避免内存泄漏，此处传入的一定要是application上下文对象
    public static LocationUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (LocationUtils.class) {
                if (instance == null) {
                    instance = new LocationUtils(context);
                }
            }
        }
        return instance;
    }




    public LocationUtils(final Context context) {
        this.context = context;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    // 查找到服务信息
                    Criteria criteria = new Criteria();
                    // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    // 设置是否要求速度
                    criteria.setSpeedRequired(false);
                    // 设置是否需要海拔信息
                    criteria.setAltitudeRequired(false);
                    // 设置是否需要方位信息
                    criteria.setBearingRequired(false);
                    // 设置是否允许运营商收费
                    criteria.setCostAllowed(true);


                    // 设置对电源的需求
                    criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

                    // 为获取地理位置信息时设置查询条件
                    mProviderName = mLocationManager.getBestProvider(criteria, true); // 获取GPS信息
//
//                    if (!TextUtils.isEmpty(mProviderName)) {
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                            return;
//                        }
//                        mLocationManager.requestLocationUpdates(mProviderName, 1000, 0, LocationUtils.this);
//                    }

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }

                    /**
                     * 参1:选择定位的方式
                     * 参2:定位的间隔时间
                     * 参3:当位置改变多少时进行重新定位
                     * 参4:位置的回调监听
                     */
                    mLocationManager.requestLocationUpdates(NETWORK_PROVIDER, 1, 0, new LocationListener() {
//                    mLocationManager.requestLocationUpdates(GPS_PROVIDER, 1, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //经度
                            double longitude = location.getLongitude();
                            //纬度
                            double latitude = location.getLatitude();

                            //海拔
                            double altitude = location.getAltitude();

                            Toast.makeText(context, "\"经度:==>\"+longitude+\" \\n 纬度==>\"+latitude+\"\\n\"+\"海拔==>\"+altitude: ", Toast.LENGTH_SHORT).show();
                            Log.i("loaction", "\"经度:==>\"+longitude+\" \\n 纬度==>\"+latitude+\"\\n\"+\"海拔==>\"+altitude: ");
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {


                            switch (status) {

                                case LocationProvider.AVAILABLE:

                                    Toast.makeText(context, "当前GPS为可用状态!", Toast.LENGTH_SHORT).show();

                                    break;

                                case LocationProvider.OUT_OF_SERVICE:

                                    Toast.makeText(context, "当前GPS不在服务内", Toast.LENGTH_SHORT).show();

                                    break;

                                case LocationProvider.TEMPORARILY_UNAVAILABLE:

                                    Toast.makeText(context, "当前GPS为暂停服务状态", Toast.LENGTH_SHORT).show();
                                    break;


                            }

                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Toast.makeText(context, "GPS开启了", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Toast.makeText(context, "GPS关闭了", Toast.LENGTH_SHORT).show();
                        }



                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isGpsEnabled(Context context) {
        return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(GPS_PROVIDER);
    }

    public void requestLocationUpdates(CLocationListener listener) {
        mLocationListener = listener;
    }

    public void removeUpdates() {
        mLocationListener = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocationListener != null) {
            mLocationListener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (mLocationListener != null) {
            mLocationListener.onLocationChanged(location);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface CLocationListener {

        void onLocationChanged(Location location);
    }
}
