package com.geokrishifarm.crophealth

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geokrishifarm.crophealth.databinding.ActivityCropHealthBinding
import com.geokrishifarm.crophealth.features.crophealth.dto.CropHealthRequest
import com.geokrishifarm.crophealth.features.crophealth.dto.FileInfo
import com.geokrishifarm.crophealth.framework.FLWResponse
import com.geokrishifarm.crophealth.repo.CropHealthRepo
import com.geokrishifarm.crophealth.viewmodel.CropHealthVM
import com.geokrishifarm.crophealth.vmfactory.CropHealthVMFactory
import com.google.android.material.snackbar.Snackbar
import com.geokrishifarm.crophealth.features.crophealth.adapter.TagAdapter
import com.geokrishifarm.crophealth.features.crophealth.dto.TagItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.DecimalFormat
import java.util.Locale

class CropHealthActivity : AppCompatActivity() {

    val IMAGE_SIZE_LIMIT = 5.0
    var imageList = ArrayList<FileInfo>()
    var audioList = ArrayList<FileInfo>()
    var currentCameraPhotoPath: String = ""
    var cropOrLivestock: String = "crop"
    var tagItemArrayList = ArrayList<TagItem>()
    var tagItemFilteredList = ArrayList<TagItem>()
    var adapter: TagAdapter? = null

    lateinit var binding: ActivityCropHealthBinding

    private val TAG = "MainActivity"

    private val vm = CropHealthVMFactory(
        this,
        CropHealthRepo.provideCropHealthRepo(this)
    ).create(CropHealthVM::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropHealthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        onPostCreate()
    }

    private fun clickCropOrLivestockRadioGroup() {

        binding.rgCropOrLiveStock.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)

            val cropOrLiveStockSelection = radio.text.toString()
            cropOrLivestock =
                if (cropOrLiveStockSelection.trim() == this.resources.getString(R.string.crop)
                        .trim()
                ) {
                    this.resources.getString(R.string.crop_eng)

                } else {
                    this.resources.getString(R.string.livestock_eng)
                }

            if (cropOrLivestock == this.resources.getString(R.string.livestock_eng).trim()) {
                showLiveStockLL()
            } else {
                hideLiveStockLL()
            }

            Log.d("$TAG ", "cropOrLivestock $cropOrLivestock")

