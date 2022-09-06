package com.example.kotlintest3.utils

object Constants {
    const val TAG : String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT
}

object API {
    const val BASE_URL : String = "https://api.unsplash.com/"

    const val CLIENT_ID : String = "AQbZhCX6n24j1ygl0hfZx2tAM1x8RglPJLfuYFr2fH8"

    const val SEARCH_PHOTO : String = "search/photos"
    const val SEARCH_USERS : String = "search/users"
}