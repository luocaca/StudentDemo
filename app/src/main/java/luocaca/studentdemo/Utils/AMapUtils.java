package luocaca.studentdemo.Utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 *
 */

public class AMapUtils {


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {


                if (aMapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。

                    Log.i("onLocation", aMapLocation.getAddress() + " thread = " + Thread.currentThread().getName());
                    Log.i("onLocation", aMapLocation.getCity());
//                    amapLocation.getCity();//城市信息

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };


    private static volatile AMapUtils instance;
    private Context mContext;

    //为避免内存泄漏，此处传入的一定要是application上下文对象
    public static AMapUtils getInstance(Context context) {
        assert context instanceof Application;
        if (instance == null) {
            synchronized (AMapUtils.class) {
                if (instance == null) {
                    instance = new AMapUtils(context);
                }
            }
        }
        return instance;
    }

    public AMapUtils setLocationListener(AMapLocationListener listener) {
        this.mLocationListener = listener;
        mLocationClient.setLocationListener(mLocationListener);
        return instance;
    }


    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private AMapUtils(Context context) {
        this.mContext = context;

        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

//        选择定位场景
//        说明：该部分功能从定位SDK v3.7.0开始提供。如果开发者选择了对应的定位场景，
// 那么则不用自行设置AMapLocationClientOption中的其他参数
// ，SDK会根据选择的场景自行定制option参数的值
// ，当然开发者也可以在基础上进行自行设置。
// 实际按最后一次设置的参数值生效。


//        目前支持3种定位场景的设置：签到、出行、运动。默认无场景。

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }


//        选择定位模式
//        高德定位服务包含GPS和网络定位（Wi-Fi和基站定位）两种能力。
// 定位SDK将GPS、网络定位能力进行了封装，以三种定位模式对外开放，
// SDK默认选择使用高精度定位模式。
//        高精度定位模式：会同时使用网络定位和GPS定位
// ，优先返回最高精度的定位结果，以及对应的地址描述信息。


//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        //高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；


//设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);


//
//设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);


//        设置单次定位
//        如果您需要使用单次定位，需要进行如下设置

//获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

//
//        自定义连续定位
//        SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔：

//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(1000);


        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);

//        设置定位请求超时时间，默认为30秒。
//        注意：自 V3.1.0 版本之后setHttpTimeOut(long httpTimeOut)
//        方法不仅会限制低功耗定位、高精度定位两种模式的定位超时时间，同样会作用在仅设备定位时。
//        如果单次定位发生超时情况，定位随即终止；
//        连续定位状态下当前这一次定位会返回超时，
//        但按照既定周期的定位请求会继续发起。


        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(15000);

        /*设置是否开启定位缓存机制
缓存机制默认开启，可以通过以下接口进行关闭。
当开启定位缓存功能，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存，不区分单次定位还是连续定位。GPS定位结果不会被缓存。
Java

//关闭缓存机制
mLocationOption.setLocationCacheEnable(false);*/

//给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//启动定位
//        mLocationClient.startLocation();
    }

    public void requestLocationUpdates() {
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    // 移除定位监听
    public void removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mLocationListener);
        }
    }

}
