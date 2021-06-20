package com.yuchiaoc.githubapidemo.ui

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yuchiaoc.githubapidemo.ui.adapter.UserListAdapter
import com.yuchiaoc.githubapidemo.utils.CommonTextWatcher
import com.yuchiaoc.githubapidemo.databinding.MainFragmentBinding
import com.yuchiaoc.githubapidemo.viewmodel.MainViewModel

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: UserListAdapter = UserListAdapter()
    private lateinit var editText: EditText
    private lateinit var list: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        editText = binding.inputText
        list = binding.list

        list.adapter = adapter
        binding.search.setOnClickListener {
            hideSoftKeyboard(editText)
            findUserList()
        }
        editText.addTextChangedListener(CommonTextWatcher(editText))
        editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    binding.search.performClick()
                    true
                } else {
                    false
                }
            }
        return binding.main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    private fun hideSoftKeyboard(view: View) {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun findUserList() {
        editText.text.trim().toString().let {
            if (it.isNotEmpty()) {
                viewModel.search(it)
                viewModel.getUsers().observe(this, Observer { items ->
                    if (items.size != 0) {
                        list.visibility = View.VISIBLE
                        binding.noResult.visibility = View.GONE
                        list.scrollToPosition(0)
                        adapter.submitList(items)
                    } else {
                        list.visibility = View.GONE
                        binding.noResult.visibility = View.VISIBLE
                        adapter.submitList(null)
                        editText.setText("")
                    }

                })
            } else {
                Snackbar.make(binding.main, "Please Enter User Name.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}