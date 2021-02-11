package org.d3if4034.bukuharianku.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if4034.bukuharianku.model.Barang
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://jsonplaceholder.typicode.com/todos/{id}")
    .build()

//API
interface BarangService {
    @GET("https://jsonplaceholder.typicode.com/todos")
    suspend fun showList(): List<Barang>
}

object BarangAPI {
    val retrofitService: BarangService = retrofit.create(BarangService::class.java)
}