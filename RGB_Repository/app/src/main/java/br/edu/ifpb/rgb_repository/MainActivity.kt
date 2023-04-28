package br.edu.ifpb.rgb_repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var rvMainTones: RecyclerView
    private lateinit var fabMainAdd: FloatingActionButton
    private var tones: MutableList<Tone>

    init {
        this.tones = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvMainTones = findViewById(R.id.rvMainTones)
        this.fabMainAdd = findViewById(R.id.fabMainAdd)

        val adapter = MyAdapter(this.tones)
        adapter.onItemClick = OnItemClick()
        this.rvMainTones.adapter = adapter

        ItemTouchHelper(OnSwipe()).attachToRecyclerView((this.rvMainTones))

        var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val tone = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("COR", Tone::class.java)
                } else {
                    it.data?.getSerializableExtra("COR")
                } as Tone
                (this.rvMainTones.adapter as MyAdapter).add(tone)
            }
        }

        this.fabMainAdd.setOnClickListener{
            val intent = Intent()
            intent.action = "NOVACOR"
            formResult.launch(intent)
        }

    }

    inner class OnItemClick: OnItemClickRecyclerView{
        override fun onItemClick(position: Int) {

            val intent = Intent()
            intent.action = "NOVACOR"
            startActivity(intent)

            val inflater = LayoutInflater.from(this@MainActivity)
            val view = inflater.inflate(R.layout.activity_adjustment_form, null)
            val sbRed = view.findViewById<SeekBar>(R.id.sbRed)
            val sbGreen = view.findViewById<SeekBar>(R.id.sbGreen)
            val sbBlue = view.findViewById<SeekBar>(R.id.sbBlue)
            val llTone = view.findViewById<LinearLayout>(R.id.llTone)
            //
            val btnAdjFrmCancel = view.findViewById<Button>(R.id.btnAdjFrmCancel)
            val btnAdjFrmSave = view.findViewById<Button>(R.id.btnAdjFrmSave)


            val tvHexadecimal = view.findViewById<TextView>(R.id.tvHexadecimal)

            val colorSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val redValue = sbRed.progress
                    val greenValue = sbGreen.progress
                    val blueValue = sbBlue.progress
                    val color = Color.rgb(redValue, greenValue, blueValue)
                    llTone.setBackgroundColor(color)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Nihil
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Nihil
                }
            }

            sbRed.setOnSeekBarChangeListener(colorSeekBarChangeListener)
            sbGreen.setOnSeekBarChangeListener(colorSeekBarChangeListener)
            sbBlue.setOnSeekBarChangeListener(colorSeekBarChangeListener)

        }
    }

    inner class OnSwipe : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END) {


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if(32 == direction){
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Excluir tom de cor")
                builder.setMessage("Confirma exclusão de tom de cor?")
                builder.setPositiveButton("Excluir") { dialog, which ->
                    (this@MainActivity.rvMainTones.adapter as MyAdapter).del(viewHolder.adapterPosition)
                }
                builder.setNegativeButton("Cancelar") { dialog, which ->
                    (this@MainActivity.rvMainTones.adapter as MyAdapter).notifyItemChanged(viewHolder.adapterPosition)
                }
                builder.show()
            } else if (16 == direction){
                val tone = (this@MainActivity.rvMainTones.adapter as MyAdapter).lista[viewHolder.adapterPosition]
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_SUBJECT, "Tom de cor em RGB e hexadecimal!")
                    putExtra(Intent.EXTRA_TEXT, "Hexadecimal: ${tone.getName()} red: ${tone.getRed()} green: ${tone.getGreen()} blue: ${tone.getBlue()}")
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Gmail não disponível", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {(this@MainActivity.rvMainTones.adapter as MyAdapter).mov(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

    }

}


