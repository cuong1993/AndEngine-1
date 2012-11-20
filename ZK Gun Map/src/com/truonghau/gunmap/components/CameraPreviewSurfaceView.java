package com.truonghau.gunmap.components;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private Camera camera;
	
	@SuppressWarnings("deprecation")
	public CameraPreviewSurfaceView(Context context) {
		super(context);
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		final Camera.Parameters parameters = this.camera.getParameters();
		parameters.setPreviewSize(width, height);
		this.camera.setParameters(parameters);
		this.camera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.camera = Camera.open();
		try {
			this.camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.camera.stopPreview();
		this.camera.release();
		this.camera = null;
	}

}
