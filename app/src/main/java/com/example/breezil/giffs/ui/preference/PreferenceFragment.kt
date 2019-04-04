package com.example.breezil.giffs.ui.preference


import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.FragmentPreferenceBinding
import com.example.breezil.giffs.ui.trending.MainActivity

import android.os.Handler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PreferenceFragment : DialogFragment() {
    lateinit var binding: FragmentPreferenceBinding
    private var mContext: Context? = null
    var something :Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_preference, container, false)
        this.mContext = activity
        updateUI()


        binding.closeButton.setOnClickListener {

            dialog!!.dismiss()
            restartActivity()
            dismiss()
        }
        return binding.root
    }

    fun updateUI() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->


            if (isChecked) {
                binding.switchButton.text = getString(R.string.night_mode_enabled)
                val editor: SharedPreferences.Editor =
                    mContext!!.getSharedPreferences(getString(R.string.PACKAGE_NAME), MODE_PRIVATE).edit()
                editor.putBoolean(getString(R.string.theme_state), true)
                editor.apply()


            } else {
                binding.switchButton.text = getString(R.string.night_mode_disable)
                val editor: SharedPreferences.Editor =
                    mContext!!.getSharedPreferences(getString(R.string.PACKAGE_NAME), MODE_PRIVATE).edit()
                editor.putBoolean(getString(R.string.theme_state), false)
                editor.apply()


            }

        }

        val sharedPrefs : SharedPreferences = mContext!!.getSharedPreferences(getString(R.string.PACKAGE_NAME), MODE_PRIVATE)
        binding.switchButton.isChecked = sharedPrefs.getBoolean(getString(R.string.theme_state), false)





    }

    fun restartActivity(){
        Handler().post(Runnable {
            val intent = activity!!.intent
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
            activity!!.overridePendingTransition(0, 0)
            activity!!.finish()

            activity!!.overridePendingTransition(0, 0)
            startActivity(intent)
        })
    }


}
