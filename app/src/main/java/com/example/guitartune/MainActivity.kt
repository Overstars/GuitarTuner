package com.example.guitartune

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.guitartune.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用Data Binding绑定布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 设置binding.root为当前活动的内容视图
        setContentView(binding.root)

        // 获取BottomNavigationView导航视图
        val navView: BottomNavigationView = binding.navView

        // 获取NavController导航控制器
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // 将每个菜单ID作为一组ID传递，因为每个菜单都应该被视为顶级目的地。
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        // 使用NavController设置ActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)
        // 使用NavController设置BottomNavigationView
        navView.setupWithNavController(navController)
    }
}