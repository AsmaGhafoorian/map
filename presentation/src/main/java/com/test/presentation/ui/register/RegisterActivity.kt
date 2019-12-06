package com.test.presentation.ui.register

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.test.presentation.*
import com.test.presentation.model.RegisterBodyModel
import com.test.presentation.navigation.registerNavigator
import com.test.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject
import android.text.Editable



/**
 * Created by asma.
 */
class RegisterActivity :BaseActivity(){

    @Inject
    lateinit var navigator: registerNavigator

    private var REQUEST_LOCATION_CODE = 101


    var registerData : RegisterBodyModel?= null
    var gender : String = "Female"

    companion object {
        lateinit var activity : Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        activity = this
        getAppInjector().inject(this)
        init()
    }

    fun init(){



        radiogroup.check(R.id.femaleRBtn)
        registerToolbar()
        backBtn.visible()
        registerTxt.visible()

        nextStage.setOnClickListener {
            when(checkInputs()){
                true ->{
                    registerData = RegisterBodyModel(1, addressTxt.text.toString(), null, null, mobileTxt.text.toString(), phoneTxt.text.toString(), nameTxt.text.toString(), familyTxt.text.toString(), gender)
                    navigator.navigateToMapActivity(this, registerData)
                }
                false ->{}
            }
        }

        radiogroup.setOnCheckedChangeListener { radioGroup, id ->
            when(id){
                R.id.maleRBtn -> {
                    gender = "Male"
                }
                R.id.femaleRBtn -> {
                    gender = "Female"
                }
            }

        }

        backBtn.setOnClickListener {
            finish()
        }

        handleEditTexts(nameTxt, null)
        handleEditTexts(familyTxt, null)
        handleEditTexts(mobileTxt, null)
        handleEditTexts(phoneTxt, null)
        handleEditTexts(addressTxt, null)
    }



    fun checkInputs() : Boolean{
        if(nameTxt.text.isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_LONG).show()
                return false
        }
        if(familyTxt.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.enter_family), Toast.LENGTH_LONG).show()
            return false
        }

        if(mobileTxt.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.enter_mobile), Toast.LENGTH_LONG).show()
            return false
        }else{
            if (mobileTxt.text.length != 11 || !mobileTxt.text.startsWith("09")) {
                Toast.makeText(this, getString(R.string.wrong_mobile), Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(phoneTxt.text.isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
            return false
        }
        if(addressTxt.text.isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.enter_address), Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }


    fun handleEditTexts(editText: EditText, parent: View?){
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dotted, 0, 0, 0)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {

                    editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0)
                } else {
                    editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dotted, 0, 0, 0)
                }
            }
        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}