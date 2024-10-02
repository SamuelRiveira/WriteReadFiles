package dev.samu.writereadfiles

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.samu.writereadfiles.ui.theme.WriteReadFilesTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriteReadFilesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SaveTextToFile("ejemplo.txt")
                }
            }
        }
    }

    @Composable
    fun SaveTextToFile(nombreArchivo: String) {
        val datetime = LocalDateTime.now().toString()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { guardarTextoEnArchivo(datetime, nombreArchivo) }
            ) {
                Text("Guardar archivo")
            }
            Button(onClick = {leerArchivo()}) {
                Text(
                    text = "Leer archivo"
                )
            }
        }
    }

    private fun guardarTextoEnArchivo(texto: String, nombreArchivo: String) {
        val estadoAlmacenamiento = Environment.getExternalStorageState()

        if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED) {
            val directorio = getFilesDir()
            val archivo = File(directorio, nombreArchivo)

            try {
                val flujoSalida = FileOutputStream(archivo, true)
                val writer = OutputStreamWriter(flujoSalida)
                writer.append(texto)
                writer.close()

                Toast.makeText(this, "Texto añadido en $directorio $nombreArchivo", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se pudo acceder al almacenamiento externo", Toast.LENGTH_SHORT).show()
        }
    }
    private fun leerArchivo(){
        val estadoAlmacenamiento = Environment.getExternalStorageState()
        val nombreArchivo = "ejemplo.txt"
        val texto = "Lorem epsilon"

        if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED) {

            val directorio = getFilesDir()
            val archivo = File(directorio, nombreArchivo)

            try {
                val flujoEntrada = FileInputStream(archivo)
                val textito = flujoEntrada.bufferedReader().use { it.readText() }

                Log.i("dam2", "Contenido del archivo:\n$textito")
                flujoEntrada.close()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("dam2", "Error al leer el archivo")
            }
        } else {
            Toast.makeText(this, "No se pudo acceder al almacenamiento externo", Toast.LENGTH_SHORT).show()
        }
    }
}