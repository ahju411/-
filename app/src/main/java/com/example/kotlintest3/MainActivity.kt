package com.example.kotlintest3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kotlintest3.databinding.ActivityMainBinding
import com.example.kotlintest3.retrofit.RetrofitManager
import com.example.kotlintest3.utils.Constants.TAG
import com.example.kotlintest3.utils.RESPONSE_STATE
import com.example.kotlintest3.utils.SEARCH_TYPE
import com.example.kotlintest3.utils.onMyTextChanged

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentSearchType:SEARCH_TYPE = SEARCH_TYPE.PHOTO


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - onCreate() called")

        //라디오 그룹 가져오기
        binding.searchRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            //switch문
            when(checkedId){
                R.id.photo_search_radio ->{
                    Log.d(TAG, "사진검색 버튼 클릭")

                    binding.searchTextLayout.hint = "사진검색"
                    binding.searchTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                    if(this.currentSearchType == SEARCH_TYPE.PHOTO){
                        binding.searchEditText.setText(null)
                    }

                }
                R.id.user_search_radio ->{
                    Log.d(TAG, "사용자검색 버튼 클릭")
                    binding.searchTextLayout.hint = "사용자검색"
                    binding.searchTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.USER
                    if(this.currentSearchType == SEARCH_TYPE.USER){
                        binding.searchEditText.setText(null)
                    }
                }
            }
        }

        //텍스트가 변경이 되었을 때
        binding.searchEditText.onMyTextChanged {
            //입력된 글자가 하나라도 있을 경우
            if(it.toString().count() > 0){
                // 검색 버튼을 보여주기
                binding.includeBtn.frameSearchBtn.visibility = View.VISIBLE

                //헬퍼 텍스트 지우기
                binding.searchTextLayout.helperText = " "

                // 스크롤뷰 올리기
                binding.mainScrollview.scrollTo(0,200)
            } else{
                binding.includeBtn.frameSearchBtn.visibility = View.INVISIBLE
                binding.searchTextLayout.helperText = "검색어를 입력해주세요"
            }
            if(it.toString().count() == 12){
                Toast.makeText(this,"검색어를 12자 까지만 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }


        //검색버튼 클릭시
        binding.includeBtn.btnSearch.setOnClickListener {

            //사진 검색 api 호출
            RetrofitManager.instance.searchPhotos(searchTerm = binding.searchEditText.toString(), completion = {
                responseState,responseBody ->

                when(responseState){
                    RESPONSE_STATE.OKAY->{
                        Log.d(TAG, "api 호출 성공 : $responseBody")
                    }
                    RESPONSE_STATE.FAIL->{
                        Toast.makeText(this, "api호출 에러입니다.",Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호출 실패 : $responseBody")
                    }
                }
            })

            this.handlerSearchBtnUi()
        }


    } //onCreate

    private fun handlerSearchBtnUi(){
        binding.includeBtn.btnProgress.visibility = View.VISIBLE
        binding.includeBtn.btnSearch.visibility = View.INVISIBLE
        binding.includeBtn.btnSearch.text = ""
        Handler(Looper.getMainLooper()).postDelayed({
            binding.includeBtn.btnProgress.visibility = View.INVISIBLE
            binding.includeBtn.btnSearch.visibility = View.VISIBLE
            binding.includeBtn.btnSearch.text ="검색"
        },1500)
    }

    
    
    
    
}