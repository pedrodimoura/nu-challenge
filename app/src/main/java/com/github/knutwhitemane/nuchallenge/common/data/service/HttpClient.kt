package com.github.knutwhitemane.nuchallenge.common.data.service

sealed interface HttpClient {
    interface RetrofitClient : HttpClient {
        fun <T> create(c: Class<T>): T
    }
}
