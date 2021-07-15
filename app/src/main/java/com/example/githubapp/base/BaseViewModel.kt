package com.example.githubapp.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.model.util.LiveDataEvent
import com.example.githubapp.utils.postEvent

abstract class BaseViewModel<UI_MODEL: BaseUiModel>(private val app: Application) : AndroidViewModel(app) {
    protected abstract val uiModelInternal: UI_MODEL

    private val _uiModel: MutableLiveData<LiveDataEvent<UI_MODEL>> = MutableLiveData()
    val uiModel: LiveData<LiveDataEvent<UI_MODEL>> get() = _uiModel

    private val _navigationData: MutableLiveData<LiveDataEvent<Intent>> = MutableLiveData()
    val navigationData: LiveData<LiveDataEvent<Intent>> get() = _navigationData



    protected val context : Context by lazy { app }

    @Synchronized
    protected fun updateUi(action: UI_MODEL.() -> Unit){
        action.invoke(uiModelInternal)
        _uiModel.postEvent(uiModelInternal)
    }

    protected fun<T: Activity> navigateTo(screen: Class<T>, finishOnNavigate: Boolean = false, extras: Intent? = null){
        val navigationIntent = Intent(context, screen)
        extras?.also { extrasIntent ->
            navigationIntent.putExtras(extrasIntent)
        }
        if(finishOnNavigate){
            navigationIntent.setFinishOnNavigate()
        }
        _navigationData.postEvent(navigationIntent)
    }

    protected fun navigateTo(intent: Intent, finishOnNavigate: Boolean = false){
        if(finishOnNavigate){
            intent.setFinishOnNavigate()
        }
        _navigationData.postEvent(intent)
    }

    protected fun Intent.setFinishOnNavigate() : Intent{
        return putExtra(BaseActivity.EXTRA_FINISH_ON_NAVIGATE, true)
    }
}