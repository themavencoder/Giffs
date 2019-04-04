package com.example.breezil.giffs.ui.preference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.ActivityAboutBinding
import com.example.breezil.giffs.ui.BaseActivity
import com.example.breezil.giffs.utils.Constant.Companion.ZERO
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*

class AboutActivity : BaseActivity() {

    lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.about)
        val aboutPage = createPage()
        binding.aboutLayout.addView(aboutPage, ZERO)

    }

    private fun createPage(): View {
        return AboutPage(this)
            .isRTL(false)
            .setDescription(getString(R.string.about_description))
            .setImage(R.mipmap.ic_launcher_round)
            .addItem(Element().setTitle("Privacy").setValue(getString(R.string.web)))
            .addItem(Element().setTitle(String.format(getString(R.string.version))))
            .addGroup(getString(R.string.contacts))
            .addEmail(getString(R.string.email), getString(R.string.email_title))
            .addWebsite(getString(R.string.web), getString(R.string.website))
            .addTwitter(getString(R.string.twitter), getString(R.string.ontwitter))
            .addGitHub(getString(R.string.github), getString(R.string.ongithub))
            .addItem(getLibElement())
            .addItem(getCopyRights())
            .create()
    }


    private fun getCopyRights(): Element {
        val copyRightsElement = Element()
        val copyrights = String.format(
            getString(R.string.copy_right),
            Calendar.getInstance().get(Calendar.YEAR)
        )
        copyRightsElement.title = copyrights
        copyRightsElement.iconDrawable = R.drawable.ic_copyright_black_24dp
        copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
        copyRightsElement.iconNightTint = android.R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        return copyRightsElement
    }

    private fun getLibElement(): Element {
        val libElement = Element()

        libElement.title = getString(R.string.open_source_libs)
        libElement.setOnClickListener {
            LibsBuilder()
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withActivityTitle(getString(R.string.library_text))
                .withAutoDetect(true)
                .start(this@AboutActivity)

        }

        return libElement
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }
}
