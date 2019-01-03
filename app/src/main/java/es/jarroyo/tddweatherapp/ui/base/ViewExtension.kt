package es.jarroyo.tddweatherapp.ui.base

import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import es.jarroyo.tddweatherapp.R
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation




fun EditText.text(): String = this.text.toString()

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun AppCompatActivity.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}

fun TextView.setHtmlText(text: String?) {
    if (text != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY))
        } else {
            this.setText(Html.fromHtml(text))
        }
        this.movementMethod = LinkMovementMethod.getInstance()
    }
}
fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

fun ImageView.loadRoundedUrl(url: String) {
    val radius = 5
    val transformation = RoundedCornersTransformation(radius, 0)

    Picasso.get().load(url).transform(transformation).into(this)
}

fun AppCompatActivity.snackBar(message: String, onClickListener: View.OnClickListener? = null) {
    //val snack = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
    val snack = Snackbar.make(findViewById(android.R.id.content), Html.fromHtml(message), Snackbar.LENGTH_LONG)

    // Custom Snackbar
    val layout = snack.getView() as Snackbar.SnackbarLayout

    // Background
    layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))

    // TextColor
    val textView = layout.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))

    // Action
    //snack.setAction(getString(R.string.undo), onClickListener)
    //snack.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    snack.show()
}

interface SnackbarTopListener {
    fun onSnackbarAnimationFinish()
}