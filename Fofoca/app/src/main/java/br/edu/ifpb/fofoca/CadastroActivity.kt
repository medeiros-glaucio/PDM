package br.edu.ifpb.fofoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import br.edu.ifpb.fofoca.repositorio.Fofoca

class CadastroActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var etFofoca: EditText
    private lateinit var rbTrue: RadioButton
    private lateinit var rbFalse: RadioButton
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        this.btnCancel = findViewById(R.id.btnCancelar)
        this.etFofoca = findViewById(R.id.etCadastroFofoca)
        this.rbFalse = findViewById(R.id.rbtnCadastroMentira)
        this.rbTrue = findViewById(R.id.rbtnCadastroVerdade)
        this.btnSave = findViewById(R.id.btnSalvar)
        this.btnSave.setOnClickListener{salve()}
        this.btnCancel.setOnClickListener{volte()}
    }

    fun salve(){
        val contexto = this.etFofoca.text.toString()
        val veredito = this.rbTrue.isChecked
        val fofoca = Fofoca(contexto, veredito)
        val intent = Intent().apply{
            putExtra("FOFOCA", fofoca)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    fun volte(){
        finish()
    }
}


