package com.neurotech.stressapp.ui.useraccount

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.neurotech.domain.TimeFormat
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserAccountFragment : Fragment(R.layout.fragment_user) {
    @Inject
    lateinit var factory: UserAccountViewModelFactory

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    val viewModel by lazy { ViewModelProvider(this, factory)[UserAccountViewModel::class.java] }

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding get() = _binding!!

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as App).component.inject(this)
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
                fillUserInfo()
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }

        }
        return binding.root
    }

    private fun waitData() {
        with(binding) {
            waitingTextView.visibility = View.VISIBLE
            waitingTextView2.visibility = View.VISIBLE
            waitingTextView3.visibility = View.VISIBLE
            waitingTextView4.visibility = View.VISIBLE
            waitingTextView5.visibility = View.VISIBLE
            waitingTextView6.visibility = View.VISIBLE
            waitingTextView.startAnimation()
            waitingTextView2.startAnimation()
            waitingTextView3.startAnimation()
            waitingTextView4.startAnimation()
            waitingTextView5.startAnimation()
            waitingTextView6.startAnimation()
        }
    }

    private fun hidePendingData() {
        with(binding) {
            waitingTextView.visibility = View.GONE
            waitingTextView2.visibility = View.GONE
            waitingTextView3.visibility = View.GONE
            waitingTextView4.visibility = View.GONE
            waitingTextView5.visibility = View.GONE
            waitingTextView6.visibility = View.GONE
        }
    }

    private fun fillUserInfo() {
        if (firebaseAuth.currentUser != null) {
            binding.logOutTextView.visibility = View.VISIBLE
            binding.signInButton.visibility = View.GONE
            val user = firebaseAuth.currentUser!!
            binding.nameTextView.text = user.displayName
            Glide.with(this).load(user.photoUrl).into(binding.imageView)
        } else {
            binding.logOutTextView.visibility = View.GONE
            binding.signInButton.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.switchButton2.setText("День", "Месяц", "Год")
        waitData()
        fillUserInfo()

        binding.dateOfBirthEdit.setOnClickListener {
            val datePicker = DatePickerDialog(requireActivity())
            datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.setDateOfBirth(
                    Tempo.with(
                        year = year,
                        month = month + 1,
                        day = dayOfMonth
                    )
                )
            }
            datePicker.show()
        }

        binding.genderEdit.setOnClickListener {
            binding.maleCheckBox.visibility = View.VISIBLE
            binding.femaleCheckBox.visibility = View.VISIBLE
            binding.maleCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setGender(true)
                    binding.maleCheckBox.visibility = View.GONE
                    binding.femaleCheckBox.visibility = View.GONE
                }
            }
            binding.femaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setGender(false)
                    binding.maleCheckBox.visibility = View.GONE
                    binding.femaleCheckBox.visibility = View.GONE

                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            binding.switchButton2.state.collect {
                when (it) {
                    1 -> viewModel.setOneDayInterval()
                    2 -> viewModel.setOneMonthInterval()
                    3 -> viewModel.setOneYearInterval()
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.userNameTextView.text = it.name
            binding.genderEdit.visibility = if(it.gender != null){View.GONE}else{View.VISIBLE}
            binding.dateOfBirthEdit.visibility = if(it.dateOfBirth != null){View.GONE}else{View.VISIBLE}
            with(binding) {
                normalTonic.text = it.tonicAvg.toString()
                normalPeakCountInTenMinuteTextView.text = it.peakNormal.toString()
                normalPeakCountInDayTextView.text = it.peakInDayNormal.toString()

                val d1: Int = it.dateOfBirth?.toString("yyyyMMdd")?.toInt() ?: 0
                val d2: Int = Tempo.now.toString("yyyyMMdd").toInt()
                val age = (d2 - d1) / 10000
                ageTextView.text = age.toString() //it.dateOfBirth?.toString(TimeFormat.dateFormat)
                genderTextView.text = it.gender
            }
        }



        viewModel.userParameter.observe(viewLifecycleOwner) {
            with(binding) {
                avgTonic.text = it.tonic.toString()
                avgPeakCountInDayTextView.text = it.dayPhase.toString()
                avgPeakCountInTenMinuteTextView.text = it.tenMinutePhase.toString()
                hidePendingData()
            }
        }
        binding.signInButton.setOnClickListener {
            authWithGoogle()
        }
        binding.logOutTextView.setOnClickListener {
            firebaseAuth.signOut()
            fillUserInfo()
        }
    }

    private fun authWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")
                } else {
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }

}
