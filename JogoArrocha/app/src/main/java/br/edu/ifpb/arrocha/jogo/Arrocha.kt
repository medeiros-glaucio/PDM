package br.edu.ifpb.arrocha.jogo

import android.util.Log

class Arrocha (var menor: Int, var maior: Int){
    var sorteio: Int
    var estado: Estado

    init {
        this.sorteio = ((this.menor + 1) .. (this.maior -1)).random()
        this.estado = Estado.EXECUTANDO
        Log.i("APP_ARROCHA", "Número sorteado ${this.sorteio}")
    }

    fun arrochado(): Boolean{
        return this.menor + 1 == this.maior - 1
    }

    fun atualizaIntervalo(chute: Int): Int{
        if (chute < this.sorteio){
            this.menor = chute
            return -1
        } else {
            this.maior = chute
            return 1
        }
    }

    fun chuteValido(chute: Int): Boolean {
        return chute > this.menor && chute < this.maior && chute != this.sorteio
    }

    fun jogar(chute: Int): Int{
        if (!this.chuteValido(chute)) {
            this.estado = Estado.PERDEU
            return 0
        } else {
            var valor = this.atualizaIntervalo(chute)
            if (this.arrochado()){
                this.estado = Estado.GANHOU
            }
            return valor
        }
    }
}