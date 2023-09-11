package com.geokrishifarm.crophealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.geokrishifarm.crophealth.databinding.ActivityCropHealthBinding
import com.geokrishifarm.crophealth.features.crophealth.dto.CropHealthRequest
import com.geokrishifarm.crophealth.framework.FLWResponse
import com.geokrishifarm.crophealth.repo.CropHealthRepo
import com.geokrishifarm.crophealth.viewmodel.CropHealthVM
import com.geokrishifarm.crophealth.vmfactory.CropHealthVMFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CropHealthActivity : AppCompatActivity() {

    lateinit var binding: ActivityCropHealthBinding

    private val TAG = "MainActivity"

    private val vm = CropHealthVMFactory(this,
        CropHealthRepo.provideCropHealthRepo(this)).create(CropHealthVM::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropHealthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        onPostCreate()
    }


    private fun setToolbar() {
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.subtitle = ""
    }
    private fun onPostCreate(){
        Log.d(TAG,"Success message from $TAG")
        setToolbar()
        CoroutineScope(Dispatchers.Main).launch{

        }


    }


    suspend fun sendPostCropHealthRequestToServer(req: CropHealthRequest){

        vm.sendCropHealthPostRequest(req).collect{ flowResponse ->

            when(flowResponse.status){

                FLWResponse.Status.SUCCESS -> {

                }
                FLWResponse.Status.ERROR -> {

                }
                FLWResponse.Status.NO_INTERNET_AVAILABLE -> {

                }
                FLWResponse.Status.TOKEN_EXPIRED -> {

                }
                FLWResponse.Status.EXCEPTION -> {

                }

                else -> {}
            }

        }

    }


    /*private fun responseToState(flwResponse: FLWResponse<String>) {
        when(flwResponse.status){
            FLWResponse.Status.ERROR -> {}
            FLWResponse.Status.NO_INTERNET_AVAILABLE -> {}
            FLWResponse.Status.TOKEN_EXPIRED -> {}
            FLWResponse.Status.EXCEPTION -> {}

            else -> {}
        }

    }*/
}