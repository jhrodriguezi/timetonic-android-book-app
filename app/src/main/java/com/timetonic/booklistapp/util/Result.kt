package com.timetonic.booklistapp.util

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Sealed class representing the result of an operation.
 * @param T The type of data encapsulated in the result.
 */
sealed class Result<out T: Any> {

    /**
     * Represents a successful result containing data of type [T].
     * @param data The data encapsulated in the success result.
     */
    data class Success<T: Any>(val data: T) : Result<T>()

    /**
     * Represents an error result containing a throwable.
     * @param throwable The throwable encapsulated in the error result.
     */
    data class Error(val throwable: Throwable) : Result<Nothing>()
}

/**
 * Wrapper for a Retrofit Call to return a Result<T>.
 * @param T The type of data expected from the Retrofit call.
 * @property proxy The original Retrofit Call object.
 */
class ResultCall<T: Any>(
    private val proxy: Call<T>
) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        proxy.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Throwable(response.message()))
                }
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = Result.Error(t)
                callback.onResponse(this@ResultCall, Response.success(result))
            }

        })
    }

    override fun execute(): Response<Result<T>> = throw NotImplementedError()

    override fun clone(): Call<Result<T>> = ResultCall(proxy.clone())

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() { proxy.cancel() }

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()
}

/**
 * Adapter for Retrofit to convert Call<T> to Call<Result<T>>.
 * @property resultType The type of data expected in the Result.
 */
class ResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<Result<Type>> {
        return ResultCall(call)
    }
}

/**
 * Factory for creating ResultCallAdapter instances.
 */
class ResultCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != Result::class.java) {
            return null
        }

        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        return ResultCallAdapter(resultType)
    }

    companion object {
        fun create(): ResultCallAdapterFactory = ResultCallAdapterFactory()
    }
}