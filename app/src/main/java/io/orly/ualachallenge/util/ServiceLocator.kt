package io.orly.ualachallenge.util

import io.orly.ualachallenge.data.remote.UalaApi

interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }
    }

    fun getUalaApi(): UalaApi

    open class DefaultServiceLocator : ServiceLocator {
        private val api by lazy {
            UalaApi.create()
        }

        override fun getUalaApi(): UalaApi = api
    }
}