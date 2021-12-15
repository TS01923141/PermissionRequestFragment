# PermissionRequestFragment
透過無view的Fragment要求權限<br/>
request and check permission by no view fragment

使用步驟<br/>
use step

1.<br/>
  在build.gradle(module)新增<br/>
  in build.gradle(module) add

```
dependencies {
  implementation 'androidx.fragment:fragment-ktx:last_version'
}
```

2.<br/>
  複製PermissionRequestFragment到你的project<br/>
  copy PermissionRequestFragment to your project

3.<br/>
  編輯PermissionRequestFragment.companion object {}，新增想要要求的權限<br/>
  edit PermissionRequestFragment.companion object {}, add the permission that you want request<br/>
  ex.
  ```
  const val FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
  ```

3.<br/>
  編輯PermissionRequestFragment.buildRequestMessage()，新增要求權限的解釋<br/>
  edit PermissionRequestFragment.buildRequestMessage(), add the explain string of your request<br/>
  ex.
  ```
  FINE_LOCATION -> requireContext().getString(R.string.permission_explain_location)
  ```

4.<br/>
  在AndroidManifest.xml加入想要要求的權限<br/>
  in AndroidManifest.xml add permission that you want request<br/>
  ex.
  ```
  <uses-permission android:name="android.permission.CAMERA"/>
  ```

5.<br/>
  在Activity/Fragment新增FragmentResultListener，監聽權限要求結果<br/>
  如果權限全部取得成功 -> result = true<br/>
  如果權限沒有全部取得成功 -> result = false, 取得失敗的權限會在deniedList內<br/>
  add FragmentResultListener in Activity/Fragment to listen the permission request result<br/>
  if request all granted -> result = true<br/>
  if request not granted all -> result = false, and get denied permission list by deniedList
  ```
  supportFragmentManager.setFragmentResultListener(PermissionRequestFragment.PERMISSION_REQUEST_RESULT, yourActivity) {
    requestKey, bundle ->
    val result = bundle.getBoolean(PermissionRequestFragment.GRANTED_ALL_PERMISSION)
    val deniedList = bundle.getStringArrayList(PermissionRequestFragment.DENIED_LIST)
    //control request result
    //...
  }
  ```

6.<br/>
  建立要求權限的list<br/>
  build permission request list
  ```
  val requestFragment = PermissionRequestFragment.newInstance(arrayListOf(
      PermissionRequestFragment.FINE_LOCATION,
      PermissionRequestFragment.CAMERA,
      PermissionRequestFragment.WRITE_EXTERNAL_STORAGE
      ))
  ```
      
7.<br/>
  開始執行fragment<br/>
  start fragment
  ```
  beginTransaction()
      .add(android.R.id.content, requestFragment)
      .commit()
  ```
