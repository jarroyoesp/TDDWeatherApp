package es.jarroyo.tddweatherapp.ui.home.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.baseTest.BaseActivityRule
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeatherFactory
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocationFactory
import es.jarroyo.tddweatherapp.domain.model.location.toEntity
import org.junit.Rule
import org.junit.Test
import org.junit.matchers.JUnitMatchers.containsString
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityRule = BaseActivityRule(HomeActivity::class.java, true, false)

    @Test
    fun should_request_locations_and_show_location_on_start(){
        // Insert in DB a wetaherLocation
        val weaherLocationEntity = WeatherLocationFactory.createCurrentLocationTest().toEntity()
        DiskDataSource(mActivityRule.getContext()).insertWeatherLocation(weaherLocationEntity)

        mActivityRule.launchActivity()

        // Check textview shows location test (Zaragoza)
        onView(withId(R.id.fragment_home_tv_current_location)).check(matches(withText(containsString(WeatherLocationFactory.locationTest))))

        // Check is set in et to request current weather
        onView(withId(R.id.fragment_home_et_city)).check(matches(withText(containsString(WeatherLocationFactory.locationTest))))
    }

    @Test
    fun show_current_wetaher_when_click_on_search() {
        // Insert in DB a wetaherLocation
        val weaherLocationEntity = WeatherLocationFactory.createCurrentLocationTest().toEntity()
        DiskDataSource(mActivityRule.getContext()).insertWeatherLocation(weaherLocationEntity)

        mActivityRule.launchActivity()

        // Click search
        onView(withId(R.id.fragment_home_button_search)).perform(click())

        // Check data current weather is showed
        onView(withId(R.id.fragment_home_tv_current_weather)).check(matches(withText(containsString(CurrentWeatherFactory.currentTemp))))
    }
}