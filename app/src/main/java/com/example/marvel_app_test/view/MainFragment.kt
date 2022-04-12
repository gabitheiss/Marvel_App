package com.example.marvel_app_test.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel_app_test.R
import com.example.marvel_app_test.adapter.AdapterCharacterList
import com.example.marvel_app_test.adapter.AdapterFeaturedCharacter
import com.example.marvel_app_test.databinding.MainFragmentBinding
import com.example.marvel_app_test.model.Characters
import com.example.marvel_app_test.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app_test.utils.Navigation
import com.example.marvel_app_test.utils.isConnected
import com.example.marvel_app_test.MainActivity

@AndroidEntryPoint
class MainFragment(private val navigation : Navigation) : Fragment(R.layout.main_fragment) {

    private var adapterFeaturedCharacter = AdapterFeaturedCharacter{ characters ->
        navigation.navigateTo(DetailsFragment(characters), addToBackstack = true, this)
    }

    private var adapterCharacterList = AdapterCharacterList{ characters ->
        navigation.navigateTo(DetailsFragment(characters), addToBackstack = true, this)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private var listRefreshToSearch : Boolean = false

    private val featuredCharacterObserver = Observer<List<Characters?>> {
        adapterFeaturedCharacter.refresh(it)
    }

    private val characterListObserver = Observer<List<Characters?>> {
        adapterCharacterList.refreshList(it, listRefreshToSearch)
    }

    private val observerChangePage = Observer<Int> {
        if (requireContext().isConnected())
            viewModel.loadListCharacterToView(offset = it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.bind(view)

        setupFeaturedCharacters()
        setupCharactersList(0)
        searchCharacter()
        starObserverOffset()
        setScrollListener()
    }

    private fun starObserverOffset() {
        viewModel.changePage.observe(viewLifecycleOwner, observerChangePage)
    }

    private fun setupFeaturedCharacters() {
        binding.includedLayoutFeaturedCharacters.rvMainFeaturedCharacter.adapter = adapterFeaturedCharacter
        binding.includedLayoutFeaturedCharacters.rvMainFeaturedCharacter.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.character.observe(viewLifecycleOwner, featuredCharacterObserver)

        viewModel.loadFeaturedCharacterToView()
    }

    private fun setupCharactersList(offset : Int) {
        binding.rvMainCharacterList.adapter = adapterCharacterList
        binding.rvMainCharacterList.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.characterList.observe(viewLifecycleOwner, characterListObserver)

        listRefreshToSearch = true
        viewModel.loadListCharacterToView(offset)
    }

    private fun searchCharacter() {

        binding.includedLayoutFeaturedCharacters.etSearchCharacters.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.length > 2) {
                        listRefreshToSearch = true
                        viewModel.onQueryTextChanged(it.toString())
                    }else if(it.isEmpty()){
                        listRefreshToSearch = true
                        viewModel.afterQueryTextChanged()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setScrollListener() = with(binding.rvMainCharacterList) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && requireContext().isConnected()
                ) {
                    if(binding.includedLayoutFeaturedCharacters.etSearchCharacters.text.length > 2){
                        viewModel.onQueryTextChanged(binding.includedLayoutFeaturedCharacters.etSearchCharacters.text.toString())
                    }else{
                        listRefreshToSearch = false
                        viewModel.nextPage()
                    }
                }
            }
        })
    }

}

