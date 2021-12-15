# PermissionRequestFragment
request and check permission by no view fragment

use step

1.在build.gradle(module)新增
  in build.gradle(module) add
dependencies {
  implementation 'androidx.fragment:fragment-ktx:last_version'
}

2.複製PermissionRequestFragment到你的project
  copy PermissionRequestFragment to your project

3.編輯PermissionRequestFragment.companion object {}，新增想要要求的權限
  edit PermissionRequestFragment.companion object {}, add the permission that you want request
  ex.const val FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION

3.編輯PermissionRequestFragment.buildRequestMessage()，新增要求權限的解釋
  edit PermissionRequestFragment.buildRequestMessage(), add the explain string of your request
  ex.FINE_LOCATION -> requireContext().getString(R.string.permission_explain_location)

4.在AndroidManifest.xml加入想要要求的權限
  in AndroidManifest.xml add permission that you want request
  ex.<uses-permission android:name="android.permission.CAMERA"/>

5.在Activity/Fragment新增FragmentResultListener，監聽權限要求結果
  如果權限全部取得成功 -> result = true
  如果權限沒有全部取得成功 -> result = false, 取得失敗的權限會在deniedList內
  add FragmentResultListener in Activity/Fragment to listen the permission request result
  if request all granted -> result = true
  if request not granted all -> result = false, and get denied permission list by deniedList
  
  supportFragmentManager.setFragmentResultListener(PermissionRequestFragment.PERMISSION_REQUEST_RESULT, yourActivity) {
    requestKey, bundle ->
    val result = bundle.getBoolean(PermissionRequestFragment.GRANTED_ALL_PERMISSION)
    val deniedList = bundle.getStringArrayList(PermissionRequestFragment.DENIED_LIST)
  }

6.建立要求權限的list
  build permission request list
  val requestFragment = PermissionRequestFragment.newInstance(arrayListOf(
      PermissionRequestFragment.FINE_LOCATION,
      PermissionRequestFragment.CAMERA,
      PermissionRequestFragment.WRITE_EXTERNAL_STORAGE
      ))
      
7.開始執行fragment
  start fragment
  beginTransaction()
      .add(android.R.id.content, requestFragment)
      .commit()
