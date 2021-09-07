package com.example.movieapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityAuthBinding
import com.example.movieapp.helpers.SharePreferencesHelper
import com.example.movieapp.helpers.viewBinding
import com.example.movieapp.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by viewBinding(ActivityAuthBinding::inflate)
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val sharePreferencesHelper: SharePreferencesHelper by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initGoogleAuthentication()

        binding.skip.setOnClickListener {
            sharePreferencesHelper.putUserId("1")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initGoogleAuthentication() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleAuthBtn.setOnClickListener {
            mGoogleSignInClient.signOut()
            singInGoogle()
        }
    }

    private fun singInGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            loadUserProfileGoogle(task)
        }

    private fun loadUserProfileGoogle(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val image_url = account.photoUrl?.toString()
                ?: "https://iupac.org/wp-content/uploads/2018/05/default-avatar.png"
            sharePreferencesHelper.putUserId(account.id)
            startActivity(Intent(this, MainActivity::class.java).putExtra("image_url", image_url))
        } catch (e: ApiException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}