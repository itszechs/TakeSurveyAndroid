package zechs.takesurvey.data.remote

import retrofit2.Response
import retrofit2.http.*
import zechs.takesurvey.data.models.*

interface TakeSurveyApi {

    @POST("/api/v1/poll")
    suspend fun createPoll(
        @Body pollRequest: PollRequest
    ): Response<CreateResponse>

    @GET("/api/v1/poll/{pollId}")
    suspend fun getPoll(
        @Path("pollId") pollId: String
    ): Response<PollResponse>

    @PATCH("/api/v1/poll/{pollId}")
    suspend fun votePoll(
        @Path("pollId") pollId: String,
        @Body voteRequest: VoteRequest
    ): Response<MessageResponse>

}