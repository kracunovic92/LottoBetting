
import com.example.lotto.data.Event
import com.example.lotto.data.OddValues
import com.example.lotto.data.TicketSystemCombinationGroup
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class CalculationResult {
    var brutoPayin = 0.0 //bruto uplata
    var payinTax = 0.0 //porez na uplati (pare)
    var quota = 0.0 //kvota
    var win = 0.0 //dobitak bez bonusa
    var bonus = 0.0 //bonus - suma bonusa
    var bonusPercent = 0.0 //procenat bonusa koji vraca javascript
    var payoutTax = 0.0 //porez na isplati (pare)
    var payout = 0.0 //isplata koja ide korisniku (neto)
    var combinations = 0 //broj kombinacija
    var rowNumber = 0 //broj kuglica
    var areAnyRowsStarted = false
    var ticketSplitCount = 0
    var ticketSplitAmount = 0.0
    var selectedSystems: List<Int> = ArrayList() //selektovani sistemi
    var fixes = 0
    var freebetType: String? = null
}


object MathUtils {
    val twoDecimals = DecimalFormat("#.00")
    val oneDecimal = DecimalFormat("#.0")

    init {
        val sym = DecimalFormatSymbols.getInstance()
        sym.decimalSeparator = '.'
        oneDecimal.decimalFormatSymbols = sym
        twoDecimals.decimalFormatSymbols = sym
        //twoDecimals.setRoundingMode(RoundingMode.DOWN);
    }

    @Deprecated("")
    fun roundQuota(quota: Double): String {
        return if (quota == 0.0) "0" else twoDecimals.format(quota)
        //        if (quota <= 9.99) {//dve decimale
        //        } else if (quota <= 99.9) {
//            return oneDecimal.format(quota);
//        }
//        return Long.toString(Math.round(quota));
    }

    fun compareInt(x: Int, y: Int): Int {
        return if (x < y) -1 else if (x == y) 0 else 1
    }

    fun compareDouble(x: Double, y: Double): Int {
        return BigDecimal.valueOf(x).compareTo(BigDecimal.valueOf(y))
    }

    //    TODO ovo nece raditi za ogromne brojeve, moze se prebaciti numberOfCombinations u long i razmotriti dodatne optimizacije
    //    ali je za sada ovo dovoljno dobro jer ionako se dalje prosledjuje ticketCruncheru
    fun calculateNumberOfCombinations(combinationGroups: List<TicketSystemCombinationGroup?>): Int {
        var numberOfCombinations = 0
        try {
            for (combinationGroup in combinationGroups) if (combinationGroup != null) {
                numberOfCombinations += calculateNumberOfCombinationsForGroup(
                    combinationGroup.fromHowMany,
                    combinationGroup.howMany
                )
                if (numberOfCombinations > 10000) {
                    return numberOfCombinations
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return numberOfCombinations
    }

    private fun calculateNumberOfCombinationsForGroup(fromHowMany: Int, howMany: Int): Int {
        var numberOfCombinations = 1
        for (i in 0 until howMany) {
            numberOfCombinations *= fromHowMany - i
            numberOfCombinations /= i + 1
        }
        return numberOfCombinations
    }

    @get:Deprecated("")
    val quotaDecimalFormat: DecimalFormat
        get() {
            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = '.'
            val df = DecimalFormat("#0.00")
            df.decimalFormatSymbols = symbols
            return df
        }

    fun compareLong(number1: Long, number2: Long): Int {
        val result = number1 - number2
        if (result > 0) {
            return 1
        } else if (result < 0) {
            return -1
        }
        return 0
    }
}



fun calcCombinationNumber(k: Int, n: Int): Int {
    var factorial: Long = 1
    val biggerFactor = if (n - k > k) n - k else k
    val smallerFactor = n - biggerFactor
    for (i in n downTo biggerFactor + 1) {
        factorial *= i.toLong()
    }
    for (i in smallerFactor downTo 2) {
        factorial /= i.toLong()
    }
    return factorial.toInt()
}

fun getNumberOfCombinations(numberOfBalls: Int, ballsToDraw: Long, subsystem: Int): Int {
    var n1 = numberOfBalls
    try {
        n1 = Math.min(numberOfBalls, ballsToDraw.toInt())
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return calcCombinationNumber(subsystem, n1)
}
fun getMaxPotentialPayment(
    ticketGame: Event,
    numberOfFixes: Int,
    numbers: List<Int?>,
    subsystems: List<Int>,
    payinNeto: Double, result: CalculationResult
): Double {
    val comboPayin: Double = payinNeto /
            calculateNumberOfCombinations(numbers, subsystems, numberOfFixes)
    val oddValues: List<OddValues> = ticketGame.oddValues
    var total = 0.0
    for (subsystem in subsystems) {
        val winningCombinations: Int = ticketGame.ballsToDraw?.toLong()?.let {
            getNumberOfCombinations(
                numbers.size - numberOfFixes,
                it,
                subsystem
            )
        }?:0
        val oddValue: Double = findLotoOddValueForBalls(subsystem + numberOfFixes, oddValues)
        total += oddValue * comboPayin * winningCombinations
    }
    result.win = total
    return total //bruto
}

fun findLotoOddValueForBalls(winBallsCount: Int, oddValues: List<OddValues?>): Double {
    for (ov in oddValues) {
        if (ov != null) {
            if (ov.ballNumber?.toLong()?.toInt() === winBallsCount) return ov.value?: 0.0
        }
    }
    return 0.0
}

fun calculateNumberOfCombinations(
    numbers: List<Int?>,
    subsystems: List<Int?>,
    inFix: Int
): Int {
    if (numbers.isEmpty()) {
        return 0
    }
    var total = 0
    val size = numbers.size - inFix
    for (subsystem in subsystems) {
        total += calcCombinationNumber(subsystem!!, size)
    }
    return total
}