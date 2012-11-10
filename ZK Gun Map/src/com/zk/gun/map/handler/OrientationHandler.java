package com.zk.gun.map.handler;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Class Orientation sử dụng để lấy các giá trị định hướng của máy
 * 
 * @author z.k.
 * @since 29/10/2012
 * 
 */
public class OrientationHandler extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
 
    private static float[] values = new float[3];
 
    @SuppressWarnings("deprecation")
	public OrientationHandler(Activity activity) {
 
        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
 
        mAccelerometer = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION).get(0);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		System.arraycopy(event.values, 0, values, 0, event.values.length);
	}
	
	/**
	 * Phương thức lấy các giá trị định hướng của máy
	 * 
	 * @return Mảng float[3]
	 */
	public static float[] getValues() {
		return values;
	}
}
