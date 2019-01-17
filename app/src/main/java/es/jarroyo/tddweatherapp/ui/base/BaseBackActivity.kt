package es.jarroyo.tddweatherapp.ui.base

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import es.jarroyo.tddweatherapp.R
import kotlinx.android.synthetic.main.activity_base_back.*

abstract class BaseBackActivity : BaseActivity() {
    override var layoutId = R.layout.activity_base_back

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configView()
    }

    fun configView(){
        prepareToolbar()
    }


    private fun prepareToolbar() {
        setSupportActionBar(activity_base_back_toolbar)
        showBackButton()
    }

    private fun showBackButton() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setTitle("")
            val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back_button)
            upArrow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
            supportActionBar!!.setHomeAsUpIndicator(upArrow)
        }
    }

    fun hideToolbar() {
        if (supportActionBar != null) {
            activity_base_back_toolbar.visibility = View.GONE
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowHomeEnabled(false)
        }
    }

    fun setTitleToolbar(title: String) {
        supportActionBar?.title = title

    }

    /**
     * ADD FRAGMENT TO MAIN LAYOUT
     */
    fun addFragmentToMainLayout(fragment: Fragment){
        val ft = supportFragmentManager?.beginTransaction()
        ft?.replace(R.id.activity_base_back_layout_fragment_container, fragment)?.commit()
    }
}
