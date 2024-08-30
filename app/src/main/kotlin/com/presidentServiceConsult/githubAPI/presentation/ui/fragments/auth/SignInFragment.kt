package com.presidentServiceConsult.githubAPI.presentation.ui.fragments.auth

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.data.local.UserPreferences
import com.presidentServiceConsult.githubAPI.databinding.FragmentSignInBinding
import com.presidentServiceConsult.githubAPI.presentation.foundation.withoutViewModel.Fragment
import org.koin.android.ext.android.inject

class SignInFragment : Fragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    override val binding by viewBinding(FragmentSignInBinding::bind)
    private val userPreferences by inject<UserPreferences>()
    private val githubAccessTokenPattern = Regex("^ghp_[a-zA-Z0-9]{36}$")

    override fun assembleViews() {
        saveGitHubAccessTokenAndProceed()
    }

    private fun saveGitHubAccessTokenAndProceed() = with(binding) {
        etGithubAccessToken.addTextChangedListener {
            tilGithubAccessToken.isErrorEnabled = false
            btnAccess.isEnabled = true
            if (btnAccess.backgroundTintList == ColorStateList.valueOf(Color.GRAY))
                btnAccess.backgroundTintList = ColorStateList.valueOf(Color.MAGENTA)
            tilGithubAccessToken.error = null
        }

        binding.etGithubAccessToken.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                authenticate()
            }
            false
        }

        btnAccess.setOnClickListener {
            authenticate()
        }

        btnSkip.navigate {
            navigate(SignInFragmentDirections.toMainFragment())
        }
    }

    private fun authenticate() = with(binding) {
        val fullAccessToken =
            tilGithubAccessToken.prefixText.toString() + etGithubAccessToken.text.toString()
        if (githubAccessTokenPattern.matches(fullAccessToken)) {
            userPreferences.githubAPIToken = fullAccessToken
            findNavController().navigate(SignInFragmentDirections.toMainFragment())
        } else {
            btnAccess.isEnabled = false
            btnAccess.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
            tilGithubAccessToken.isErrorEnabled = true
            tilGithubAccessToken.error = "Invalid GitHub Access Token"
        }
    }
}