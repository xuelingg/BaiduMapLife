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
	 * 定位sdk 核心类
	 */
	private LocationClient mLocationClient;
	/**
	 * 用户位置信息
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
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 卫星地图
		//	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// 交通图
		mBaiduMap.setTrafficEnabled(true);
	}

	//	第一步，构造自定义 PoiOverlay 类；

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
	//	第二步，在POI检索回调接口中添加自定义的PoiOverlay；

	public void onGetPoiResult(PoiResult result) {  
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {  
			return;  
		}  
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {  
			mBaiduMap.clear();  
			//创建PoiOverlay  
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);  
			//设置overlay可以处理标注点击事件  
			mBaiduMap.setOnMarkerClickListener(overlay);  
			//设置PoiOverlay数据  
			overlay.setData(result);  
			//添加PoiOverlay到地图中  
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
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
		mMapView.onDestroy();  
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
		mMapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
		mMapView.onPause();  
	}  
}
