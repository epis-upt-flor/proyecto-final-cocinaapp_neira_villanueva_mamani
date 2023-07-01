package com.example.cocinafeliz.notificacion

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cocinafeliz.notificacion.NotificacionProgramada.Companion.NOTIFICACION_ID
import java.util.Calendar

@Composable
fun Notificaciones(){
    val context = LocalContext.current
    val idCanal = "CanalCocina"
    val isNotificacion = 0

    LaunchedEffect(key1 = Unit){
        crearCanalNotificacion(idCanal, context)
    }

    val modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Text(
            text ="ALARMAS",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )
                Button(onClick = {
                         notificacionProgramada(context)
        },
            modifier = modifier) {
            Text(text = "Notificacion Programada")
        }
    }
}

fun notificacionProgramada(context: Context) {
    val intent = Intent(context, NotificacionProgramada::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    var alarmaManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmaManager.setExact(
        AlarmManager.RTC_WAKEUP,
        Calendar.getInstance().timeInMillis + 1000,
        pendingIntent
    )
    Log.d("ALARMAMANDADA", "ALARMA SONANDO")

}

fun crearCanalNotificacion(
    idCanal: String,
    context: Context
){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val nombre = "CanalCocina"
        val descripcion = "Canal de Notificaciones Cocina"
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        val canal = NotificationChannel(idCanal, nombre, importancia)
            .apply {
                description = descripcion
            }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationManager.createNotificationChannel(canal)

    }

}

