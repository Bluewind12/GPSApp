package momonyan.gpsapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LocationListener {

    private var manager: LocationManager? = null
    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkButton.setOnClickListener {
            val text = "緯度：" + location!!.latitude.toString() + "経度：" + location!!.longitude
            var textView = TextView(this)
            textView.text = text
            mainLinearLayout.addView(textView)
        }
    }


    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        manager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1f, this)
        manager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1f, this)
    }

    override fun onStop() {
        super.onStop()
        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            manager!!.removeUpdates(this)
        }
    }

    override fun onLocationChanged(p0: Location) {
        val text = "緯度：" + p0.latitude + "経度：" + p0.longitude

        location = p0

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }
}
