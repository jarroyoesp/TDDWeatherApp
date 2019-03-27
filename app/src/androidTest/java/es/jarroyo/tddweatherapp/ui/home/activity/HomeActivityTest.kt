package es.jarroyo.tddweatherapp.ui.home.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.baseTest.BaseActivityRule
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocationFactory
import es.jarroyo.tddweatherapp.domain.model.location.toEntity
import es.jarroyo.tddweatherapp.domain.model.user.UserFactory
import es.jarroyo.tddweatherapp.ui.utils.RecyclerViewMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityRule = BaseActivityRule(HomeActivity::class.java, true, false)


    @Before
    fun insertData() {
        // Insert USER ENTITY Test
        DiskDataSource(mActivityRule.getContext()).insertUser(UserFactory.createUserTest().toEntity())

        // Insert in DB a wetaherLocation
        val weaherLocationEntity = WeatherLocationFactory.createCurrentLocationTest().toEntity()
        DiskDataSource(mActivityRule.getContext()).insertWeatherLocation(weaherLocationEntity)
    }


    @Test
    fun should_request_locations_and_show_location_on_start(){
        mActivityRule.launchActivity()

        // Check RV shows location test (Zaragoza)
        onView(withRecyclerView(R.id.fragment_home_rv).atPosition(0))
            .check(matches(hasDescendant(withText(WeatherLocationFactory.locationTest))))

    }

    @Test
    fun GIVEN_a_location_saved_WHEN_click_in_weather_THEN_show_forecast() {
        mActivityRule.launchActivity()

        // Check RV shows location test (Zaragoza) and Perform click
        onView(withRecyclerView(R.id.fragment_home_rv).atPosition(0))
            .check(matches(hasDescendant(withText(WeatherLocationFactory.locationTest)))).perform(click())

        // Check forecast detail is shown
        onView(withId(R.id.fragment_forecast_rv)).check(matches(isDisplayed()))

    }

   @Test
   fun GIVEN_app_init_WHEN_user_click_BottomNavigationView_THEN_show_fragments() {
       mActivityRule.launchActivity()

       onView(withId(R.id.navigation_account)).perform(click())

       onView(withId(R.id.navigation_home)).perform(click())

       onView(withRecyclerView(R.id.fragment_home_rv).atPosition(0))
           .check(matches(hasDescendant(withText(WeatherLocationFactory.locationTest))))

       onView(withId(R.id.navigation_account)).perform(click())

       pressBack()
   }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}