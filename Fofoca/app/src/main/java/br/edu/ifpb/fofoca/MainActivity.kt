package br.edu.ifpb.fofoca

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifpb.fofoca.repositorio.Cadastro
import br.edu.ifpb.fofoca.repositorio.Fofoca

class MainActivity : AppCompatActivity() {
    // Botões da tela principal
    private lateinit var tvMain: TextView
    private lateinit var btnJogar: Button
    private lateinit var btnCadastrar: Button
    private var cadastro: Cadastro

    init {
        this.cadastro = Cadastro()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvMain = findViewById(R.id.tvJogoDaFofoca)
        this.btnJogar = findViewById(R.id.btnJogar)
        this.btnCadastrar = findViewById(R.id.btnCadastrar)

        var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                val fofoca = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("FOFOCA", Fofoca::class.java)
                } else {
                    it.data?.getSerializableExtra("FOFOCA")
                } as Fofoca
                this.cadastro.coleta(fofoca)
                Toast.makeText(this, "Fofoca cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

        var playResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                Toast.makeText(this, "Venceu!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Perdeu!", Toast.LENGTH_SHORT).show()
            }
        }

        this.btnCadastrar.setOnClickListener{
            val intent = Intent( this, CadastroActivity::class.java)
            formResult.launch(intent)
        }

        this.btnJogar.setOnClickListener {
            val fofoca = this.cadastro.exibeFofoca()
            val intent = Intent(this, JogoActivity::class.java).apply {
                putExtra("FOFOCA", fofoca)
            }
            playResult.launch(intent)
        }
    }

    fun jogar() {
        val intent = Intent(this, JogoActivity::class.java)
        startActivity(intent)
    }
}