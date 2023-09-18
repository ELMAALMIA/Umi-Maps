package com.example.umimaps

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umimaps.models.Place
import com.example.umimaps.models.UserMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
private const val REQUEST_CODE = 1234
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
class MainActivity : AppCompatActivity() {

    private lateinit var userMaps:MutableList<UserMap>
    private lateinit var mapAdapter :MapsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         userMaps = generateSampleData().toMutableList();
        //layout RecylerView
        MyRecyclerView.layoutManager = LinearLayoutManager(this)
        //Adapter RecyclerViewé
        mapAdapter = MapsAdapter(this, userMaps,object: MapsAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                var intent : Intent
                if(position==0){
                    intent = Intent(this@MainActivity,Schools::class.java)

                    startActivity(intent)
                }else{
                    intent= Intent(this@MainActivity,DisplayMapActivity::class.java)
                    intent.putExtra(EXTRA_USER_MAP,userMaps[position])
                    startActivity(intent)
                }
            }
        })
        MyRecyclerView.adapter = mapAdapter

        buttonadd.setOnClickListener{
            showAlertDialog()
        }
    }
    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //get new map data from the data
            val userMap = data?.getSerializableExtra(EXTRA_USER_MAP) as UserMap
            userMaps.add(userMap)
            mapAdapter.notifyItemInserted(userMaps.size-1)
        }
    }
    private fun showAlertDialog(){
        val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map,null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Map title")
            .setView(mapFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("ok",null)
            .show()

        //when clicking on the positive button
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val title = mapFormView.findViewById<EditText>(R.id.ettitle).text.toString()
            if(title.trim().isEmpty()){
                Toast.makeText(this,"Place must have non-empty title ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //navigate to create map activity
            val intent = Intent(this@MainActivity,CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE,title)
            startActivityForResult(intent, REQUEST_CODE)
            dialog.dismiss()
        }

    }

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Faculties and schools of UMI",
                listOf(
                    Place("", "", 0.0,  0.0)
                )
            ),
            UserMap("Coffeehouses and restaurants to try",
                listOf(
                    Place("Café NadaBella", "Great Coffee here", 33.85853780453219, -5.5754968044822215),
                    Place("Black Iris", "Great restaurant", 33.86370521291895, -5.576759139270766),
                    Place("Estrella", "Great restaurant", 33.85967912494225, -5.569631202187817),
                    Place("Twin", "Great Coffe here", 33.85876370334306, -5.570561935915655)
                )),
            UserMap("Pharmacies near your school",
                listOf(
                    Place("Pharmacie Tilleul Marjane", "", 33.85446941232293, -5.581303927225386),
                    Place("Pharmacie el bassma", "", 33.8600558128709, -5.577625104554922),
                    Place("Pharmacie Arrazi", "", 33.860262439235086, -5.580545631384388),
                    Place("Pharmacie le jasmin", "", 33.859737921671275, -5.568400704058604),
                    Place("Pharmacie Ryad el ismailia", "", 33.8566962629351, -5.567615623840334)
                )
            ),
            UserMap("Places to hang out and visit",
                listOf(
                    Place("SARIJ souani", "Great place to visit", 33.88175210433678, -5.559635906153049),
                    Place("Prison de Kara", "Great place to visit", 33.8914189698665, -5.563937507649715),
                    Place("El hedime Square", "Great place to visit", 33.893303342437335, -5.564793859497202),
                    Place("Mausolée de Moulay Ismaïl", "Great place to visit", 33.89099006126796, -5.5627838650399255),
                    Place("Musée Dar Eljamîi", "Great place to visit", 33.893802031123535, -5.566393121780371)
                )
            ),
            UserMap("Gyms and clubs near your school",
                listOf(
                    Place("Savana Fitness", "Meilleurs cotchs et bon services", 33.8625328951381, -5.578830874487217),
                    Place("EXtra Fitness", "Meilleurs cotchs et bon services", 33.860053714351146, -5.570470199984497),
                    Place("SMAILI SPORT CLUB", "Meilleurs cotchs et bon services", 33.86142092394263, -5.563133951294023),
                    Place("Hamza Sport Club", "Meilleurs cotchs et bon services", 33.85779654938575, -5.5606114212921955)
                )
            )
        )
    }
}