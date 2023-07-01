package com.example.cocinafeliz.notificacion

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.cocinafeliz.R
import com.example.cocinafeliz.constants.Constants.channelId

class NotificacionProgramada: BroadcastReceiver() {
    companion object{
        const val  NOTIFICACION_ID = 5
    }
    override fun onReceive(context: Context, intent: Intent?) {
        crearNotificacion(context)
    }

    private fun crearNotificacion(context: Context) {
        val notificacion = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.olla)
            .setContentTitle("Notificacion programada")
            .setContentText("Ejemplo de Notificacion programada")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Saludos. Esta es una prueba de notificacion programada. Aparecera despues de un tiempo, incluso si la app esta cerrada. Puedes usar las otras caracteristicas de una notificacion que ya has usado. Da clic para abrir la app. Gracias hasta pronto")

            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager
        manager.notify(NOTIFICACION_ID, notificacion)
    }
}