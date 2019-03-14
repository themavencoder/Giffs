package com.example.breezil.giffs.ui.bottom_sheet


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.breezil.giffs.BuildConfig

import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.FragmentSavedActionBottomSheetBinding
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.utils.Constant.Companion.GIF
import com.example.breezil.giffs.ui.saved.SavedViewModel
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
        updateUI(this!!.getSavedGif()!!)
        return binding.root
    }
    private fun updateUI(savedGif: SavedGif) {
        val gif_image : String = BuildConfig.START_GIF + savedGif.id + BuildConfig.END_GIF_200

        binding.deleteGif.setOnClickListener { showDeleteDialog( savedGif) }
    }

    private fun showDeleteDialog(savedGif: SavedGif) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage(R.string.Are_you_sure_you_want_to_delete_this_image)
            .setPositiveButton(R.string.yes) { dialog, which ->
                savedViewModel.delete(savedGif)
                Toast.makeText(activity, R.string.gif_deleted, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
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
}
