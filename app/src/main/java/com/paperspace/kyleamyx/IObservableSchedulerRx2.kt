package com.paperspace.kyleamyx

import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

interface IObservableSchedulerRx2 {

    fun <T> schedule(): ObservableTransformer<T, T>

    fun <T> scheduleSingle(): SingleTransformer<T, T>

    companion object {

        val SUBSCRIBE_IO_OBSERVE_ANDROID_MAIN: IObservableSchedulerRx2 = object : IObservableSchedulerRx2 {
            override fun <T> schedule(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

            override fun <T> scheduleSingle(): SingleTransformer<T, T> {
                return SingleTransformer { upstream ->
                    upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }
        }

        val SUBSCRIBE_COMPUTATION_OBSERVE_ANDROID_MAIN: IObservableSchedulerRx2 = object : IObservableSchedulerRx2 {
            override fun <T> schedule(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    upstream
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

            override fun <T> scheduleSingle(): SingleTransformer<T, T> {
                return SingleTransformer { upstream ->
                    upstream
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }
        }

        val TRAMPOLINE: IObservableSchedulerRx2 = object : IObservableSchedulerRx2 {
            override fun <T> schedule(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    val scheduler = TestScheduler()
                    upstream.subscribeOn(Schedulers.trampoline())
                            .observeOn(Schedulers.trampoline())
                }
            }

            override fun <T> scheduleSingle(): SingleTransformer<T, T> {
                return SingleTransformer { upstream ->
                    val scheduler = TestScheduler()
                    upstream.subscribeOn(Schedulers.trampoline())
                            .observeOn(Schedulers.trampoline())
                }
            }
        }

        val COMPUTATION: IObservableSchedulerRx2 = object : IObservableSchedulerRx2 {
            override fun <T> schedule(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    upstream.subscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.computation())
                }
            }

            override fun <T> scheduleSingle(): SingleTransformer<T, T> {
                return SingleTransformer { upstream ->
                    upstream.subscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.computation())
                }
            }
        }

        val IO: IObservableSchedulerRx2 = object : IObservableSchedulerRx2 {
            override fun <T> schedule(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    upstream.subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                }
            }


            override fun <T> scheduleSingle(): SingleTransformer<T, T> {
                return SingleTransformer { upstream ->
                    upstream.subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                }
            }
        }
    }
}