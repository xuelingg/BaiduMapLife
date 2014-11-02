package com.example.baidumaplife;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.nplatform.comapi.map.MapController;

public class MainActivity extends Activity {

	private Toast mToast;
	private BMapManager mapManager;
	private MapView mMapView = null; 
	private BaiduMap mBaiduMap = null;
	private MapController mMapController;
	private PoiResult poiResult;
	/**
	 * ��λsdk ������
	 */
	private LocationClient mLocationClient;
	/**
	 * �û�λ����Ϣ
	 */
	private MyLocationData mLocationData;

	private MyLocationOverLay mLocationOverLay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_main);
		mapManager = new BMapManager(getApplication());

		mMapView = (MapView) findViewById(R.id.bmapView);
		
		mBaiduMap = mMapView.getMap();
		// ��ͨ��ͼ
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// ���ǵ�ͼ
		//	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// ��ͨͼ
		mBaiduMap.setTrafficEnabled(true);
	}

	//	��һ���������Զ��� PoiOverlay �ࣻ

	private class MyPoiOverlay extends PoiOverlay {  
		public MyPoiOverlay(BaiduMap baiduMap) {  
			super(baiduMap);  
		}  
		@Override  
		public boolean onPoiClick(int index) {  
			super.onPoiClick(index);  
			return true;  
		}  
	}
	//	�ڶ�������POI�����ص��ӿ�������Զ����PoiOverlay��

	public void onGetPoiResult(PoiResult result) {  
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {  
			return;  
		}  
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {  
			mBaiduMap.clear();  
			//����PoiOverlay  
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);  
			//����overlay���Դ����ע����¼�  
			mBaiduMap.setOnMarkerClickListener(overlay);  
			//����PoiOverlay����  
			overlay.setData(result);  
			//���PoiOverlay����ͼ��  
			overlay.addToMap();  
			overlay.zoomToSpan();  
			return;  
		}  
	}

	private class MyLocationOverLay extends Overlay
	{
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		//��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onDestroy();  
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		//��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onPause();  
	}  
}
