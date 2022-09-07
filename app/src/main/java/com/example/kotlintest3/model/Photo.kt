package com.example.kotlintest3.model

import java.io.Serializable

/**
 * 3. 포토 데이터 설정
 * 어떤 것을 가져올지 미리 설정한다.
 */
data class Photo(var thumbnail: String?,
                 var author: String?,
                 var createdAt: String?,
                 var likesCount: Int?
                 ) : Serializable   //이걸 안 하면 오류가 난다.
{

}