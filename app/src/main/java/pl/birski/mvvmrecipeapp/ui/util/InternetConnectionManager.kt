package pl.birski.mvvmrecipeapp.ui.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionManager
@Inject constructor(application: Application) {

    private val connectionLiveData = ConnectionLiveData(application)

    val isNetworkAvailable = mutableStateOf(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner) { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        }
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}
