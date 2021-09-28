package com.example.myapplication

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.cat.Cat
import com.example.myapplication.models.duck.Duck
import com.example.myapplication.ui.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var catResponse : MutableLiveData<NetworkResult<Cat>> = MutableLiveData()
    var duckResponse : MutableLiveData<NetworkResult<Duck>> = MutableLiveData()

    fun getCat(queries: Map<String, String>) = viewModelScope.launch {
        getCatSafeCall(queries)
    }

    fun getDuck(queries: Map<String, String>) = viewModelScope.launch {
        getDuckSafeCall(queries)
    }

    private suspend fun getDuckSafeCall(queries: Map<String, String>) {
        duckResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getDuck(queries)
                duckResponse.value = handleDuckResponse(response)
            }catch (e: Exception) {
                duckResponse.value = NetworkResult.Error("Duck Not Found")

            }

        }else {
            duckResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleDuckResponse(response: Response<Duck>): NetworkResult<Duck>? {
        when {
            response.message().toString().contains("timeout")-> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Not Found")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Duck Not Found.")
            }
            response.isSuccessful -> {
                val duck = response.body()
                return NetworkResult.Success(duck!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }

    }

    private suspend fun getCatSafeCall(queries: Map<String, String>) {
        catResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getCat(queries)
                catResponse.value = handleCatResponse(response)
            } catch (e: Exception) {
                catResponse.value = NetworkResult.Error("Cat Not Found")
            }
        } else {
            catResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleCatResponse(response: Response<Cat>): NetworkResult<Cat>? {
        when {
            response.message().toString().contains("timeout")-> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Not Found")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Cat Not Found.")
            }
            response.isSuccessful -> {
                val cat = response.body()
                return NetworkResult.Success(cat!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(

            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)->true

            else -> false
        }
    }
}