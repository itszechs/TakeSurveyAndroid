package zechs.takesurvey.ui.attempt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zechs.takesurvey.data.models.MessageResponse
import zechs.takesurvey.data.models.PollResponse
import zechs.takesurvey.data.repository.TakeSurveyRepository
import javax.inject.Inject

@HiltViewModel
class AttemptViewModel @Inject constructor(
    private val takeSurveyRepository: TakeSurveyRepository
) : ViewModel() {

    private val _attemptState = MutableLiveData<AttemptSurveyUiState>()
    val attemptState: LiveData<AttemptSurveyUiState> = _attemptState

    private val _fetchState = MutableLiveData<FetchSurveyUiState>()
    val fetchState: LiveData<FetchSurveyUiState> = _fetchState

    fun attemptSurvey(
        pollId: String,
        pollOption: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _attemptState.postValue(AttemptSurveyUiState.Attempting)
        try {
            takeSurveyRepository.votePoll(pollId, pollOption).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        _attemptState.postValue(AttemptSurveyUiState.AttemptedStatus(it.message))
                    } ?: {
                        _attemptState.postValue(AttemptSurveyUiState.Error("Unknown error"))
                    }
                } else {
                    _attemptState.postValue(AttemptSurveyUiState.Error(response.message()))
                }
            }
        } catch (e: Exception) {
            _attemptState.postValue(
                AttemptSurveyUiState.Error(
                    e.message ?: "Something went wrong!"
                )
            )
        }
    }

    fun fetchSurvey(
        pollId: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _fetchState.postValue(FetchSurveyUiState.Fetching)
        try {
            val response = takeSurveyRepository.getPoll(pollId)
            if (response.isSuccessful) {
                _fetchState.postValue(
                    FetchSurveyUiState.FetchedSurvey(response.body()!!)
                )
            } else {
                _fetchState.postValue(
                    FetchSurveyUiState.Error(
                        response.errorBody()?.string()?.let {
                            Gson().fromJson(it, MessageResponse::class.java).message
                        } ?: "An unknown error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            _fetchState.postValue(
                FetchSurveyUiState.Error(e.message ?: "Something went wrong!")
            )
        }
    }

}

sealed class FetchSurveyUiState {
    object Fetching : FetchSurveyUiState()
    data class FetchedSurvey(val survey: PollResponse) : FetchSurveyUiState()
    data class Error(val message: String) : FetchSurveyUiState()
}

sealed class AttemptSurveyUiState {
    object Attempting : AttemptSurveyUiState()
    data class AttemptedStatus(val message: String) : AttemptSurveyUiState()
    data class Error(val message: String) : AttemptSurveyUiState()
}