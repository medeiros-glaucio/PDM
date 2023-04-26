package br.edu.ifpb.rgb_repository

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView


class ToneAdjustmentForm : AppCompatActivity() {

    private lateinit var sbRed: SeekBar
    private lateinit var sbGreen: SeekBar
    private lateinit var sbBlue: SeekBar
    private lateinit var tvRed: TextView
    private lateinit var tvGreen: TextView
    private lateinit var tvBlue: TextView
    private lateinit var llTone: LinearLayout

    private lateinit var backgroundColor: ColorDrawable
    private lateinit var tvHexadecimal: TextView

    private lateinit var btnAdjFrmSave: Button
    private lateinit var btnAdjFrmCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adjustment_form)

        this.sbRed = findViewById(R.id.sbRed)
        this.sbGreen = findViewById(R.id.sbGreen)
        this.sbBlue = findViewById(R.id.sbBlue)

        this.tvRed = findViewById(R.id.tvRed)
        this.tvGreen = findViewById(R.id.tvGreen)
        this.tvBlue = findViewById(R.id.tvBlue)

        this.llTone = findViewById(R.id.llTone)
        this.tvHexadecimal = findViewById(R.id.tvHexadecimal)
        this.btnAdjFrmSave = findViewById(R.id.btnAdjFrmSave)
        this.btnAdjFrmCancel = findViewById(R.id.btnAdjFrmCancel)

        this.llTone.setBackgroundColor(this.createTone())
        this.tvHexadecimal.text = this.hexTone(this.createTone())

        this.sbRed.setOnSeekBarChangeListener(OnChangeColor())
        this.sbGreen.setOnSeekBarChangeListener(OnChangeColor())
        this.sbBlue.setOnSeekBarChangeListener(OnChangeColor())

        this.btnAdjFrmSave.setOnClickListener{salvar()}
        this.btnAdjFrmCancel.setOnClickListener{cancelar()}
    }

    fun createTone(): Int{
        val red = this@ToneAdjustmentForm.sbRed.progress
        val green = this@ToneAdjustmentForm.sbGreen.progress
        val blue = this@ToneAdjustmentForm.sbBlue.progress
        return Color.rgb(red, green, blue)
    }


    fun hexTone(tone: Int): String {
        return Integer.toHexString(tone)
    }

    fun salvar(){
        val red = this@ToneAdjustmentForm.sbRed.progress
        val green = this@ToneAdjustmentForm.sbGreen.progress
        val blue = this@ToneAdjustmentForm.sbBlue.progress
        val name = this@ToneAdjustmentForm.tvHexadecimal.text.toString()
        val tone = Tone(red, green, blue, name)
        val intent = Intent().apply {
            putExtra("COR", tone)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    fun cancelar(){
        finish()
    }

    inner class OnChangeColor: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val red = this@ToneAdjustmentForm.sbRed.progress
            val green = this@ToneAdjustmentForm.sbGreen.progress
            val blue = this@ToneAdjustmentForm.sbBlue.progress

            this@ToneAdjustmentForm.tvRed.text = red.toString()
            this@ToneAdjustmentForm.tvGreen.text = green.toString()
            this@ToneAdjustmentForm.tvBlue.text = blue.toString()

            this@ToneAdjustmentForm.llTone.setBackgroundColor(this@ToneAdjustmentForm.createTone())
            this@ToneAdjustmentForm.backgroundColor = llTone.background as ColorDrawable
            this@ToneAdjustmentForm.tvHexadecimal.text = String.format("#%06X", 0xFFFFFF and backgroundColor.color)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
        }
    }
}
