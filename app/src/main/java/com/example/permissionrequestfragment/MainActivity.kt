package com.example.permissionrequestfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.permissionrequestfragment.databinding.ActivityMainBinding
import com.example.trackingroute.ui.permission.PermissionRequestFragment
import java.lang.StringBuilder

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.apply {
            //add listener
            setFragmentResultListener(PermissionRequestFragment.PERMISSION_REQUEST_RESULT, this@MainActivity) {
                    requestKey, bundle ->
                //show result on text view.
//                Log.d(TAG, "onCreate: requestKey: $requestKey")
                val result = bundle.getBoolean(PermissionRequestFragment.GRANTED_ALL_PERMISSION)
                val deniedList = bundle.getStringArrayList(PermissionRequestFragment.DENIED_LIST)
//                Log.d(TAG, "onCreate: result: $result")
//                Log.d(TAG, "onCreate: deniedList.isNullOrEmpty: " + deniedList.isNullOrEmpty())
//                Log.d(TAG, "onCreate: deniedList.size: " + deniedList?.size)
                val stringBuilder = StringBuilder()
                stringBuilder.append("Granted all permission: ").append(result.toString())
                deniedList?.forEach {
                    stringBuilder.append("\n").append(it)
                }
                binding.textViewMain.text = stringBuilder.toString()
            }

            //the list of permission that you want request or check.
            val requestFragment = PermissionRequestFragment.newInstance(arrayListOf(
                PermissionRequestFragment.FINE_LOCATION,
                PermissionRequestFragment.CAMERA,
                PermissionRequestFragment.WRITE_EXTERNAL_STORAGE
                ))
            //start request permission
            beginTransaction()
                .add(android.R.id.content, requestFragment)
                .commit()
        }
    }
}