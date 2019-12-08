package figueroa.rodriguez.reciclado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var personas:ArrayList<Persona>? = null
    var adapter:PersonaAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        miRecycler.layoutManager = GridLayoutManager(this, 1)
        miRecycler.setHasFixedSize(true)
        personas = ArrayList()
        adapter = PersonaAdapter(personas!!, this)
        miRecycler.adapter = adapter



        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())

        val requestQueue = RequestQueue(cache,network).apply {
            start()
        }
        val url = "https://randomuser.me/api/?page=3&results=50"

        val jsonObjectPersonas = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val resultadosJSON = response.getJSONArray("results")

                for (indice in 0..resultadosJSON.length()-1){
                    val personaJSON = resultadosJSON.getJSONObject(indice)
                    val genero = personaJSON.getString("gender")//genero
                    val nombreJSON = personaJSON.getJSONObject("name")
                    val nombrePersona = "${nombreJSON.getString("title")} ${nombreJSON.getString("first")} ${nombreJSON.getString("last")}"//titulo, nombre, apellido
                    val fotoJSON = personaJSON.getJSONObject("picture")
                    val foto = fotoJSON.getString("large")
                    val locationJSON = personaJSON.getJSONObject("location")
                    val coordJSON = locationJSON.getJSONObject("coordinates")
                    val latitud = coordJSON.getString("latitude").toDouble()
                    val longitud = coordJSON.getString("longitude").toDouble()

                    personas!!.add(Persona(nombrePersona,foto,longitud,latitud,genero))
                }
                adapter!!.notifyDataSetChanged()


            },Response.ErrorListener { error ->
                Log.wtf("error volley",error.localizedMessage)
            })
        requestQueue.add(jsonObjectPersonas)
    }
}
