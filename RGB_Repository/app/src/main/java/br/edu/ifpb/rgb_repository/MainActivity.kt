package br.edu.ifpb.rgb_repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private lateinit var rvMainTones: RecyclerView
    private lateinit var fabMainAdd: FloatingActionButton
    private var tones: MutableList<Tone>
    private var local: Int = 0

    init {
        this.tones = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvMainTones = findViewById(R.id.rvMainTones)
        this.fabMainAdd = findViewById(R.id.fabMainAdd)

        var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val tone = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("COR", Tone::class.java)
                } else {
                    it.data?.getSerializableExtra("COR")
                } as Tone
                (this.rvMainTones.adapter as MyAdapter).add(tone)
                prepareTone()
            }
        }


        this.fabMainAdd.setOnClickListener{
            val intent = Intent()
            intent.action = "NOVACOR"
            formResult.launch(intent)
        }

        prepareTone()

        ItemTouchHelper(OnSwipe()).attachToRecyclerView((this.rvMainTones))

    }

    fun prepareTone(){
        this.rvMainTones.adapter = MyAdapter(this.tones)

        (this.rvMainTones.adapter as MyAdapter).onItemClick = OnItemClick()
    }

    inner class OnItemClick: OnItemClickRecyclerView{
        override fun onItemClick(position: Int): Boolean {

            local = position
            locateTone()
            return true
        }
    }

    fun locateTone() {
        var tone = this@MainActivity.tones.get(local)
        val intent = Intent(this, ToneAdjustmentForm :: class.java)
        intent.putExtra("COR", tone)
        formResult.launch(intent)
    }

    var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val tone = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("COR", Tone::class.java)
            } else {
                it.data?.getSerializableExtra("COR")
            } as Tone
            (this.tones.set(local, tone))
            prepareTone()
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


