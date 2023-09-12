package com.geokrishifarm.crophealth.features.crophealth.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geokrishifarm.crophealth.R
import com.geokrishifarm.crophealth.features.crophealth.dto.CropHealthExt


class CropHealthResponseAdapter(var context: Context, var list: List<CropHealthExt>,
                                var listener : OnReplyClick, var listenerAudio : OnAudioSectionClick
) : RecyclerView.Adapter<CropHealthResponseAdapter.ViewHolder>() {


/*
    var feedBackAdapter : FeedbackAdapter? = null
*/

    interface OnAudioSectionClick{
        fun onPlayAudioClick(item : CropHealthExt , playBtn : AppCompatImageView, pauseBtn : AppCompatImageView,stopBtn : AppCompatImageView)
        fun onPauseAudioClick(item : CropHealthExt, playBtn : AppCompatImageView, pauseBtn : AppCompatImageView,stopBtn : AppCompatImageView)
        fun onStopAudioClick(item : CropHealthExt, playBtn : AppCompatImageView, pauseBtn : AppCompatImageView,stopBtn : AppCompatImageView)
    }


    interface OnReplyClick{
        fun onReplyOfEXTClick(textView: AppCompatTextView,item : CropHealthExt.Answer)
        fun onSendButtonClick(textView : AppCompatTextView,editText: AppCompatEditText,item : CropHealthExt)
        fun onReplyItalicClick(textView : AppCompatTextView)
        fun onReplyEditClick(editText: AppCompatEditText,item : CropHealthExt.Answer)
        fun onReplyDeleteClick(item : CropHealthExt.Answer)
        fun onEditTextChangeListenerClick(query : String)
        fun onThumpUpClick(position: Int,item : CropHealthExt.Answer, thumbUp: ImageView,thumbDown : ImageView)
        fun onThumpDownClick(position: Int, item : CropHealthExt.Answer, thumbUp: ImageView,thumbDown : ImageView)
    }


    private fun AppCompatEditText.onChange(cb: (String) -> Unit) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_crop_health_response, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!list[position].livestockAge.isNullOrEmpty()){
            holder.tvLiveStockAge.text = context.resources.getString(R.string.livestock_age)+ " : " +list[position].livestockAge?.trim()
        }else{
            holder.tvLiveStockAge.visibility = View.GONE
        }

        holder.tvDescription.text = list[position].description.toString()
