package com.example.githubapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import com.example.githubapp.model.util.LiveDataEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseActivity<VM: BaseViewModel<UI_MODEL>, BINDING: ViewDataBinding, UI_MODEL: BaseUiModel> : AppCompatActivity() {
    protected lateinit var binding: BINDING
    protected val viewModel: VM by viewModel(clazz = getViewModelKClass())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel.uiModel.observeEvent { uiModel ->
            onUiUpdates(uiModel)
        }
        viewModel.navigationData.observeEvent { navigationIntent ->
            startActivity(navigationIntent)
            if(navigationIntent.getBooleanExtra(EXTRA_FINISH_ON_NAVIGATE, false)){
                finish()
            }
        }
    }

    protected open fun onUiUpdates(model: UI_MODEL){}

    protected abstract fun getLayoutId(): Int

    // extensions
    fun<T: Any> LiveData<LiveDataEvent<T>>.observeEvent(action: (T) -> Unit){
        observe(this@BaseActivity) { event ->
            event.get(action)
        }
    }


    // private utility methods
    @Suppress("UNCHECKED_CAST")
    private fun getViewModelKClass(): KClass<VM>{
        return ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.first() as Class<VM>).kotlin
    }

    companion object {
        const val EXTRA_FINISH_ON_NAVIGATE = "finish_on_navigate"
    }
}