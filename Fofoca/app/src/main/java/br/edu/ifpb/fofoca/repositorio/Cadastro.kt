package br.edu.ifpb.fofoca.repositorio

class Cadastro {
    private var fofocas: MutableList<Fofoca>

    init {
        this.fofocas = mutableListOf()
    }

    fun coleta(fofoca: Fofoca){
        this.fofocas.add(fofoca)
    }

    fun exibeFofoca(): Fofoca?{
        if(fofocas.size > 0)
            return this.fofocas.random()
        else return null
    }
}