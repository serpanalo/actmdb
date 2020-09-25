package com.serpanalo.actmdb.ui

import androidx.recyclerview.widget.RecyclerView
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


class UiTest : KoinTest {

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
     * Checks navigation to favs
     * And empty view
     * **/

    @Test
    fun clickMovieNavigatesToFavs() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.navigation_favs)).perform(click())
        onView(withId(R.id.fragment_favs_parent)).check(matches(isDisplayed()))
        if (getRVcount() == 0) {
            onView(withId(R.id.tvEmpty)).check(matches(isDisplayed()))
        }
    }

    private fun getRVcount(): Int {
        val recyclerView =
            activityTestRule.activity.findViewById(R.id.recycler_favs) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}