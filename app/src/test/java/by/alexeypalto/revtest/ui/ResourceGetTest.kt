package by.alexeypalto.revtest.ui

import android.content.Context
import android.os.Build
import by.alexeypalto.revtest.R
import by.alexeypalto.revtest.extensions.getRateFlagByCodeResId
import by.alexeypalto.revtest.extensions.getRateNameByCodeResId
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class ResourceGetTest {
    lateinit var context: Context

    @Before
    fun setContextFromEnvironment() {
        context = RuntimeEnvironment.application
    }

    @Test
    fun returnCorrectResource() {
        val correctName = "usd"

        val resId = getRateFlagByCodeResId(context, correctName)

        assertThat(resId, equalTo(R.drawable.ic_usd_flag))
    }

    @Test
    fun returnIncorrectResourceNotFound() {
        val incorrectName = "incorrect"
        val default = R.drawable.ic_usd_flag

        val resId = getRateFlagByCodeResId(context, incorrectName)

        assertThat(resId, equalTo(default))
    }

    @Test
    fun returnCorrectString() {
        val correctName = "usd"

        val resId = getRateNameByCodeResId(context, correctName)

        assertThat(resId, equalTo(R.string.currency_usd_name))
    }


    @Test
    fun returnIncorrectStringNotFound() {
        val incorrectName = "incorrect"
        val default = R.string.currency_usd_name

        val resId = getRateNameByCodeResId(context, incorrectName)

        assertThat(resId, equalTo(default))
    }
}