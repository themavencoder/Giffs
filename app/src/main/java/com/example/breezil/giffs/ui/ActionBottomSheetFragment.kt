package com.example.breezil.giffs.ui


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.breezil.giffs.BuildConfig

import com.example.breezil.giffs.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.breezil.giffs.databinding.FragmentActionBottomSheetBinding
import com.example.breezil.giffs.glide.GlideApp
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.utils.Constant.Companion.GIF
import com.example.breezil.giffs.view_model.SavedViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ActionBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentActionBottomSheetBinding
    private var mContext: Context? = null
    internal lateinit var mProgress: ProgressDialog


    fun getGif(gif: Gif): ActionBottomSheetFragment {
        val fragment = ActionBottomSheetFragment()
        val args = Bundle()
        args.putParcelable(GIF, gif)
        fragment.arguments = args
        return fragment

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_action_bottom_sheet, container, false)
        this.mContext = activity
        mProgress = ProgressDialog(mContext)
        updateUI(this!!.getGif()!!)
        return binding.root
    }


    private fun updateUI(gif: Gif){

        val gif_image : String = BuildConfig.START_GIF + gif.id + BuildConfig.END_GIF_200

        binding.saveGif.setOnClickListener { v ->
            val savedViewModel = ViewModelProviders.of(this)
                .get(SavedViewModel::class.java)

            GlideApp.with(activity!!)
                .asGif()
                .load(gif_image)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        val savedGif = SavedGif(
                            gif.score, gif.bitlyGifUrl, gif.bitlyUrl, gif.contentUrl, gif.embedUrl, gif.id,
                            gif.importDatetime, gif.isSticker, gif.rating, gif.slug, gif.source, gif.sourcePostUrl,
                            gif.sourceTld, gif.title, gif.trendingDatetime, gif.type, gif.url, gif.username
                            )
                        savedViewModel.insert(savedGif)

                        Toast.makeText(this@ActionBottomSheetFragment.mContext, "saved", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@ActionBottomSheetFragment.mContext, "failed", Toast.LENGTH_SHORT).show()
                        return true
                    }

                }).submit()


            dismiss()

        }

        binding.shareGif.setOnClickListener { v ->
            GlideApp.with(activity!!)
                .asGif()
                .load(gif_image)
                .listener(object : RequestListener<GifDrawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@ActionBottomSheetFragment.mContext, "failed", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startSharing(getLocalBitmapUri(resource,this@ActionBottomSheetFragment.mContext))
                        return true
                    }

                }).submit()
            dismiss()
        }

        binding.downloadGif.setOnClickListener { v ->
            GlideApp.with(activity!!)
                .asGif()
                .load(gif_image)
                .listener(object : RequestListener<GifDrawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@ActionBottomSheetFragment.mContext, "failed", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        mProgress.setTitle(mContext!!.getString(R.string.downloading))
                        mProgress.setMessage(mContext!!.getString(R.string.please_wait_gif_is_downloading))
                        mProgress.setCancelable(false)
                        mProgress.show()
                        val handler = Handler()
                        handler.postDelayed({
//                            startDownloading(this@ActionBottomSheetFragment.mContext!!, resource)
                            mProgress.dismiss()
                            Toast.makeText(
                                this@ActionBottomSheetFragment.mContext,
                                R.string.downloaded,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 1000)
                        return true
                    }

                }).submit()
            dismiss()
        }

    }


    private fun startSharing(localBitmapUri: Uri) {
        val activity = activity
        if (activity != null && isAdded) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/gif"
            shareIntent.putExtra(Intent.EXTRA_STREAM, localBitmapUri)
            startActivity(Intent.createChooser(shareIntent, "share"))

        }
        dismiss()

    }




    private fun getGif(): Gif? {
        return if (arguments!!.getParcelable<Gif>(GIF) != null) {
            arguments!!.getParcelable(GIF)
        } else {
            null
        }
    }

    fun getLocalBitmapUri(gif: GifDrawable?, context: Context?): Uri {
        var bmpUri: Uri? = null
        try
        {
            val file = File(
                context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                (context!!.getString(R.string.share_image_) +
                        System.currentTimeMillis() + context.getString(R.string.gif)))
            val out = FileOutputStream(file)
            if (gif != null) {
                gif.firstFrame.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            out.flush()
            out.close()
            bmpUri = Uri.fromFile(file)
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
        return bmpUri!!
    }

    fun startDownloading(context: Context, image: Bitmap): String? {
        var savedImagePath: String? = null
        // Create the new file in the external storage
        val timeStamp = SimpleDateFormat(
            context.getString(R.string.date_format),
            Locale.getDefault()
        ).format(Date())
        val imageFileName = context.getString(R.string.app_name) + timeStamp + context.getString(R.string.gif)
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + context.getString(
                R.string._slash_pixxo
            )
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }

        // Save the new Bitmap
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fileOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fileOut)
                fileOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(context, savedImagePath)

        }

        return savedImagePath
    }

    private fun galleryAddPic(context: Context, gifPath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(gifPath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }



}



