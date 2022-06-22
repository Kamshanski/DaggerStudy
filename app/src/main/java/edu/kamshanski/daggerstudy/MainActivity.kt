package edu.kamshanski.daggerstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import edu.kamshanski.daggerstudy.databinding.ActivityMainBinding
import edu.kamshanski.daggerstudy.di.ActivityComponent
import edu.kamshanski.daggerstudy.ui.Screens
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseActivity: AppCompatActivity() {

    var activityComponent: ActivityComponent by Delegates.notNull()
}

class MainActivity : BaseActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator by lazy { AppNavigator(this, R.id.fragmentContainer) }

    private var binding: ActivityMainBinding by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComp = app.appComponent
        activityComponent = appComp.activityComponent().create()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabric.setOnClickListener { router.navigateTo(Screens.Production()) }
        binding.market.setOnClickListener { router.navigateTo(Screens.Market()) }

        router.navigateTo(Screens.Market())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}