package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.StringNetwork
import com.example.myapplication.model.LoginBody
import com.example.myapplication.model.State
import com.example.myapplication.model.StringContent
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.b3nedikt.restring.Restring
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val network: StringNetwork
) : ViewModel() {
    private val _state = MutableStateFlow(State<List<StringContent>>())
    val state: StateFlow<State<List<StringContent>>> get() = _state

    fun fetchStrings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.emit(_state.value.copy(isLoading = true))
                val token =
                    network.doLogin(LoginBody("vidio-consumen@maildrop.cc", "5b%NLcA&kjzTjx"))
                val response = network.fetch(token.token, 0, 1000, "(platform,eq,mobile)").also {
                    val ids = buildMap {
                        it.list.forEach { cont ->
                            this[cont.key] = cont.langId
                        }
                    }
                    val en = buildMap {
                        it.list.forEach { cont ->
                            this[cont.key] = cont.langEn
                        }
                    }
                    val th = buildMap {
                        it.list.forEach { cont ->
                            this[cont.key] = cont.langTh
                        }
                    }
                    val vi = buildMap {
                        it.list.forEach { cont ->
                            this[cont.key] = cont.langVi
                        }
                    }
                    val my = buildMap {
                        it.list.forEach { cont ->
                            this[cont.key] = cont.langMy
                        }
                    }
                    Restring.putStrings(
                        Locale("id", "ID"),
                        ids
                    )
                    Restring.putStrings(
                        Locale.US,
                        en
                    )
                    Restring.putStrings(
                        Locale("th", "TH"),
                        th
                    )
                    Restring.putStrings(
                        Locale("vi", "VI"),
                        vi
                    )
                    Restring.putStrings(
                        Locale("my", "MY"),
                        my
                    )
                }
                _state.emit(_state.value.copy(isLoading = false, data = response.list))
            } catch (e: Exception) {
                _state.emit(_state.value.copy(isLoading = false, isError = true, throwable = e))
            }
        }
    }
}