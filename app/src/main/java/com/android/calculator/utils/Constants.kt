package com.android.calculator.utils

data class UnitInfo(val factor: Double, val symbol: String)

object Constants {
    val LENGTH_UNITS = mapOf(
        "Meter" to UnitInfo(1.0, "m"),
        "Kilometer" to UnitInfo(1000.0, "km"),
        "Centimeter" to UnitInfo(0.01, "cm"),
        "Millimeter" to UnitInfo(0.001, "mm"),
        "Micrometer" to UnitInfo(0.000001, "µm"),
        "Nanometer" to UnitInfo(0.000000001, "nm"),
        "Mile" to UnitInfo(1609.34, "mi"),
        "Yard" to UnitInfo(0.9144, "yd"),
        "Foot" to UnitInfo(0.3048, "ft"),
        "Inch" to UnitInfo(0.0254, "in")
    )

    val MASS_UNITS = mapOf(
        "Gram" to UnitInfo(1.0, "g"),
        "Kilogram" to UnitInfo(1000.0, "kg"),
        "Milligram" to UnitInfo(0.001, "mg"),
        "Microgram" to UnitInfo(0.000001, "µg"),
        "Ton" to UnitInfo(1000000.0, "t"),
        "Pound" to UnitInfo(453.592, "lb"),
        "Ounce" to UnitInfo(28.3495, "oz"),
        "Stone" to UnitInfo(6350.29, "st"),
        "Tonne" to UnitInfo(1000000.0, "t"),
        "Carat" to UnitInfo(0.2, "ct")
    )

    val NUMERAL_UNITS = mapOf(
        "Binary" to UnitInfo(2.0, "BIN"),
        "Octal" to UnitInfo(8.0, "OCT"),
        "Decimal" to UnitInfo(10.0, "DEC"),
        "Hexadecimal" to UnitInfo(16.0, "HEX")
    )
}