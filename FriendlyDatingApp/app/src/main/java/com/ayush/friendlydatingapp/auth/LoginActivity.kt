package com.ayush.friendlydatingapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ayush.friendlydatingapp.MainActivity
import com.ayush.friendlydatingapp.R
import com.ayush.friendlydatingapp.databinding.ActivityLoginBinding
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
   // private lateinit var dialog: AlertDialog


    val auth = FirebaseAuth.getInstance()
    private var  verificationId:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
       // dialog = AlertDialog.Builder(this).setView(R.layout.pro).create()

        setContentView(binding.root)

        binding.sendOtp.setOnClickListener {
            if (binding.userNumber.text!!.isEmpty()){
                binding.userNumber.error = "Please enter your mobile number"
            }
            else{
                sendOtp(binding.userNumber.text.toString())
            }

        }
        binding.verifyOtp.setOnClickListener {
            if (binding.userOtP.text!!.isEmpty()){
                binding.userOtP.error = "Please enter the OTP"
            }else{
                verifyOtp(binding.userOtP.text.toString())
            }


        }



    }

    private fun verifyOtp(otp: String) {

      //  dialog.show()
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)

    }

    private fun sendOtp(number: String) {
      //  dialog.show()

        val  callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {


                signInWithPhoneAuthCredential(credential)

            }

            override fun onVerificationFailed(e: FirebaseException) {
                //...

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@LoginActivity.verificationId = verificationId

              //  dialog.dismiss()

                binding.numberLayout.visibility = View.GONE
                binding.otplayout.visibility = View.VISIBLE



            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    checkUserExists(binding.userNumber.text.toString())
                    //                   startActivity(Intent(this,MainActivity::class.java))
                    //                 finish()

                } else {
                  //  dialog.dismiss()
                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun checkUserExists(number: String) {

        FirebaseDatabase.getInstance().getReference("users").child("+91$number")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()){
                     //   dialog.dismiss()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //dialog.dismiss()
                    Toast.makeText(this@LoginActivity,error.message,Toast.LENGTH_SHORT).show()
                }

            })

    }
//    private  lateinit var binding :ActivityLoginBinding
//    val auth =FirebaseAuth.getInstance()
//    private var verificattionId : String?=null
//    private  lateinit var  dialog :AlertDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding= ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//         dialog = AlertDialog.Builder(this).setView(R.layout.loading_layout).setCancelable(false).create()
//         binding.SendOtp.setOnClickListener{
//
//             if(binding.userNumber.text.toString().isEmpty())
//             {
//                 binding.userNumber.error="Please enter your number "
//             }
//             else
//             {
//                 sendOtp(binding.userNumber.text.toString())
//             }
//         }
//        binding.VerifyOTP.setOnClickListener{
//            if(binding.userOtP.text.toString().isEmpty())
//            {
//                binding.userOtP.error="Please enter your OTP "
//            }
//            else
//            {
//                verifyOtp(binding.userOtP.text.toString())
//            }
//        }
//    }
//
//    private fun verifyOtp(otp: String){
//        dialog.show()
//        val credential = PhoneAuthProvider.getCredential(verificattionId!!, otp)
//
//        signInWithPhoneAuthCredential(credential)
//
//    }
//
//    private fun sendOtp(number: String) {
//     //binding.SendOtp.showLoadingButton()
//        dialog.show()
//       val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//
//               // binding.SendOtp.showNormalButton()
//                signInWithPhoneAuthCredential(credential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                this@LoginActivity.verificattionId = verificattionId
//                dialog.dismiss()
//                //binding.SendOtp.showNormalButton()
//                binding.numberLayout.visibility = GONE
//                binding.otplayout.visibility = VISIBLE
//            }
//        }
//
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber("+91$number")       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//               // binding.SendOtp.showNormalButton()
//                if (task.isSuccessful) {
//
//                    checkUserExist(binding.userNumber.text.toString())
//
//                } else {
//                    dialog.dismiss()
//                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_LONG).show()
//                }
//            }
//    }
//
//    private fun checkUserExist(number: String) {
//        FirebaseDatabase.getInstance().getReference("users").child(number).addValueEventListener(object : ValueEventListener{
//
//            override fun onCancelled(p0: DatabaseError) {
//                dialog.dismiss()
//                Toast.makeText(this@LoginActivity, p0.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if(p0.exists()){
//                    dialog.dismiss()
//                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
//                    finish()
//                }
//                else {
//                    startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
//                    finish()
//                }
//            }
//
//        })
//
//    }
}