            binding.cropHealthTag.text.clear()
            CoroutineScope(Dispatchers.Main).launch {
                sendTagListRequest(cropOrLivestock)
            }


        }
    }


    private fun setToolbar() {
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.subtitle = "जियोकृषि-खेत"
    }
    /*private fun onPostCreate(){
        Log.d(TAG,"Success message from $TAG")
        setToolbar()
        CoroutineScope(Dispatchers.Main).launch{

        }


    }*/


    private fun showLiveStockLL() {
        binding.liveStockAgeLL.visibility = View.VISIBLE
    }

    private fun hideLiveStockLL() {
        binding.liveStockAgeLL.visibility = View.GONE
    }


    fun onPostCreate() {
        initAdapterAndRecyclerView()
//        imageSelection()
//        clickViewImageButton()
        clickCropOrLivestockRadioGroup()
//        chooseFromCamera()
//        chooseFromGallery()
//        clickSendBtn()


//        clickAudioText()

//        clickStopButton()
//        clickPauseButton()


        CoroutineScope(Dispatchers.Main).launch {
            sendTagListRequest(cropOrLivestock)
        }




        tagTextChangeListener()
        setToolbar()
    }

    /*fun clickDeleteButton(filePath: String){
        binding.deleteButton.setOnClickListener {
            //open delete dialog
            showDeleteAudioDialog(filePath)
        }
    }*/


    /*private fun showDeleteAudioDialog(filePath: String) {

        DeleteDialog.showDeleteDialog(activity, activity.resources.getString(R.string.yes), activity.resources.getString(R.string.cancel),
            "तपाईले यो अडियो हटाउनु खोज्नु भएको हो ?", object : DeleteDialog.AlertDialogListener {
                override fun onPositiveButtonClick() {
//                val isDeleted =  DownloadUtils.deleteOfflineAudioFile(activity,filePath)
                    val isDeleted =  DownloadUtils.deleteInternalStorageFile(activity,filePath)

//                FileUtils.deleteAudioRecordFile(filePath)
//                activity.hideAudioPlayPauseLL()
//                audioList.clear()
//                ToastUtils.showToastMessage(activity,activity.resources.getString(R.string.audio_deleted_successfully))

                    if (isDeleted){
                        activity.hideAudioPlayPauseLL()
                        audioList.clear()
                        ToastUtils.showToastMessage(activity,activity.resources.getString(R.string.audio_deleted_successfully))
                    }
                }

                override fun onNegativeButtonClick() {

                }
            })
    }
    private fun clickAudioText(){
        activity.recordAudio.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if (PermissionUtils.checkAudioPermissionForAndroid13AndMore(activity)){
                    chooseAudioFromRecorder()
                }else{
                    PermissionUtils.requestAudioPermissionsForAndroid13AndMore(activity,activity.REQUEST_PERMISSION_CODE_FOR_AUDIO)
                }
            }else{
                chooseAudioFromRecorder()
            }


        }
    }

    private fun clickAudioText(){
        activity.recordAudio.setOnClickListener {
            chooseAudioFromRecorder()

        }
    }

    fun chooseAudioFromRecorder() {
        //  http://www.java2s.com/Code/Android/Core-Class/UsingIntenttorecordaudio.htm
        try {
            val intent = Intent(
                MediaStore.Audio.Media.RECORD_SOUND_ACTION
            )
            activity.startActivityForResult(intent, activity.REQUEST_RECORD_SOUND)
        } catch (ex: Exception) {
            // activity.showToast("No App found for audio recording!")
            openAudioRecordDialog()
        }
    }*/


    /*private fun clickSendBtn(){
        binding.btnSend.setOnClickListener {
            if (onValid()){
                sendCropHealthRequest()
            }


        }
    }*/

    private fun initAdapterAndRecyclerView() {

//        if (tagItemArrayList.size > 0) {

        binding.cropOrLiveStockTagRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.cropOrLiveStockTagRecyclerView.setHasFixedSize(true)
        adapter = TagAdapter(this, /*tagItemArrayList,*/ object : TagAdapter.OnItemClick {
            override fun item(item: TagItem) {
                setEtCropHealthTagName(item.tag!!)
                hideRecyclerView()
            }
        })
        binding.cropOrLiveStockTagRecyclerView.adapter = adapter
//            adapter!!.notifyDataSetChanged()
//        }

    }

    private fun setEtCropHealthTagName(name: String) {
        binding.cropHealthTag.setText(name)
    }

    private fun hideRecyclerView() {
        binding.cropOrLiveStockTagRecyclerView.visibility = View.GONE
    }

    private fun showRecyclerView() {
        binding.cropOrLiveStockTagRecyclerView.visibility = View.VISIBLE
    }

    private fun tagTextChangeListener() {


//        var scrollOnce  = true
//        val tagList=ArrayList<TagItem>()
//        tagList.addAll(tagItemArrayList)

//        Timber.v("TagItemList : ${tagList.size}")

        binding.cropHealthTag.onChange {
            val query = it
            tagItemFilteredList = tagItemArrayList.filter {
                (it.tag!!.lowercase(Locale.getDefault())).contains(
                    query.lowercase(
                        Locale.getDefault()
                    )
                )
            } as ArrayList<TagItem>

            Log.d(TAG, "query $query")
            if (query.isEmpty()) {
                Log.d(TAG, "QueryOFTag is empty")
                hideRecyclerView()

//                scrollOnce = true


            } else {

                Log.d(TAG, "QueryOFTag is empty")
                showRecyclerView()
//                adapter!!.submitList(tagItemFilteredList!!)
                /*if (scrollOnce){
                    activity.scrollView.fullScroll(View.FOCUS_DOWN)
                    activity.groupName.setSelection(1)
                    scrollOnce = false
                }*/

                if (tagItemFilteredList != null && tagItemFilteredList!!.size > 0) {
                    adapter!!.submitList(tagItemFilteredList!!)

                } else {
                    hideRecyclerView()
                }

            }
        }


    }


    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                removeTextChangedListener(this)
                cb(s.toString())
                addTextChangedListener(this)

            }
        })
    }


    private suspend fun sendTagListRequest(tag: String) {

        vm.sendTagListRequest(tag).collect {

            when (it.status) {

                FLWResponse.Status.SUCCESS -> {

                    tagItemArrayList.clear()
                    tagItemFilteredList.clear()

//                    hideTagProgressBar()
//                    Timber.v("cropOrLivestock message before tagItemArrayList : ${tagItemArrayList.size}" )
//                    Timber.v("cropOrLivestock message before tagItemFilteredList : ${tagItemFilteredList.size}" )

                    tagItemArrayList = it.data  as ArrayList<TagItem>
//                    Timber.v("cropOrLivestock message after tagItemArrayList : ${tagItemArrayList.size}" )
//                    Timber.v("cropOrLivestock message after tagItemFilteredList : ${tagItemFilteredList.size}" )
                    if (adapter == null){
                        initAdapterAndRecyclerView()
                    }

//                    Timber.v("TagAdapter $adapter")
                    adapter?.submitList(tagItemArrayList)



                    tagTextChangeListener()
//                    Timber.v("cropOrLivestock message final tagItemArrayList : ${tagItemArrayList.size}" )
//                    Timber.v("cropOrLivestock message final tagItemFilteredList : ${tagItemFilteredList.size}" )

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


    suspend fun sendPostCropHealthRequestToServer(req: CropHealthRequest) {

        vm.sendCropHealthPostRequest(req).collect { flowResponse ->

            when (flowResponse.status) {

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


    /*private fun imageSelection(){
        binding.ivImageSelection.setOnClickListener {
            showPicSelectionMenu()
        }
    }


    fun chooseFromCamera() {
        try {
            val file = FileUtils.createImageFile().apply { currentCameraPhotoPath = absolutePath }
            FileUtils.chooseFromCamera(this, file, activity.REQUEST_CODE_IMAGE_CAMERA)
        } catch (ex: IOException) {
            Timber.v(ex.toString())
        }

    }


    fun chooseFromGallery() {
        FileUtils.pickImage(this, activity.REQUEST_CODE_IMAGE)
    }


    private fun clickViewImageButton(){
        binding.viewImage.setOnClickListener {

            if (imageList.isNotEmpty()) {


                val infoPicDialog = FileInfoPicDialog()
                val b = Bundle()
                b.putString(
                    FileInfoPicDialog.KEY_FILE_INFO_LIST,
                    GsonUtils.toString(imageList)

                )
                infoPicDialog.arguments = b
                infoPicDialog.listener = object :
                    FileInfoPicDialog.PicListener {
                    override fun onDelete(
                        dialog: androidx.fragment.app.DialogFragment,
                        position: Int,
                        fileInfo: FileInfo
                    ) {
                        imageList.removeAt(position)
                        setMaterialCountAndMbSize()
                        if (imageList.isEmpty()){
                            hideViewAllTv()
                            activity.healthImageView.setImageDrawable(null)

                        }
                    }

                }
                infoPicDialog.show(activity.supportFragmentManager, null)
            }else{
                activity.showErrorMessage("No image is selected")
            }


        }
    }

    private fun hideTagProgressBar(){
        binding.tagProgressBar.visibility = View.GONE

    }

    private fun showProgressBar(){
        binding.tagProgressBar.visibility = View.VISIBLE

    }


    fun showViewAllTv(){
        binding.viewImage.visibility = View.VISIBLE
    }
    fun hideViewAllTv(){
        binding.viewImage.visibility = View.GONE
    }



    fun decideWhetherToAddPicOrNot(fileInfoList: ArrayList<FileInfo>) {

        val totalSavedImageInMb = calculateTotalImageFileSize()
        var newImageInMb = 0.0

        for (fileInfo in fileInfoList) {
            newImageInMb += fileInfo.sizeInMb
        }

        val grandTotal = totalSavedImageInMb + newImageInMb

        if (grandTotal > IMAGE_SIZE_LIMIT) {
            activity.showErrorMessage("Sorry, total image size limit is ${IMAGE_SIZE_LIMIT} MB\n\nPlease edit or crop picture and try to upload again.")
        } else {
            imageList.addAll(fileInfoList)
            setMaterialCountAndMbSize()
            setPictureIntoHolder(binding.healthImageView,imageList[0])
            showViewAllTv()
        }

    }

    private fun setMaterialCountAndMbSize() {
        val df = DecimalFormat("#.##")
        val text = df.format(calculateTotalImageFileSize()) + "/$IMAGE_SIZE_LIMIT"+ " MB"
        binding.upLoadPhoto.text =text
//
    }

    private fun calculateTotalImageFileSize(): Double {

        var totalMb = 0.0

        for (fileInfo in imageList) {
            totalMb += fileInfo.sizeInMb
        }

        Log.d(TAG, "total image mb : $totalMb")

        return totalMb
    }




    private fun goToNewDashBoardActivity(){
        Constants.CAMERA_FLAG = false
        val intent = Intent(activity, NewDashBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        activity.startActivity(intent)
        activity.finish()
    }




    private fun sendCropHealthRequest() {

        val uProgressBar = UProgressBar.newInstance(activity.getString(R.string.sending_crop_health))
        uProgressBar.isCancelable = false
        val fm = activity.supportFragmentManager
//        var cropOrLivestock: String = activity.resources.getString(R.string.crop_eng)
        var cropHealthRequest : CropHealthRequest? = null
        Timber.v("CropHealth Request cropOrLivestock: "+ cropOrLivestock)

        if(cropOrLivestock.trim() == activity.resources.getString(R.string.crop_eng)){
            cropHealthRequest = CropHealthRequest(cropOrLivestock,activity.cropHealthTag.text.toString() ,activity.description.text.toString(), null, null,null, imageList,audioList)

        }else{
            cropHealthRequest = CropHealthRequest(cropOrLivestock,activity.cropHealthTag.text.toString() ,activity.description.text.toString(), null, null,activity.livestock_age.text.toString(), imageList,audioList)
        }

        Timber.v("CropHealth Request New"+ GsonUtils.toString(cropHealthRequest))

        userDashBoardViewModel.sendCropHealthRequest(cropHealthRequest).observe(activity, Observer {

            when (it?.status) {

                LDResponse.Status.LOADING -> {
                    uProgressBar.show(fm, null)

                }

                LDResponse.Status.IN_PROGRESS -> {
                    Timber.v("ImageOfCrops is uploading one by one")
                    uProgressBar.message = it.progressStat!!.message
                    uProgressBar.updateProgressStat(it.progressStat!!)

                }

                LDResponse.Status.LOADING_FINISHED -> {
                    uProgressBar.dismiss()
                }

                LDResponse.Status.SUCCESS -> {
                    Timber.v("message" + it.ldData)

                    ToastUtils.showToastMessage(activity,it.ldData)
                    goToNewDashBoardActivity()
                    // set the value for urea, dap and map
                }

                LDResponse.Status.ERROR -> {
                    activity.showErrorMessage(it.error)
                }

                LDResponse.Status.NO_INTERNET_AVAILABLE -> {
                    activity.showErrorMessage(it.error)

                }


                else -> {}
            }
        })


    }
    fun openAudioRecordDialog() {
        var recordDialog = AudioRecordDialog()
        recordDialog.listener = object :
            AudioRecordDialog.AudioListener {
            override fun onComplete(dialog: DialogFragment, position: Int, recordPath: String) {
                FileUtils.addToMediaScanner(activity, recordPath)
                audioList.add(FileInfo("audio/*", recordPath, FileUtils.getFileSizeInMb(recordPath)))
                activity.showAudioPlayPauseLL()
                recordDialog.dismiss()
                activity.clickPlayBottom(Uri.parse(audioList[0].path))

            }
        }
        recordDialog.show(activity.supportFragmentManager, null)
    }


    fun chooseAudioFromStorage() {
        FileUtils.pickAudio(
            activity,
            activity.REQUEST_CODE_AUDIO
        )
    }


    fun setPictureIntoHolder(holder : AppCompatImageView, fileInfo : FileInfo ){

        Logger.log("FilePath: "+ GsonUtils.toString(fileInfo))


        Picasso.get()
            // add file:// infront of path of image regarding in gallery.
            .load("file://"+fileInfo.path).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder)
    }


    private fun onValid(): Boolean {

        if (audioList.isEmpty()){

            if (cropOrLivestock.trim() == activity.resources.getString(R.string.livestock_eng)){
                if (TextUtils.isEmpty(activity.livestock_age.text)) {
                    activity.livestock_age.error = activity.resources.getString(R.string.do_not_leave_blank)
                    return false
                }
            }

            if (TextUtils.isEmpty(activity.cropHealthTag.text)) {
                activity.cropHealthTag.error = activity.resources.getString(R.string.do_not_leave_blank)
                return false
            }

            if (TextUtils.isEmpty(activity.description.text)) {
                activity.description.error = activity.resources.getString(R.string.do_not_leave_blank)
                return false
            }
        }


        if (imageList.isEmpty()){
            showErrorSnackBar(activity.resources.getString(R.string.dry_no_image_selected))
            return false
        }

        return true
    }


    private fun showErrorSnackBar(errorString: String) {
        val snackBar = Snackbar
            .make(activity.coordinator, errorString, Snackbar.LENGTH_LONG)

        snackBar.show()
    }

    */


}*/

}


