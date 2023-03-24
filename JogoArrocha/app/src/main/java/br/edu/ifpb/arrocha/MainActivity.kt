package br.edu.ifpb.arrocha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.edu.ifpb.arrocha.jogo.Arrocha

class MainActivity : AppCompatActivity() {
    // usados no inicio para testes
    private lateinit var tvMenor: TextView
    private lateinit var tvMaior: TextView

    private lateinit var tvEstado: TextView
    private lateinit var etNumeroPalpite: EditText
    private lateinit var btnChutar: Button
    private lateinit var arrocha: Arrocha
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvMenor = findViewById(R.id.tvMenor)
        this.tvMaior = findViewById(R.id.tvMaior)

        this.tvEstado = findViewById(R.id.tvEstado)
        this.etNumeroPalpite = findViewById(R.id.etNumeroPalpite)
        this.btnChutar = findViewById(R.id.btnChutar)
        this.btnReset = findViewById(R.id.btnReset)

        this.arrocha = Arrocha(1, 100)

        this.f5()

        this.btnReset.setOnClickListener({
            this.arrocha = Arrocha(1, 100)
            f5()
            Toast.makeText(this, "Novo Jogo!", Toast.LENGTH_SHORT).show()
        })

        this.btnChutar.setOnClickListener(ClickBotao())
    }

    fun chutar() {
        var valor = this.etNumeroPalpite.text.toString().toInt()
        var resposta = this.arrocha.jogar(valor)
        if (resposta > 0){
            Toast.makeText(this, "Seu chute é maior!", Toast.LENGTH_SHORT).show()
        } else if (resposta < 0) {
            Toast.makeText(this, "Seu chute é menor!", Toast.LENGTH_SHORT).show()
        } else {
        }
        f5()
    }

    fun f5() {
        this.tvMenor.text = this.arrocha.menor.toString()
        this.tvMaior.text = this.arrocha.maior.toString()
        this.tvEstado.text = this.arrocha.estado.toString()
        this.etNumeroPalpite.text.clear()
    }

    inner class ClickBotao: OnClickListener{
        override fun onClick(v: View?){
            var valor = this@MainActivity.etNumeroPalpite.text.toString().toInt()
            var resposta = this@MainActivity.arrocha.jogar(valor)
            if (resposta > 0){
                Toast.makeText(this@MainActivity, "Seu chute é maior!", Toast.LENGTH_SHORT).show()
            } else if (resposta < 0) {
                Toast.makeText(this@MainActivity, "Seu chute é menor!", Toast.LENGTH_SHORT).show()
            } else {
            }
            f5()
        }
    }
}