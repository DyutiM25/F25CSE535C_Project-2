package com.example.tictaktoeproject2

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictaktoeproject2.ui.theme.TicTakToeProject2Theme
import org.json.JSONObject
import java.io.IOException
import java.util.UUID

private const val TAG = "MY_TICTAKTOE_APP"
//randomly generated static UUID
private val MY_UUID: UUID = UUID.fromString("02119a91-0c55-4238-a552-0756e76a6bfc")

class MainActivity : ComponentActivity() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var connectedThread: BluetoothService.ConnectedThread? = null


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter

        val device: BluetoothDevice? = bluetoothAdapter?.bondedDevices?.firstOrNull()
        if(device != null) {
            Thread {
                try {
                    val socket: BluetoothSocket =
                        device.createRfcommSocketToServiceRecord(MY_UUID)

                    socket.connect()

                    // Once connected, start the communication thread
                    val service = BluetoothService(handler)
                    connectedThread = service.ConnectedThread(socket)
                    connectedThread?.start()

                } catch (e: IOException) {
                    Log.e(TAG, "Connection failed", e)
                }
            }.start()
        }
        //example send code tied to a button
/*
        sendButton.setOnClickListener {
            val text = inputMessage.text.toString()

            // Make JSON Object
            val json = JSONObject().apply {
                put("data", text)
            }

            val jsonString = json.toString()
            if (connectedThread != null) {
                connectedThread?.write(jsonString.toByteArray(Charsets.UTF_8))
                Log.d(TAG, "Sent $jsonString")
            } else {
                Log.w(TAG, "Not connected")
            }
        }
 */

        enableEdgeToEdge()
        setContent {
            TicTakToeProject2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    val readMessage = String(readBuf, 0, msg.arg1)
                    Log.d(TAG, "Received: $readMessage")

                    // Handle received data
                    // JSON.decode(readMessage)
                    // check turn flag
                    // update board
                }

                MESSAGE_WRITE -> {
                    Log.d(TAG, "Message sent to device.")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTakToeProject2Theme {
        Greeting("Android")
    }
}