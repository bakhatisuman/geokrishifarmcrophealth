package com.geokrishifarm.crophealth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geokrishifarm.crophealth.R
import com.geokrishifarm.crophealth.features.crophealth.dto.CropHealthRequest
import com.geokrishifarm.crophealth.framework.FLWResponse
import com.geokrishifarm.crophealth.repo.CropHealthRepo
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow

class CropHealthVM(private val repo: CropHealthRepo) : ViewModel() {

    private var disposals = CompositeDisposable()
     var helloText = "Hello Suman"


    suspend fun sendCropHealthPostRequest(request: CropHealthRequest): Flow<FLWResponse<String>> {
       return repo.sendPostCropHealthQuery(request)
    }

    suspend fun test(page : Int): Flow<FLWResponse<String>> {
       return repo.test(page)
    }


    /*suspend fun sendCropHealthPostRequest(request: CropHealthRequest){
        repo.sendPostCropHealthQuery(request).collect{
            if(it.status == FLWResponse.Status.SUCCESS){

            }else{
                responseToState(it)
            }
        }
    }*/


    private fun responseToState(flwResponse: FLWResponse<String>) {
        when(flwResponse.status){
            FLWResponse.Status.ERROR -> {}
            FLWResponse.Status.NO_INTERNET_AVAILABLE -> {}
            FLWResponse.Status.TOKEN_EXPIRED -> {}
            FLWResponse.Status.EXCEPTION -> {}

            else -> {}
        }

    }



    override fun onCleared() {
        super.onCleared()
        disposals.clear()
    }

}