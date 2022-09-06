package com.example.kotlintest3.model

import java.io.Serializable

data class Photo(var thumbnail: String?,
                 var author: String?,
                 var createdAt: String?,
                 var likesCount: Int?
                 ) : Serializable   //이걸 안 하면 오류가 난다.
{

}