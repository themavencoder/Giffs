package com.example.breezil.giffs.ui


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.bumptech.glide.request.RequestOptions
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
        updateUI(this!!.getGif()!!)
        return binding.root
    }


    private fun updateUI(gif: Gif){

        val gif_image : String = BuildConfig.START_GIF + gif.id + BuildConfig.END_GIF_200

        binding.shareGif.setOnClickListener { v ->
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
                            score = gif.score,
                            bitlyGifUrl = gif.bitlyGifUrl,
                            bitlyUrl = gif.bitlyUrl,
                            contentUrl = gif.contentUrl,
                            embedUrl = gif.embedUrl,
                            id = gif.id,
                            importDatetime = gif.importDatetime,
                            isSticker = gif.isSticker,
                            rating = gif.rating,
                            slug = gif.slug,
                            source = gif.source,
                            sourcePostUrl = gif.sourcePostUrl,
                            sourceTld = gif.sourceTld,
                            title = gif.title,
                            trendingDatetime = gif.trendingDatetime,
                            type = gif.type,
                            url = gif.url,
                            username = gif.username
                            )
                        savedViewModel.insert(savedGif)

                        Toast.makeText(this@ActionBottomSheetFragment.mContext, "Saved", Toast.LENGTH_SHORT).show()
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

        binding.saveGif.setOnClickListener { v ->
            Toast.makeText(this@ActionBottomSheetFragment.mContext, "saved", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.downloadGif.setOnClickListener { v ->
            Toast.makeText(this@ActionBottomSheetFragment.mContext, "download", Toast.LENGTH_SHORT).show()
            dismiss()
        }

    }


    private fun getGif(): Gif? {
        return if (arguments!!.getParcelable<Gif>(GIF) != null) {
            arguments!!.getParcelable(GIF)
        } else {
            null
        }
    }

}

