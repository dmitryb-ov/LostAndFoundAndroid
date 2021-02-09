package com.example.lostandfound.presentation.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostandfound.BuildConfig
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.databinding.ProfileFragmentBinding
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.profile_fragment.view.*
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: ProfileFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusProfileComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.title = "Профиль"
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner



        binding.profileViewModel = profileViewModel
        signOut(binding.root)
        editProfile(binding.root)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        load()
    }


    fun load() {
        profileViewModel.getCurrentUser()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseDatabase.getInstance().reference.child("users")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val user =
                        snapshot.child(currentUser?.uid.toString())
                            .getValue(UserEntity::class.java)!!
                    bindUserData(user)
                } catch (e: KotlinNullPointerException) {
                    binding.root.tv_pf_name.text = "Пусто"
                    binding.root.tv_pf_phone_number.text = "Пусто"
                    binding.root.tv_pf_city.text = "Пусто"
                    binding.root.tv_pf_email.text = "Пусто"
                }
            }
        })
    }

    fun bindUserData(user: UserEntity) {
        binding.root.tv_pf_name.text = user.name
        binding.root.tv_pf_phone_number.text = user.phone
        binding.root.tv_pf_city.text = user.city
        binding.root.tv_pf_email.text = user.email
    }

    private val mSettings: SharedPreferences by lazy {
        this.activity!!.getSharedPreferences(BuildConfig.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun editProfile(view: View) {
        view.floatingActionButton.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java).apply {
                putExtra("email", view.tv_pf_email.text.toString())
                putExtra("name", view.tv_pf_name.text.toString())
                putExtra("city", view.tv_pf_city.text.toString())
                putExtra("phone", view.tv_pf_phone_number.text.toString())
//                putExtra("reg_date", binding.profileViewModel?.user?.value?.registrationDate)
            })
        }
    }

    fun signOut(view: View) {
        view.btn_logout.setOnClickListener {
            auth.signOut()
            activity?.let {
                val intent = Intent(it, FirebaseLoginActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }

    companion object {
        val auth = FirebaseAuth.getInstance()
    }
}