package com.example.breezil.giffs.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.FragmentSavedActionBottomSheetBinding
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.utils.Constant
import com.example.breezil.giffs.utils.Constant.Companion.GIF
import com.example.breezil.giffs.view_model.SavedViewModel
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
        updateUI(this!!.getSavedGif()!!)
        return fragment
    }

    private fun updateUI(savedGif: SavedGif) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_action_bottom_sheet, container, false)

        return binding.root
    }

    private fun getSavedGif(): SavedGif? {
        return if (arguments!!.getParcelable<SavedGif>(GIF) != null) {
            arguments!!.getParcelable(GIF)
        } else {
            null
        }
    }
}
