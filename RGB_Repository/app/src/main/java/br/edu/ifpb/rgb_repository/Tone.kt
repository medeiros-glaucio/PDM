package br.edu.ifpb.rgb_repository

import android.graphics.Color

class Tone (private var red: Int, private var green: Int, private var blue: Int, private var hexCode: String): java.io.Serializable {
    override fun toString(): String{
        return "${this.hexCode}"
    }

    fun getName(): String{
        return this.hexCode
    }

    fun getRed(): Int{
        return this.red
    }

    fun getGreen(): Int{
        return this.green
    }

    fun getBlue(): Int{
        return this.blue
    }

    fun toneGenerator(red: Int, green: Int, blue: Int){
        this.red = red
        this.green = green
        this.blue = blue
        val colorGen = Color.rgb(red, green, blue)
        this.hexCode = Integer.toHexString(colorGen)

    }

    fun getRgbTone(): Int {
        return Color.rgb(this.red, this.green, this.blue)
    }

}