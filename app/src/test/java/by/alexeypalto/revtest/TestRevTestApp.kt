package by.alexeypalto.revtest

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], application = RevTestApp::class)
class MainApplicationTest {

    @Test
    fun packageName() {
        assertThat(RuntimeEnvironment.application).isInstanceOf(RevTestApp::class.java)
    }
}