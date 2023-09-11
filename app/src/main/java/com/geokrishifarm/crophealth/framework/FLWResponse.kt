package com.geokrishifarm.crophealth.framework

class FLWResponse<T> {
    var status: Status
    var data: T?
    var error: String?
    var progressStat: ProgressStat? = null

    constructor(status: Status, data: T? = null, error: String?) {
        this.status = status
        this.data = data
        this.error = error
    }

    constructor(status: Status, data: T? = null, error: String?, progressStat: ProgressStat) {
        this.status = status
        this.data = data
        this.error = error
        this.progressStat = progressStat
    }


    enum class Status {
        INIT, LOADING, LOADING_FINISHED, SUCCESS, ERROR, NO_INTERNET_AVAILABLE, TOKEN_EXPIRED, IN_PROGRESS, USER_NOT_VERIFIED, EMPTY, EXCEPTION, SAVEONDB
    }

    companion object {

        fun <T> init(): FLWResponse<T> {
            return FLWResponse<T>(Status.INIT, null, null)
        }

        fun <T> success(data: T): FLWResponse<T> {
            return FLWResponse(Status.SUCCESS, data, null)
        }


        fun <T> saveonDB(data: T): FLWResponse<T> {
            return FLWResponse(Status.SAVEONDB, data, null)
        }

        fun <T> error(error: String?): FLWResponse<T> {
            return FLWResponse(Status.ERROR, null, error)
        }

        fun <T> tokenExpired(error: String?): FLWResponse<T> {
            return FLWResponse(Status.TOKEN_EXPIRED, null, error)
        }

        fun <T> onException(error: String?): FLWResponse<T> {
            return FLWResponse(Status.EXCEPTION, null, error)
        }


        fun <T> userNotVerified(error: String?): FLWResponse<T> {
            return FLWResponse(Status.USER_NOT_VERIFIED, null, error)
        }

        fun <T> inProgress(data: ProgressStat): FLWResponse<T> {
            return FLWResponse(Status.IN_PROGRESS, null, null, data)
        }

        fun <T> loading(): FLWResponse<T> {
            return FLWResponse<T>(Status.LOADING, null, null)
        }

        fun <T> empty(message: String?): FLWResponse<T> {
            return FLWResponse(Status.EMPTY, null, message)
        }

        fun <T> loadingFinished(): FLWResponse<T> {
            return FLWResponse(Status.LOADING_FINISHED, null, null)
        }

        fun <T> noInternetAvailable(): FLWResponse<T> {
            return FLWResponse(Status.NO_INTERNET_AVAILABLE, null, "No internet connection found")
        }
    }


    class ProgressStat {
        var message: String = ""
        var percent: Double = 0.0
        var totalFileCount: Int = 0
        var totalUploadFileCountTillNow = 0
        var totalUploadFileTillNowInMb = 0.0
        var totalSizeFileInMb = 0.0
    }


}