package zechs.takesurvey.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import zechs.takesurvey.data.models.*

interface TakeSurveyApi {

    @POST("/api/v1/poll")
    suspend fun createPoll(
        @Body pollRequest: PollRequest
    ): Response<CreateResponse>

    @GET("/api/v1/poll/:pollId")
    suspend fun getPoll(
        pollId: String
    ): Response<PollResponse>

    @PATCH("/api/v1/poll/:pollId")
    suspend fun votePoll(
        pollId: String,
        @Body voteRequest: VoteRequest
    ): Response<MessageResponse>

}