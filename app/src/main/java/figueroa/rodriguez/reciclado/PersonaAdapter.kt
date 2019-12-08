package figueroa.rodriguez.reciclado

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mi_tarjeta.view.*

class PersonaAdapter(personas:ArrayList<Persona>, context:Context): RecyclerView.Adapter<PersonaAdapter.ViewHolder>(){
    var elementos:ArrayList<Persona>?=null
    var contexto:Context?=null

    init {
        this.elementos = personas
        this.contexto = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PersonaAdapter.ViewHolder {
        val vistaPersona:View = LayoutInflater.from(contexto).inflate(R.layout.mi_tarjeta,parent, false)
        val lugarViewHolder = ViewHolder(vistaPersona)
        vistaPersona.tag = lugarViewHolder
        return lugarViewHolder
    }

    override fun getItemCount(): Int {
        return this.elementos!!.count()
    }

    override fun onBindViewHolder(p0: PersonaAdapter.ViewHolder, p1: Int) {

        p0.nombre!!.text = elementos!![p1].nombre
        p0.genero!!.text = elementos!![p1].genero
        Picasso.get().load(elementos!![p1].foto)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(p0.imagen)
    }

    class ViewHolder(vista:View):RecyclerView.ViewHolder(vista) {
        var imagen:ImageView?= null
        var nombre:TextView?=null
        var genero:TextView?=null

        init {

            imagen = vista.ivPersona
            nombre = vista.tvNombre
            genero = vista.tvGenero
        }
    }
}