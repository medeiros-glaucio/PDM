package br.edu.ifpb.janelapopup

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import br.edu.ifpb.janelapopup.OnItemLongClickRecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvNomes: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private var lista = mutableListOf<String>()
    private lateinit var etNome: EditText
    private lateinit var tts: TextToSpeech
    private var lugar: Int = 0

    init {
        this.lista.add("Primeiro")
        this.lista.add("Segundo")
//        this.lista.add("Terceiro")
//        this.lista.add("Quarto")
//        this.lista.add("Quinto")
//        this.lista.add("Sexto")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvNomes = findViewById(R.id.rvNomes)
        this.fabAdd = findViewById(R.id.fabAdd)

        this.fabAdd.setOnClickListener{ add() }

        this.rvNomes.adapter = MyAdapter(this.lista)
        (this.rvNomes.adapter as MyAdapter).onItemClickRecyclerView = OnItemClick()

        this.tts = TextToSpeech(this, null)


        (this.rvNomes.adapter as MyAdapter).onItemLongClickRecyclerView = LongClickList()

        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvNomes)
    }

    fun add(){
        this.etNome = EditText(this)
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Novo Nome!")
            setMessage("Digite o novo nome")
            setView(this@MainActivity.etNome)
            setPositiveButton("Salvar", OnClick())
            setNegativeButton("Cancelar", null)
        }
        builder.create().show()
    }

    inner class OnClick: OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val nome = this@MainActivity.etNome.text.toString()
            (this@MainActivity.rvNomes.adapter as MyAdapter).add(nome)
        }
    }

    inner class OnItemClick: OnItemClickRecyclerView{
        override fun onItemClick(position: Int) {
            val nome = this@MainActivity.lista.get(position)
//            Toast.makeText(this@MainActivity, nome, Toast.LENGTH_SHORT).show()
            this@MainActivity.tts.speak(nome, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    inner class LongClickList: OnItemLongClickRecyclerView {
        override fun onItemLongClick(position: Int): Boolean {
            lugar = position
            deletar(this@MainActivity.lista.get(position))
            return true
        }
    }

    fun deletar(termo: String): Boolean {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Apagar...")
            setMessage("Confirma?")
            setNegativeButton("Não", null)
            setPositiveButton("Sim", ConfirmedDelete())
        }
        builder.create().show()
        (this.rvNomes.adapter as MyAdapter).notifyDataSetChanged()
        return true
    }

    inner class ConfirmedDelete: OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            (this@MainActivity.rvNomes.adapter as MyAdapter).delete(lugar)
            (this@MainActivity.rvNomes.adapter as MyAdapter).notifyDataSetChanged()
        }
    }

    inner class OnSwipe: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean {
            (this@MainActivity.rvNomes.adapter as MyAdapter).move(
                viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){

        }
    }

}
