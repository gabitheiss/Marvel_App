package com.example.marvel_app_test.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app_test.model.Characters
import com.example.marvel_app_test.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MarvelRepository) : ViewModel() {

    private val _character = MutableLiveData<List<Characters?>>()
    val character: LiveData<List<Characters?>> = _character

    private val _characterList = MutableLiveData<List<Characters?>>()
    val characterList: LiveData<List<Characters?>> = _characterList

    private val _changePage = MutableLiveData(0)
    val changePage : LiveData<Int> = _changePage

    fun loadListCharacterToView(offset : Int) {
        viewModelScope.launch {
            val returnList = repository.checkConnectForGetCharactersListFromDatabaseOrApi(offset)
            returnList.let {
                _characterList.value = it
            }
        }
    }

    fun loadFeaturedCharacterToView() {
        viewModelScope.launch {
            val returnList = repository.checkConnectForGetFeaturedCaracterFromDatabaseOrApi()
            returnList.let {
                _character.value = it
            }
        }
    }

    fun afterQueryTextChanged() {
        _characterList.value = repository.fetchAllFromDatabase()
    }

    fun onQueryTextChanged(query:String){
        _characterList.value = repository.searchCharacter(query)
    }

    fun nextPage() {
        _changePage.value = changePage.value!! + 20
    }

}
