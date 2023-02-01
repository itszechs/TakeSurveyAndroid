package zechs.takesurvey.data.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import zechs.takesurvey.data.models.*
import zechs.takesurvey.data.remote.TakeSurveyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TakeSurveyRepository @Inject constructor(
    private val api: TakeSurveyApi
) {

    suspend fun createPoll(
        title: String,
        options: List<String>
    ): Response<CreateResponse> {
        if (options.size < 2) {
            return Response.error(
                400,
                "Poll must have at least 2 options"
                    .toResponseBody("text/plain".toMediaTypeOrNull())
            )
        }
        return api.createPoll(PollRequest(title, options))
    }

    suspend fun getPoll(
        pollId: String
    ): Response<PollResponse> {
        return api.getPoll(pollId)
    }

    suspend fun votePoll(
        pollId: String,
        optionId: String
    ): Response<MessageResponse> {
        return api.votePoll(pollId, VoteRequest(optionId))
    }

}