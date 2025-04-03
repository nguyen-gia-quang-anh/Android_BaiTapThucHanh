package com.example.th4_b1sharedpreferences

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.th4_b1sharedpreferences.ui.theme.TH4_B1SharedPreferencesTheme

class MainActivity : ComponentActivity() {
    private lateinit var edtName : EditText
    private lateinit var edtPwd : EditText
    private lateinit var btnShow : Button
    private lateinit var tvName : TextView
    private lateinit var tvPwd : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_main)
        edtName = findViewById(R.id.edt_name)
        edtPwd = findViewById(R.id.edt_pwd)
        btnShow = findViewById(R.id.btn_show)
        tvName = findViewById(R.id.tv_name)
        tvPwd = findViewById(R.id.tv_pwd)
        showData(btnShow)
    }

    public fun saveData(v : View){
        val name = edtName.text.toString()
        val pwd = edtPwd.text.toString()
        val preferenceHelper = PreferenceHelper(this)
        preferenceHelper.saveUser(name, pwd)
    }

    public fun deleteData(v : View){
        val preferenceHelper = PreferenceHelper(this)
        preferenceHelper.clearData()
    }

    public fun showData(v : View){
        val preferenceHelper = PreferenceHelper(this)
        val name = preferenceHelper.getName()
        val pwd = preferenceHelper.getPwd()
        tvName.text = "Username: $name"
        tvPwd.text = "Password: $pwd"
    }
}