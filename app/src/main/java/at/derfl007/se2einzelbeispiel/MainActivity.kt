package at.derfl007.se2einzelbeispiel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import at.derfl007.se2einzelbeispiel.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import java.util.concurrent.Executors
import java.util.stream.IntStream
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onSend(view: View) {
        Executors.newSingleThreadExecutor().execute {
            val socket = Socket("se2-isys.aau.at", 53212)
            val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val dataOut = DataOutputStream(socket.getOutputStream())
            dataOut.writeBytes(binding.editTextMatrikelnummer.text.toString() + "\n")
            binding.textViewAnswer.text = bufferedReader.readLine()
        }
    }

    fun calculate(view: View) {
        val matNr = binding.editTextMatrikelnummer.text.toString().toCharArray()
        matNr.sort()
        var res = ""
        matNr.forEach {
            val int = Integer.parseInt(it.toString())
            if (!isPrime(int)) {
                res += int.toString()
            }
        }
        binding.textViewAnswer2.text = res
    }

    private fun isPrime(number: Int): Boolean {
        return number > 1 && IntStream.rangeClosed(2, sqrt(number.toDouble()).toInt()).noneMatch { n -> (number % n == 0) }
    }
}