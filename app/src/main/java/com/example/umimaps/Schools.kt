package com.example.umimaps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umimaps.models.Place
import com.example.umimaps.models.UserMap
import kotlinx.android.synthetic.main.activity_schools.*

const val POSITION = "POSITION"
class Schools : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schools)
        val umi = UserMap("Université Moulay Ismail - Présidence", listOf(Place("Université Moulay Ismail - Présidence","Université",33.8561377096017, -5.5743051724312025)))
        val est = UserMap("Ecole Supérieure de Technologie", listOf(Place("Ecole Supérieure de Technologie","Ecole",33.857241,-5.5800908)) )
        val ensam = UserMap("Ecole Nationale Supérieure des Arts et Métiers", listOf(Place("Ecole Nationale Supérieure des Arts et Métiers","Ecole",33.858272,-5.571598)) )
        val flsh = UserMap("Faculté des Lettres et des Sciences Humaines ", listOf(Place("Faculté des Lettres et des Sciences Humaines Meknès","Faculté",33.869268,-5.541034)) )
        val fs = UserMap("Faculté des sciences", listOf(Place("Faculté des sciences","Faculté",33.86747687838488, -5.544033055862062)))
        val fsjes = UserMap("Faculté des sciences juridiques, économiques et sociales", listOf(Place("Faculté des sciences juridiques, économiques et sociales","Faculté",33.88504221790842, -5.5857843405636665)))
        val ens = UserMap("Ecole normale supérieure", listOf(Place("Ecole normale supérieure","Ecole",33.88815040470112, -5.596980979012046)))
        val enam = UserMap("Ecole nationale d'agriculture", listOf(Place("Ecole nationale d'agriculture de Meknès","Ecole",33.84353672450974, -5.477545633797439)))
        val fst = UserMap("Faculté des Sciences et Technique", listOf(Place("Faculté des Sciences et Technique Errachidia","Faculté",31.93956209044431, -4.457226668039228)))

        var listSchoolsUmi  = listOf<UserMap>(umi,est,ensam,ens,enam,fs,fsjes,flsh,fst)
        RecyleSchools.layoutManager =LinearLayoutManager(this)
        RecyleSchools.adapter =MapsAdapter(this,listSchoolsUmi,object :MapsAdapter.OnClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@Schools,SchoolMapsActivity::class.java)

                intent.putExtra(POSITION,listSchoolsUmi[position])
                startActivity(intent)
            }

        })
    }


}