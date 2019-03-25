package com.example.breezil.giffs.repository

//enum class Status {
//    RUNNING,
//    SUCCESS,
//    FAILED
//}
//
//@Suppress("DataClassPrivateConstructor")
//data class NetworkState private constructor(
//    val status: Status,
//    val msg: String? = null) {
//    companion object {
//        val LOADED = NetworkState(Status.SUCCESS)
//        val LOADING = NetworkState(Status.RUNNING)
//        val FAILED: NetworkState?
//
//        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
//    }
//}



//enum class Status {
//    RUNNING,
//    SUCCESS,
//    FAILED,
//    NO_RESULT
//}
//data class NetworkState private  constructor(
//    val status: Status,
//    val msg: String? = null) {
//
//
//    companion object {
//
//        val LOADED: NetworkState = NetworkState(Status.SUCCESS)
//        val LOADING: NetworkState = NetworkState(Status.RUNNING)
//
//        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
//
//    }
//}
class NetworkState(val status: Status) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        NO_RESULT
    }

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS)
            LOADING = NetworkState(Status.RUNNING)
        }
    }
}