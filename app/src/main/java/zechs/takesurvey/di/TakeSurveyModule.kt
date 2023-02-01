package zechs.takesurvey.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import zechs.takesurvey.data.remote.TakeSurveyApi
import zechs.takesurvey.data.repository.TakeSurveyRepository
import zechs.takesurvey.utils.Constants.Companion.TAKESURVEY_API
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TakeSurveyModule {

    @Provides
    @Singleton
    fun provideTakeSurvey(
        client: OkHttpClient,
        moshi: Moshi
    ): TakeSurveyApi {
        return Retrofit.Builder()
            .baseUrl(TAKESURVEY_API)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TakeSurveyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTakeSurveyRepository(
        TakeSurveyApi: TakeSurveyApi
    ): TakeSurveyRepository {
        return TakeSurveyRepository(TakeSurveyApi)
    }

}
