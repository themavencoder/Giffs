package com.example.breezil.giffs.ui.bottom_sheet


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.breezil.giffs.BuildConfig

import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.FragmentSavedActionBottomSheetBinding
import com.example.breezil.giffs.glide.GlideApp
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.ui.GifUtils
import com.example.breezil.giffs.ui.saved.SavedActivity
import com.example.breezil.giffs.utils.Constant.Companion.GIF
import com.example.breezil.giffs.ui.saved.SavedViewModel
import com.example.breezil.giffs.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SavedActionBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding : FragmentSavedActionBottomSheetBinding
    private var mContext: Context? = null
    lateinit var savedViewModel: SavedViewModel
    internal lateinit var gifUtils: GifUtils

    internal lateinit var mProgress: ProgressDialog


    fun getSavedGif(savedGif: SavedGif): SavedActionBottomSheetFragment {
        val fragment = SavedActionBottomSheetFragment()
        val args = Bundle()
        args.putParcelable(GIF, savedGif)
        fragment.arguments = args
        return fragment
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_action_bottom_sheet, container, false)
        savedViewModel = ViewModelProviders.of(this).get(SavedViewModel::class.java)
        this.mContext = activity
        gifUtils = GifUtils(mContext!!)
        mProgress = ProgressDialog(mContext)
        updateUI(this!!.getSavedGif()!!)
        return binding.root
    }
    private fun updateUI(savedGif: SavedGif) {
        val gif_image : String = BuildConfig.START_GIF + savedGif.id + BuildConfig.END_GIF_200

        binding.shareGif.setOnClickListener {
            GlideApp.with(activity!!)
                .asGif()
                .load(gif_image)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@SavedActionBottomSheetFragment.mContext, "failed", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onResourceReady(
                        resource: GifDrawable,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (ContextCompat.checkSelfPermission(
                                this@SavedActionBottomSheetFragment.mContext!!,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED){
                            startSharing(gifUtils.getLocalDrawableUri(this@SavedActionBottomSheetFragment.mContext!!,resource))

                        }else{
                            ActivityCompat.requestPermissions(
                                this@SavedActionBottomSheetFragment.mContext as Activity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constant.STORAGE_PERMISSION_CODE
                            )
                        }
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
                        Toast.makeText(this@SavedActionBottomSheetFragment.mContext, "failed", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onResourceReady(
                        resource: GifDrawable,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (ContextCompat.checkSelfPermission(
                                this@SavedActionBottomSheetFragment.mContext!!,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            mProgress.setTitle(mContext!!.getString(R.string.downloading))
                            mProgress.setMessage(mContext!!.getString(R.string.please_wait_gif_is_downloading))
                            mProgress.setCancelable(false)
                            mProgress.show()
                            val handler = Handler()
                            handler.postDelayed({
                                gifUtils.startDownloading(this@SavedActionBottomSheetFragment.mContext!!, resource)
                                mProgress.dismiss()
                                Toast.makeText(
                                    this@SavedActionBottomSheetFragment.mContext,
                                    R.string.downloaded,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, 1000)
                        }else{
                            ActivityCompat.requestPermissions(
                                this@SavedActionBottomSheetFragment.mContext as Activity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constant.STORAGE_PERMISSION_CODE
                            )

                        }

                        return true
                    }

                }).submit()
            dismiss()
        }
        binding.deleteGif.setOnClickListener { showDeleteDialog( savedGif) }
    }


    private fun startSharing(localBitmapUri: Uri) {
        val activity = activity
        if (activity != null && isAdded) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, localBitmapUri)
            startActivity(Intent.createChooser(shareIntent, "share"))

        }
        dismiss()

    }

    private fun showDeleteDialog(savedGif: SavedGif) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage(R.string.Are_you_sure_you_want_to_delete_this_image)
            .setPositiveButton(R.string.yes) { dialog, which ->
                savedViewModel.delete(savedGif)
                Toast.makeText(activity, R.string.gif_deleted, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                restartActivity()
                dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.setTitle(getString(R.string.delete_gif))
        alertDialog.show()

    }


    private fun getSavedGif(): SavedGif? {
        return if (arguments!!.getParcelable<SavedGif>(GIF) != null) {
            arguments!!.getParcelable(GIF)
        } else {
            null
        }
    }




    fun restartActivity() {
        val intent = Intent(activity, SavedActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }
}
