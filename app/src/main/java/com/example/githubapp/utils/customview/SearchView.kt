package com.example.githubapp.utils.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.githubapp.R
import java.util.*

class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val input: EditText
    private val imageButton: ImageView

    private var timer: Timer = Timer()

    private var listener: SearchListener? = null

    init {
        inflate(context, R.layout.view_search, this)
        input = findViewById(R.id.view_search_input)
        imageButton = findViewById(R.id.view_search_image_button)
        configure()
    }

    private fun configure(){
        input.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                timer.cancel()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                timer.cancel()
                timer = Timer()
                configureImage(s.isEmpty())
                if(s.isNotBlank()) {
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            listener?.onSearchTriggered(s.toString())
                        }
                    }, 800)
                }
            }
        })
    }

    private fun configureImage(isEmpty: Boolean){
        if(isEmpty) {
            imageButton.setImageResource(R.drawable.ic_search)
            imageButton.setOnClickListener(null)
        } else {
            imageButton.setImageResource(R.drawable.ic_clear)
            imageButton.setOnClickListener {
                input.setText("")
            }
        }
    }

    fun setSearchListener(listener: SearchListener?){
        this.listener = listener
    }

    interface SearchListener {
        fun onSearchTriggered(keyword: String)
    }
}