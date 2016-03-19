# SDK下载 #
http://wiki.open.qq.com/wiki/SDK下载
下面这个是移动应用的
http://wiki.open.qq.com/wiki/mobile/SDK下载
下载安卓用的包
比如:Android_SDK_V2.9.4

然后按照 http://wiki.open.qq.com/wiki/创建并配置工程 的教程,配置你的项目.

http://wiki.open.qq.com/wiki/website/API列表


```java
	...
	//建立起Tencent
	mTencent = Tencent.createInstance("1104944776", this.getApplicationContext());
	mTencent.setOpenId("9D788BA31E04D7487D4199D3CF148D26");
	mTencent.setAccessToken("9B740AB23505550EBACF9B6FFDBB7819", "7776000");
	if (mTencent.isSessionValid())
		return;

	loginListener = new IUiListener() {
		@Override
		public void onComplete(Object o) {
			Log.i(TAG, "onComplete: ");
			JSONObject jo = (JSONObject) o;
			Log.i(TAG, "" + jo);
			mTencent.setOpenId(jo.optString("openid"));
			mTencent.setAccessToken(jo.optString("access_token"), jo.optString("expires_in"));
		}

		@Override
		public void onError(UiError uiError) {
			Log.i(TAG, "onError: ");
		}

		@Override
		public void onCancel() {
			Log.i(TAG, "onCancel: ");
		}
	};
	mTencent.login(MainActivity.this, "get_user_info,get_simple_userinfo", loginListener);
}
```