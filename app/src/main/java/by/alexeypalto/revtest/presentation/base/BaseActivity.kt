package by.alexeypalto.revtest.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.alexeypalto.revtest.extensions.convertDpToPx

abstract class BaseActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    protected fun setToolbar(
        toolbar: Toolbar,
        displayShowTitleEnabled: Boolean = false,
        setDisplayHomeAsUpEnabled: Boolean = false
    ) {
        this.toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(displayShowTitleEnabled)
        supportActionBar?.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled)
    }

    fun setToolbarElevation(enableElevation: Boolean) {
        toolbar?.elevation = convertDpToPx(if (enableElevation) 4f else 0f).toFloat()

    }

}