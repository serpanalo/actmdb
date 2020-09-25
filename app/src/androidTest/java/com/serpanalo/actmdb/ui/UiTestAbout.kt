package com.serpanalo.actmdb.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.antonioleiva.mymovies.ui.MockWebServerRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.data.server.TheMovieDb
import com.serpanalo.actmdb.ui.navigation.MainActivity
import com.serpanalo.actmdb.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get


class UiTestAbout : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popularmovies.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<TheMovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }


    /**
     * Checks Binding data in about fragment
     * **/

    @Test
    fun testNavigateToAbout() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.navigation_about)).perform(click())
        onView(withId(R.id.fragment_about_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText("Sergio Pantoja Alonso")))
    }
}