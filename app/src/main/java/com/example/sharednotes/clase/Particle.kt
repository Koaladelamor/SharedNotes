package com.example.sharednotes.clase

class Particle(
    var name: String="",
    val family: Family=Family.QUARK,
    val mass: Double=0.0,
    val charge: String="",
    var spin: String=""

) {
public enum class Family{ QUARK, LEPTON, SCALAR_BOSON, GAUGE_BOSON }
}