//        holder.tvAskedOn.text = list[position].dateUploaded.toString()


        if (list[position].dateUploadedNepali.isNullOrEmpty()) {
//            holder.tvAskedOn.text = DateUtils.dateTimeFormat(list[position].dateUploaded!!)
            holder.tvAskedOn.text = list[position].dateUploaded.toString()

        } /*else {
            val date = DateUtils.splitNepaliDate(list[position].dateUploadedNepali!!)
            val formatMonth = MonthUtils.whichNepaliMonth(date[1].toInt().minus(1)) // 0 INDEX MONTH..
            val nepaliYear = ConvertUtils.convertNumberViaLocale(date[0].toInt())
            val displayDate = nepaliYear.replace(",","")+" "+"$formatMonth ${ConvertUtils.convertNumberViaLocale(date[2].toInt())} गते"
            holder.tvAskedOn.text = context.getString(R.string.asked_date)+displayDate
        }*/

        if (!list[position].audio.isNullOrEmpty()){
            holder.audioSectionLL.visibility = View.VISIBLE
        }
        else{
            holder.audioSectionLL.visibility = View.GONE
        }

        var listOfExt : List<CropHealthExt.Answer>? = null
        listOfExt = list[position].answers
        if (!listOfExt.isNullOrEmpty()){
            // setting up answer recyclerView
            holder.tvNoResponse.visibility = View.GONE
            holder.answerRecyclerView.visibility = View.VISIBLE

            holder.answerRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            holder.answerRecyclerView.setHasFixedSize(true)
            /*feedBackAdapter = FeedbackAdapter(context, listOfExt, holder.tvReplyOfText, holder.etReply)
            holder.answerRecyclerView.adapter = feedBackAdapter
            feedBackAdapter?.notifyDataSetChanged()*/
        }else{
            // show no feedback provided text here and hide recyclerview
            holder.answerRecyclerView.visibility = View.GONE
//            holder.reviewRL.visibility = View.GONE
            holder.tvNoResponse.visibility = View.VISIBLE

        }

        /* todo uncomment this when pIcasso library is added.

        if (!list[position].images.isNullOrEmpty()){
            val issueImageToShow = list[position].images!![0]
            Picasso.get().load(issueImageToShow).into(holder.ivIssueImage)
        }*/


        holder.etReply.onChange {
            // make atfModelIdForGroup null whenever group name text is changed.
            val query = it
            listener.onEditTextChangeListenerClick(query)


        }


        /*holder.tvViewAll.setOnClickListener {
         holder.reviewRL.child.visibility = View.VISIBLE
        }*/

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLiveStockAge: TextView
        var tvDescription: TextView
        var tvAskedOn: TextView
        var tvNoResponse: TextView
        var tvReplyOfText: AppCompatTextView
        var etReply: AppCompatEditText
        var ivBtnSend: AppCompatImageView
        var ivIssueImage : ImageView
        var answerRecyclerView: RecyclerView
        var reviewRL: RelativeLayout
        var tvViewAll: TextView
        var audioSectionLL: LinearLayout
        var ivPlayButton: AppCompatImageView
        var ivPauseButton: AppCompatImageView
        var ivStopButton: AppCompatImageView



        init {
            context = itemView.context
            tvLiveStockAge = itemView.findViewById(R.id.livestockAge) as TextView
            tvDescription = itemView.findViewById(R.id.description) as TextView
            tvViewAll = itemView.findViewById(R.id.viewAll) as TextView
            tvAskedOn = itemView.findViewById(R.id.askedOn) as TextView
            tvNoResponse = itemView.findViewById(R.id.noResponse) as TextView
            ivIssueImage = itemView.findViewById(R.id.issueImage) as ImageView
            answerRecyclerView = itemView.findViewById(R.id.answerRecyclerView) as RecyclerView
            reviewRL = itemView.findViewById(R.id.reviewRL) as RelativeLayout
            audioSectionLL = itemView.findViewById(R.id.audioPlayPauseLL) as LinearLayout
            ivPlayButton = itemView.findViewById(R.id.playButton) as AppCompatImageView
            ivPauseButton = itemView.findViewById(R.id.pauseButton) as AppCompatImageView
            ivStopButton = itemView.findViewById(R.id.stopButton) as AppCompatImageView


            tvReplyOfText = itemView.findViewById(R.id.replyOfText) as AppCompatTextView
            etReply = itemView.findViewById(R.id.reply) as AppCompatEditText
            ivBtnSend = itemView.findViewById(R.id.btnSend) as AppCompatImageView




            ivBtnSend.setOnClickListener {

                val pos = this@ViewHolder.adapterPosition
                listener.onSendButtonClick(tvReplyOfText,etReply,list[pos])
            }

            tvReplyOfText.setOnClickListener {
                val pos = this@ViewHolder.adapterPosition
                listener.onReplyItalicClick(tvReplyOfText)
            }

            ivPlayButton.setOnClickListener {

                val pos = this@ViewHolder.adapterPosition
                listenerAudio.onPlayAudioClick(list[pos] ,ivPlayButton, ivPauseButton , ivPauseButton)
            }

            ivPauseButton.setOnClickListener {

                val pos = this@ViewHolder.adapterPosition
                listenerAudio.onPauseAudioClick(list[pos],ivPlayButton, ivPauseButton , ivPauseButton)
            }

            ivStopButton.setOnClickListener {
                val pos = this@ViewHolder.adapterPosition
                listenerAudio.onStopAudioClick(list[pos],ivPlayButton, ivPauseButton , ivPauseButton)
            }



        }


    }


    /*inner class FeedbackAdapter(private var context: Context,
                                  private var answerList: List<CropHealthExt.Answer>,
                                private var textView: AppCompatTextView , private var editText: AppCompatEditText) : RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_answer, parent, false)

            return ViewHolder(view)
        }


        override fun getItemCount(): Int {
            return answerList.size
        }

        fun changeImageIconsWhenClickThumpUp(thumbUp: ImageView , thumbDown: ImageView){
            thumbUp.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_up_blue_24, null ))
            thumbDown.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_down_gray_24, null ))
//            thumbUp.setImageDrawable(context.resources.getDrawable(R.drawable.ic_thumb_up_blue_24,null))
//            thumbDown.setImageDrawable(context.resources.getDrawable(R.drawable.ic_thumb_down_gray_24,null))



        }

        fun changeImageIconsWhenClickThumpDown(thumbUp: ImageView , thumbDown: ImageView){
            thumbUp.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_up_gray_24, null ))
            thumbDown.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_down_red_24, null ))
//            thumbUp.setImageDrawable(context.resources.getDrawable(R.drawable.ic_thumb_up_gray_24,null))
//            thumbDown.setImageDrawable(context.resources.getDrawable(R.drawable.ic_thumb_down_red_24,null))
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.tvExtFullName.text = answerList[position].extFullName

            holder.tvSelfFullName.text = answerList[position].extFullName

            if (answerList[position].createdOnNepali.isNullOrEmpty()) {
                holder.tvRepliedOn.text = DateUtils.dateTimeFormat(answerList[position].createdOn!!)
                holder.tvSelfRepliedOn.text = DateUtils.dateTimeFormat(answerList[position].createdOn!!)

            } *//*else {
                val date = DateUtils.splitNepaliDate(answerList[position].createdOnNepali!!)
                val formatMonth = MonthUtils.whichNepaliMonth(date[1].toInt().minus(1)) // 0 INDEX MONTH..
                val nepaliYear = ConvertUtils.convertNumberViaLocale(date[0].toInt())
                val displayDate = nepaliYear.replace(",","")+" "+"$formatMonth ${ConvertUtils.convertNumberViaLocale(date[2].toInt())} गते"
                holder.tvRepliedOn.text = context.getString(R.string.replied_date)+ displayDate
                holder.tvSelfRepliedOn.text = context.getString(R.string.replied_date)+ displayDate
            }*//*

            // check whose answer
            if (answerList[position].isSelf){
                holder.tvEditIcon.visibility = View.VISIBLE
                holder.tvDeleteIcon.visibility = View.VISIBLE
                holder.tvReplyIcon.visibility = View.GONE
                holder.selfRl.visibility = View.VISIBLE
                holder.extRl.visibility = View.GONE
            }else{
                holder.tvReplyIcon.visibility = View.VISIBLE
                holder.tvEditIcon.visibility = View.GONE
                holder.tvDeleteIcon.visibility = View.GONE
                holder.selfRl.visibility = View.GONE
                holder.extRl.visibility = View.VISIBLE
            }


            if (answerList[position].parentAnswer != null){
                holder.tvRepliedForText.visibility = View.VISIBLE
                holder.tvSelfRepliedForText.visibility = View.VISIBLE
                holder.tvRepliedForText.text = answerList[position].parentAnswer
                holder.tvSelfRepliedForText.text = answerList[position].parentAnswer

            }

            *//*if (answerList[position].isSelf){
                holder.tvEditIcon.visibility = View.VISIBLE
                holder.tvDeleteIcon.visibility = View.VISIBLE
                holder.tvReplyIcon.visibility = View.GONE
            }else{
                holder.tvReplyIcon.visibility = View.VISIBLE
                holder.tvEditIcon.visibility = View.GONE
                holder.tvDeleteIcon.visibility = View.GONE
            }*//*

//            holder.tvRepliedOn.text = DateUtils.dateTimeFormatForDynamic(answerList[position].createdOn!!)
//            holder.tvRepliedOn.text = answerList[position].createdOn!!
            holder.tvAnswer.text = answerList[position].answer
            holder.tvSelfAnswer.text = answerList[position].answer
//            holder.tvAnswer.text = HtmlCompat.fromHtml(answerList[position].answer!!, HtmlCompat.FROM_HTML_MODE_COMPACT)

            if (!answerList[position].extImage.isNullOrEmpty()){
                try {
                    Picasso.get().load(answerList[position].extImage).into(holder.ivExtImage)
                    Picasso.get().load(answerList[position].extImage).into(holder.ivSelfImage)
                }catch (ex : Exception){
                    ToastUtils.showToastMessage(context, context.resources.getString(R.string.cant_load_circular_image))
                }


            }

            *//**check for no reaction.*//*
            if (answerList[position].reaction == null){
                holder.ivThumbUp.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_up_gray_24, null ))
                holder.ivThumbDown.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_down_gray_24, null ))
//
            }else{
                *//**check for like.*//*
                if (answerList[position].reaction!! == 1){
                    holder.ivThumbUp.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_up_blue_24, null ))
                    holder.ivThumbDown.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_down_gray_24, null ))
                }
                 *//**check for dislike.*//*
                else if (answerList[position].reaction!! == 2){
                    holder.ivThumbUp.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_up_gray_24, null ))
                    holder.ivThumbDown.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_thumb_down_red_24, null ))
                }
            }

        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var tvExtFullName : TextView
            var tvRepliedOn  : TextView
            var tvAnswer : TextView
            // this needs to be checked whether show or not.
            var tvRepliedForText : TextView
            var ivExtImage : ImageView
            var tvReplyIcon : AppCompatTextView
            var tvEditIcon : AppCompatTextView
            var tvDeleteIcon : AppCompatTextView
            var cvAnswer : CardView
            var selfRl : RelativeLayout
            var extRl : RelativeLayout

            // self card
            var tvSelfFullName : TextView
            var tvSelfRepliedOn  : TextView
            var tvSelfRepliedForText : TextView
            var ivSelfImage : ImageView
            var tvSelfAnswer : TextView

            var ivThumbUp : ImageView
            var ivThumbDown : ImageView


            init {

                context = itemView.context
                tvExtFullName = itemView.findViewById(R.id.extFullName) as TextView
                tvRepliedOn = itemView.findViewById(R.id.repliedOn) as TextView
                tvRepliedForText = itemView.findViewById(R.id.repliedForText) as TextView
                tvReplyIcon = itemView.findViewById(R.id.replyResponse) as AppCompatTextView
                tvEditIcon = itemView.findViewById(R.id.editResponse) as AppCompatTextView
                tvDeleteIcon = itemView.findViewById(R.id.deleteResponse) as AppCompatTextView
                tvAnswer = itemView.findViewById(R.id.answer)as TextView
                ivExtImage =  itemView.findViewById(R.id.extImage) as ImageView
                cvAnswer =  itemView.findViewById(R.id.answerCardView) as CardView
                selfRl = itemView.findViewById(R.id.selfRl) as RelativeLayout
                extRl = itemView.findViewById(R.id.extRl) as RelativeLayout

                // for self card
                tvSelfFullName = itemView.findViewById(R.id.selfFullName) as TextView
                tvSelfRepliedOn = itemView.findViewById(R.id.selfrepliedOn) as TextView
                tvSelfRepliedForText = itemView.findViewById(R.id.selfRepliedForText) as TextView
                tvSelfAnswer = itemView.findViewById(R.id.selfAnswer) as TextView
                ivSelfImage =  itemView.findViewById(R.id.selfImage) as ImageView
                ivThumbUp = itemView.findViewById(R.id.thumpUp) as ImageView
                ivThumbDown = itemView.findViewById(R.id.thumpDown) as ImageView



                ivThumbUp.setOnClickListener {
                    val pos  = this@ViewHolder.absoluteAdapterPosition
//                    changeImageIconsWhenClickThumpUp(pos,ivThumbUp,ivThumbDown)
                    listener.onThumpUpClick(pos,answerList[pos], ivThumbUp,ivThumbDown)
                }

                ivThumbDown.setOnClickListener {
                    val pos  = this@ViewHolder.absoluteAdapterPosition
//                    changeImageIconsWhenClickThumpDown(pos,ivThumbUp,ivThumbDown)
                    listener.onThumpDownClick(pos,answerList[pos],ivThumbUp, ivThumbDown)
                }



                tvReplyIcon.setOnClickListener {
                    val pos = this@ViewHolder.absoluteAdapterPosition
                    listener.onReplyOfEXTClick(textView,answerList[pos])
                    editText.requestFocus()
                }

                tvEditIcon.setOnClickListener {
                    val pos  = this@ViewHolder.absoluteAdapterPosition
                    listener.onReplyEditClick(editText,answerList[pos])
                    editText.requestFocus()

                }

                tvDeleteIcon.setOnClickListener {
                    val pos  = this@ViewHolder.absoluteAdapterPosition
                    listener.onReplyDeleteClick(answerList[pos])

                }

            }

        }


    }*/





}