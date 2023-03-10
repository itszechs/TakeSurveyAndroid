package zechs.takesurvey.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zechs.takesurvey.data.models.MessageResponse
import zechs.takesurvey.data.repository.TakeSurveyRepository
import zechs.takesurvey.ui.attempt.FetchSurveyUiState
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val takeSurveyRepository: TakeSurveyRepository
) : ViewModel() {

    private val _fetchState = MutableLiveData<FetchSurveyUiState>()
    val fetchState: LiveData<FetchSurveyUiState> = _fetchState

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
