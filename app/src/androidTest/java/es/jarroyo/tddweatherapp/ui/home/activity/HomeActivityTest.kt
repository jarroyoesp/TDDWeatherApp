package es.jarroyo.tddweatherapp.ui.home.activity

import android.support.test.runner.AndroidJUnit4
import es.jarroyo.tddweatherapp.app.baseTest.BaseActivityRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityRule = BaseActivityRule(HomeActivity::class.java, true, false)

    @Test
    fun should_request_locations_on_start(){
        mActivityRule.launchActivity()
    }
}