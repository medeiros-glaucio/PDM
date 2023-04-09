package br.edu.ifpb.fofoca

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import br.edu.ifpb.fofoca.repositorio.Fofoca

class JogoActivity : AppCompatActivity() {
    private lateinit var pbTempo: ProgressBar
    private lateinit var tvTrueOrFalse: TextView
    private lateinit var tvFofoca: TextView
    private lateinit var rbTrueResponse: RadioButton
    private lateinit var rbFalseResponse: RadioButton
    private lateinit var btnResponder: Button
    private lateinit var fofoca: Fofoca

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        this.fofoca = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("FOFOCA", Fofoca::class.java)
        } else {
            intent.getSerializableExtra("FOFOCA")
        } as Fofoca

        this.pbTempo = findViewById(R.id.pbTempo)
        this.tvTrueOrFalse = findViewById(R.id.tvVouf)
        this.tvFofoca = findViewById(R.id.tvExibeFofoca)
        this.rbTrueResponse = findViewById(R.id.rbtnRespostaVerdade)
        this.rbFalseResponse = findViewById(R.id.rbtnRespostaMentira)
        this.btnResponder = findViewById(R.id.btnResponder)

        this.tvFofoca.text = fofoca.contexto
        this.btnResponder.setOnClickListener{answer()}

        startTime()
    }

    fun answer(){
        if ((this.fofoca.veredito) && (this.rbTrueResponse.isChecked)){
            setResult(RESULT_OK)
        }else if ((!this.fofoca.veredito) && (this.rbFalseResponse.isChecked)){
            setResult(RESULT_OK)
        }
        finish()
    }

    fun startTime(){
        Thread{
            while (this.pbTempo.progress < 100){
                this.pbTempo.progress += 1
                Thread.sleep(100)
            }
            finish()
        }.start()
    }
}