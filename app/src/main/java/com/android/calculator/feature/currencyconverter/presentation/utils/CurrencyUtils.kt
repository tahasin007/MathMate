package com.android.calculator.feature.currencyconverter.presentation.utils

import com.android.calculator.R
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object CurrencyUtils {

    val defaultCurrencyRate = CurrencyRate(
        time_last_update_utc = "Sat, 24 Aug 2024 00:00:02 +0000",
        base_code = "USD",
        conversion_rates = mapOf(
            "USD" to 1.0,
            "AED" to 3.6725,
            "AFN" to 70.7597,
            "ALL" to 89.4608,
            "AMD" to 387.9657,
            "ANG" to 1.79,
            "AOA" to 910.9379,
            "ARS" to 948.17,
            "AUD" to 1.4775,
            "AWG" to 1.79,
            "AZN" to 1.7003,
            "BAM" to 1.7517,
            "BBD" to 2.0,
            "BDT" to 119.4914,
            "BGN" to 1.7525,
            "BHD" to 0.376,
            "BIF" to 2877.9964,
            "BMD" to 1.0,
            "BND" to 1.3046,
            "BOB" to 6.9181,
            "BRL" to 5.5818,
            "BSD" to 1.0,
            "BTN" to 83.8687,
            "BWP" to 13.3666,
            "BYN" to 3.2296,
            "BZD" to 2.0,
            "CAD" to 1.3531,
            "CDF" to 2821.5218,
            "CHF" to 0.8492,
            "CLP" to 918.7021,
            "CNY" to 7.1314,
            "COP" to 4011.6405,
            "CRC" to 523.045,
            "CUP" to 24.0,
            "CVE" to 98.7594,
            "CZK" to 22.4971,
            "DJF" to 177.721,
            "DKK" to 6.6794,
            "DOP" to 59.6871,
            "DZD" to 133.8473,
            "EGP" to 48.7876,
            "ERN" to 15.0,
            "ETB" to 109.7289,
            "EUR" to 0.8957,
            "FJD" to 2.2123,
            "FKP" to 0.759,
            "FOK" to 6.6794,
            "GBP" to 0.759,
            "GEL" to 2.6982,
            "GGP" to 0.759,
            "GHS" to 15.7064,
            "GIP" to 0.759,
            "GMD" to 70.4646,
            "GNF" to 8639.3704,
            "GTQ" to 7.7362,
            "GYD" to 209.0857,
            "HKD" to 7.7975,
            "HNL" to 24.7549,
            "HRK" to 6.7483,
            "HTG" to 131.6523,
            "HUF" to 352.9973,
            "IDR" to 15495.7173,
            "ILS" to 3.6939,
            "IMP" to 0.759,
            "INR" to 83.8687,
            "IQD" to 1308.1726,
            "IRR" to 42065.5476,
            "ISK" to 136.9586,
            "JEP" to 0.759,
            "JMD" to 156.6001,
            "JOD" to 0.709,
            "JPY" to 145.0359,
            "KES" to 128.7023,
            "KGS" to 85.5604,
            "KHR" to 4071.1304,
            "KID" to 1.4775,
            "KMF" to 440.6334,
            "KRW" to 1331.4018,
            "KWD" to 0.3054,
            "KYD" to 0.8333,
            "KZT" to 480.5294,
            "LAK" to 21951.2558,
            "LBP" to 89500.0,
            "LKR" to 300.1162,
            "LRD" to 194.741,
            "LSL" to 17.8155,
            "LYD" to 4.7757,
            "MAD" to 9.6723,
            "MDL" to 17.4543,
            "MGA" to 4559.1279,
            "MKD" to 55.2514,
            "MMK" to 2099.8098,
            "MNT" to 3399.8675,
            "MOP" to 8.0314,
            "MRU" to 39.7456,
            "MUR" to 46.0038,
            "MVR" to 15.4372,
            "MWK" to 1744.1658,
            "MXN" to 19.2224,
            "MYR" to 4.3775,
            "MZN" to 63.9062,
            "NAD" to 17.8155,
            "NGN" to 1563.805,
            "NIO" to 36.7774,
            "NOK" to 10.5131,
            "NPR" to 134.1899,
            "NZD" to 1.6118,
            "OMR" to 0.3845,
            "PAB" to 1.0,
            "PEN" to 3.7442,
            "PGK" to 3.9111,
            "PHP" to 56.2636,
            "PKR" to 278.7748,
            "PLN" to 3.8333,
            "PYG" to 7593.6458,
            "QAR" to 3.64,
            "RON" to 4.4752,
            "RSD" to 105.1804,
            "RUB" to 91.4178,
            "RWF" to 1343.7835,
            "SAR" to 3.75,
            "SBD" to 8.4934,
            "SCR" to 13.4619,
            "SDG" to 510.3035,
            "SEK" to 10.2004,
            "SGD" to 1.3046,
            "SHP" to 0.759,
            "SLE" to 22.4148,
//            "SLL" to 22414.795,
            "SOS" to 571.368,
            "SRD" to 29.1458,
            "SSP" to 2912.3522,
            "STN" to 21.9435,
            "SYP" to 12847.9609,
            "SZL" to 17.8155,
            "THB" to 34.1455,
            "TJS" to 10.6026,
            "TMT" to 3.4992,
            "TND" to 3.0406,
            "TOP" to 2.3259,
            "TRY" to 34.0023,
            "TTD" to 6.7686,
            "TVD" to 1.4775,
            "TWD" to 31.7836,
            "TZS" to 2693.484,
            "UAH" to 41.2533,
            "UGX" to 3715.6586,
            "UYU" to 40.2322,
            "UZS" to 12669.1754,
            "VES" to 36.5888,
            "VND" to 24984.2516,
            "VUV" to 118.3135,
            "WST" to 2.6968,
            "XAF" to 587.5112,
            "XCD" to 2.7,
//            "XDR" to 0.7428,
            "XOF" to 587.5112,
            "XPF" to 106.8803,
            "YER" to 250.1678,
            "ZAR" to 17.7843,
            "ZMW" to 26.1109,
            "ZWL" to 13.8134
        )
    )

    fun getCurrencyFullName(currencyCode: String): String {
        val currencyFullNames = mapOf(
            "USD" to "United States Dollar",
            "AED" to "United Arab Emirates Dirham",
            "AFN" to "Afghan Afghani",
            "ALL" to "Albanian Lek",
            "AMD" to "Armenian Dram",
            "ANG" to "Netherlands Antillean Guilder",
            "AOA" to "Angolan Kwanza",
            "ARS" to "Argentine Peso",
            "AUD" to "Australian Dollar",
            "AWG" to "Aruban Florin",
            "AZN" to "Azerbaijani Manat",
            "BAM" to "Bosnia-Herzegovina Convertible Mark",
            "BBD" to "Barbadian Dollar",
            "BDT" to "Bangladeshi Taka",
            "BGN" to "Bulgarian Lev",
            "BHD" to "Bahraini Dinar",
            "BIF" to "Burundian Franc",
            "BMD" to "Bermudian Dollar",
            "BND" to "Brunei Dollar",
            "BOB" to "Bolivian Boliviano",
            "BRL" to "Brazilian Real",
            "BSD" to "Bahamian Dollar",
            "BTN" to "Bhutanese Ngultrum",
            "BWP" to "Botswana Pula",
            "BYN" to "Belarusian Ruble",
            "BZD" to "Belize Dollar",
            "CAD" to "Canadian Dollar",
            "CDF" to "Congolese Franc",
            "CHF" to "Swiss Franc",
            "CLP" to "Chilean Peso",
            "CNY" to "Chinese Yuan",
            "COP" to "Colombian Peso",
            "CRC" to "Costa Rican Colón",
            "CUP" to "Cuban Peso",
            "CVE" to "Cape Verdean Escudo",
            "CZK" to "Czech Koruna",
            "DJF" to "Djiboutian Franc",
            "DKK" to "Danish Krone",
            "DOP" to "Dominican Peso",
            "DZD" to "Algerian Dinar",
            "EGP" to "Egyptian Pound",
            "ERN" to "Eritrean Nakfa",
            "ETB" to "Ethiopian Birr",
            "EUR" to "Euro",
            "FJD" to "Fijian Dollar",
            "FKP" to "Falkland Islands Pound",
            "FOK" to "Faroese Króna",
            "GBP" to "British Pound Sterling",
            "GEL" to "Georgian Lari",
            "GGP" to "Guernsey Pound",
            "GHS" to "Ghanaian Cedi",
            "GIP" to "Gibraltar Pound",
            "GMD" to "Gambian Dalasi",
            "GNF" to "Guinean Franc",
            "GTQ" to "Guatemalan Quetzal",
            "GYD" to "Guyanese Dollar",
            "HKD" to "Hong Kong Dollar",
            "HNL" to "Honduran Lempira",
            "HRK" to "Croatian Kuna",
            "HTG" to "Haitian Gourde",
            "HUF" to "Hungarian Forint",
            "IDR" to "Indonesian Rupiah",
            "ILS" to "Israeli New Shekel",
            "IMP" to "Isle of Man Pound",
            "INR" to "Indian Rupee",
            "IQD" to "Iraqi Dinar",
            "IRR" to "Iranian Rial",
            "ISK" to "Icelandic Króna",
            "JEP" to "Jersey Pound",
            "JMD" to "Jamaican Dollar",
            "JOD" to "Jordanian Dinar",
            "JPY" to "Japanese Yen",
            "KES" to "Kenyan Shilling",
            "KGS" to "Kyrgyzstani Som",
            "KHR" to "Cambodian Riel",
            "KID" to "Kiribati Dollar",
            "KMF" to "Comorian Franc",
            "KRW" to "South Korean Won",
            "KWD" to "Kuwaiti Dinar",
            "KYD" to "Cayman Islands Dollar",
            "KZT" to "Kazakhstani Tenge",
            "LAK" to "Lao Kip",
            "LBP" to "Lebanese Pound",
            "LKR" to "Sri Lankan Rupee",
            "LRD" to "Liberian Dollar",
            "LSL" to "Lesotho Loti",
            "LYD" to "Libyan Dinar",
            "MAD" to "Moroccan Dirham",
            "MDL" to "Moldovan Leu",
            "MGA" to "Malagasy Ariary",
            "MKD" to "Macedonian Denar",
            "MMK" to "Myanmar Kyat",
            "MNT" to "Mongolian Tögrög",
            "MOP" to "Macanese Pataca",
            "MRU" to "Mauritanian Ouguiya",
            "MUR" to "Mauritian Rupee",
            "MVR" to "Maldivian Rufiyaa",
            "MWK" to "Malawian Kwacha",
            "MXN" to "Mexican Peso",
            "MYR" to "Malaysian Ringgit",
            "MZN" to "Mozambican Metical",
            "NAD" to "Namibian Dollar",
            "NGN" to "Nigerian Naira",
            "NIO" to "Nicaraguan Córdoba",
            "NOK" to "Norwegian Krone",
            "NPR" to "Nepalese Rupee",
            "NZD" to "New Zealand Dollar",
            "OMR" to "Omani Rial",
            "PAB" to "Panamanian Balboa",
            "PEN" to "Peruvian Sol",
            "PGK" to "Papua New Guinean Kina",
            "PHP" to "Philippine Peso",
            "PKR" to "Pakistani Rupee",
            "PLN" to "Polish Złoty",
            "PYG" to "Paraguayan Guaraní",
            "QAR" to "Qatari Riyal",
            "RON" to "Romanian Leu",
            "RSD" to "Serbian Dinar",
            "RUB" to "Russian Ruble",
            "RWF" to "Rwandan Franc",
            "SAR" to "Saudi Riyal",
            "SBD" to "Solomon Islands Dollar",
            "SCR" to "Seychellois Rupee",
            "SDG" to "Sudanese Pound",
            "SEK" to "Swedish Krona",
            "SGD" to "Singapore Dollar",
            "SHP" to "Saint Helena Pound",
            "SLE" to "Sierra Leonean Leone",
//            "SLL" to "Sierra Leonean Leone",
            "SOS" to "Somali Shilling",
            "SRD" to "Surinamese Dollar",
            "SSP" to "South Sudanese Pound",
            "STN" to "São Tomé and Príncipe Dobra",
            "SYP" to "Syrian Pound",
            "SZL" to "Eswatini Lilangeni",
            "THB" to "Thai Baht",
            "TJS" to "Tajikistani Somoni",
            "TMT" to "Turkmenistani Manat",
            "TND" to "Tunisian Dinar",
            "TOP" to "Tongan Paʻanga",
            "TRY" to "Turkish Lira",
            "TTD" to "Trinidad and Tobago Dollar",
            "TVD" to "Tuvaluan Dollar",
            "TWD" to "New Taiwan Dollar",
            "TZS" to "Tanzanian Shilling",
            "UAH" to "Ukrainian Hryvnia",
            "UGX" to "Ugandan Shilling",
            "UYU" to "Uruguayan Peso",
            "UZS" to "Uzbekistani Soʻm",
            "VES" to "Venezuelan Bolívar",
            "VND" to "Vietnamese Đồng",
            "VUV" to "Vanuatu Vatu",
            "WST" to "Samoan Tālā",
            "XAF" to "Central African CFA Franc",
            "XCD" to "East Caribbean Dollar",
//            "XDR" to "Special Drawing Rights",
            "XOF" to "West African CFA Franc",
            "XPF" to "CFP Franc",
            "YER" to "Yemeni Rial",
            "ZAR" to "South African Rand",
            "ZMW" to "Zambian Kwacha",
            "ZWL" to "Zimbabwean Dollar"
        )
        return currencyFullNames[currencyCode] ?: "Unknown Currency"
    }


    fun getFlagDrawable(currencyCode: String): Int {
        return when (currencyCode) {
            "USD" -> R.drawable.ic_flag_us
            "AED" -> R.drawable.ic_flag_ae
            "ALL" -> R.drawable.ic_flag_al
            "AMD" -> R.drawable.ic_flag_am
            "ANG" -> R.drawable.ic_flag_sx
            "AOA" -> R.drawable.ic_flag_ao
            "ARS" -> R.drawable.ic_flag_ar
            "AUD" -> R.drawable.ic_flag_au
            "AFN" -> R.drawable.ic_flag_af
            "AWG" -> R.drawable.ic_flag_aw
            "AZN" -> R.drawable.ic_flag_az
            "BAM" -> R.drawable.ic_flag_ba
            "BBD" -> R.drawable.ic_flag_bb
            "BDT" -> R.drawable.ic_flag_bd
            "BGN" -> R.drawable.ic_flag_bg
            "BHD" -> R.drawable.ic_flag_bh
            "BIF" -> R.drawable.ic_flag_bi
            "BMD" -> R.drawable.ic_flag_bm
            "BND" -> R.drawable.ic_flag_bn
            "BOB" -> R.drawable.ic_flag_bo
            "BRL" -> R.drawable.ic_flag_br
            "BSD" -> R.drawable.ic_flag_bhs
            "BTN" -> R.drawable.ic_flag_btn
            "BWP" -> R.drawable.ic_flag_bw
            "BYN" -> R.drawable.ic_flag_by
            "BZD" -> R.drawable.ic_flag_bz
            "CAD" -> R.drawable.ic_flag_ca
            "CDF" -> R.drawable.ic_flag_cd
            "CHF" -> R.drawable.ic_flag_ch
            "CLP" -> R.drawable.ic_flag_cl
            "CNY" -> R.drawable.ic_flag_cn
            "COP" -> R.drawable.ic_flag_co
            "CRC" -> R.drawable.ic_flag_cr
            "CUP" -> R.drawable.ic_flag_cu
            "CVE" -> R.drawable.ic_flag_cv
            "CZK" -> R.drawable.ic_flag_cz
            "DJF" -> R.drawable.ic_flag_dj
            "DKK" -> R.drawable.ic_flag_fro
            "DOP" -> R.drawable.ic_flag_do
            "DZD" -> R.drawable.ic_flag_dz
            "EGP" -> R.drawable.ic_flag_eg
            "ERN" -> R.drawable.ic_flag_er
            "ETB" -> R.drawable.ic_flag_et
            "EUR" -> R.drawable.ic_flag_eu
            "FJD" -> R.drawable.ic_flag_fj
            "FKP" -> R.drawable.ic_flag_fk
            "FOK" -> R.drawable.ic_flag_fo
            "GBP" -> R.drawable.ic_flag_gb
            "GEL" -> R.drawable.ic_flag_ge
            "GGP" -> R.drawable.ic_flag_gg
            "GHS" -> R.drawable.ic_flag_gh
            "GIP" -> R.drawable.ic_flag_gi
            "GMD" -> R.drawable.ic_flag_gm
            "GNF" -> R.drawable.ic_flag_gn
            "GTQ" -> R.drawable.ic_flag_gt
            "GYD" -> R.drawable.ic_flag_gy
            "HKD" -> R.drawable.ic_flag_hk
            "HNL" -> R.drawable.ic_flag_hn
            "HRK" -> R.drawable.ic_flag_hr
            "HTG" -> R.drawable.ic_flag_ht
            "HUF" -> R.drawable.ic_flag_hu
            "IDR" -> R.drawable.ic_flag_id
            "ILS" -> R.drawable.ic_flag_il
            "IMP" -> R.drawable.ic_flag_im
            "INR" -> R.drawable.ic_flag_in
            "IQD" -> R.drawable.ic_flag_iq
            "IRR" -> R.drawable.ic_flag_ir
            "ISK" -> R.drawable.ic_flag_is
            "JEP" -> R.drawable.ic_flag_je
            "JMD" -> R.drawable.ic_flag_jm
            "JOD" -> R.drawable.ic_flag_jo
            "JPY" -> R.drawable.ic_flag_jp
            "KES" -> R.drawable.ic_flag_ke
            "KGS" -> R.drawable.ic_flag_kg
            "KHR" -> R.drawable.ic_flag_kh
            "KID" -> R.drawable.ic_flag_ki
            "KMF" -> R.drawable.ic_flag_km
            "KRW" -> R.drawable.ic_flag_kr
            "KWD" -> R.drawable.ic_flag_kw
            "KYD" -> R.drawable.ic_flag_ky
            "KZT" -> R.drawable.ic_flag_kz
            "LAK" -> R.drawable.ic_flag_la
            "LBP" -> R.drawable.ic_flag_lb
            "LKR" -> R.drawable.ic_flag_lk
            "LRD" -> R.drawable.ic_flag_lr
            "LSL" -> R.drawable.ic_flag_ls
            "LYD" -> R.drawable.ic_flag_ly
            "MAD" -> R.drawable.ic_flag_ma
            "MDL" -> R.drawable.ic_flag_md
            "MGA" -> R.drawable.ic_flag_mg
            "MKD" -> R.drawable.ic_flag_mk
            "MMK" -> R.drawable.ic_flag_mm
            "MNT" -> R.drawable.ic_flag_mn
            "MOP" -> R.drawable.ic_flag_mo
            "MRU" -> R.drawable.ic_flag_mr
            "MUR" -> R.drawable.ic_flag_mu
            "MVR" -> R.drawable.ic_flag_mv
            "MWK" -> R.drawable.ic_flag_mw
            "MXN" -> R.drawable.ic_flag_mx
            "MYR" -> R.drawable.ic_flag_my
            "MZN" -> R.drawable.ic_flag_mz
            "NAD" -> R.drawable.ic_flag_na
            "NGN" -> R.drawable.ic_flag_ng
            "NIO" -> R.drawable.ic_flag_ni
            "NOK" -> R.drawable.ic_flag_no
            "NPR" -> R.drawable.ic_flag_np
            "NZD" -> R.drawable.ic_flag_nz
            "OMR" -> R.drawable.ic_flag_om
            "PAB" -> R.drawable.ic_flag_pa
            "PEN" -> R.drawable.ic_flag_pe
            "PGK" -> R.drawable.ic_flag_pg
            "PHP" -> R.drawable.ic_flag_ph
            "PKR" -> R.drawable.ic_flag_pk
            "PLN" -> R.drawable.ic_flag_pl
            "PYG" -> R.drawable.ic_flag_py
            "RON" -> R.drawable.ic_flag_ro
            "RSD" -> R.drawable.ic_flag_rs
            "RUB" -> R.drawable.ic_flag_ru
            "RWF" -> R.drawable.ic_flag_rw
            "SAR" -> R.drawable.ic_flag_sa
            "SBD" -> R.drawable.ic_flag_sb
            "SCR" -> R.drawable.ic_flag_sc
            "SDG" -> R.drawable.ic_flag_sd
            "SEK" -> R.drawable.ic_flag_se
            "SGD" -> R.drawable.ic_flag_sg
            "SHP" -> R.drawable.ic_flag_sh
            "SLE" -> R.drawable.ic_flag_sl
//            "SLL" -> R.drawable.ic_flag_sn
            "SOS" -> R.drawable.ic_flag_so
            "SRD" -> R.drawable.ic_flag_sr
            "SSP" -> R.drawable.ic_flag_ss
            "STN" -> R.drawable.ic_flag_st
            "SYP" -> R.drawable.ic_flag_sy
            "SZL" -> R.drawable.ic_flag_sz
            "THB" -> R.drawable.ic_flag_th
            "TJS" -> R.drawable.ic_flag_tj
            "TMT" -> R.drawable.ic_flag_tm
            "TND" -> R.drawable.ic_flag_tn
            "TOP" -> R.drawable.ic_flag_to
            "TRY" -> R.drawable.ic_flag_tr
            "TTD" -> R.drawable.ic_flag_tt
            "TVD" -> R.drawable.ic_flag_tv
            "TWD" -> R.drawable.ic_flag_tw
            "TZS" -> R.drawable.ic_flag_tz
            "UAH" -> R.drawable.ic_flag_ua
            "UGX" -> R.drawable.ic_flag_ug
            "UYU" -> R.drawable.ic_flag_uy
            "UZS" -> R.drawable.ic_flag_uz
            "VES" -> R.drawable.ic_flag_ve
            "VND" -> R.drawable.ic_flag_vn
            "VUV" -> R.drawable.ic_flag_vu
            "WST" -> R.drawable.ic_flag_ws
            "XAF" -> R.drawable.ic_flag_cm
            "XCD" -> R.drawable.ic_flag_al
//            "XDR" -> R.drawable.ic_flag_xd
            "XOF" -> R.drawable.ic_flag_ml
            "XPF" -> R.drawable.ic_flag_pf
            "YER" -> R.drawable.ic_flag_ye
            "ZAR" -> R.drawable.ic_flag_za
            "ZMW" -> R.drawable.ic_flag_zm
            "ZWL" -> R.drawable.ic_flag_zw
            else -> R.drawable.ic_flag_white
        }
    }

    fun convertToLocalTime(utcTime: String): String {
        return try {
            // Parse the UTC time string
            val inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")
            val utcDateTime = ZonedDateTime.parse(utcTime, inputFormatter)

            // Convert to local time zone
            val timeZone = ZoneId.systemDefault().id
            val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.of(timeZone))

            // Format the local time
            val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            localDateTime.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            utcTime
        }
    }
}