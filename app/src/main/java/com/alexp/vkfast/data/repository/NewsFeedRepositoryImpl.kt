package com.alexp.vkfast.data.repository

import android.app.Application
import com.alexp.vkfast.data.mapper.NewsFeedMapper
import com.alexp.vkfast.data.network.ApiFactory
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.NewsFeedResult
import com.alexp.vkfast.domain.entity.PostComment
import com.alexp.vkfast.domain.entity.StatisticItem
import com.alexp.vkfast.domain.entity.StatisticType
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import com.alexp.vkfast.extensions.mergeWith
import com.alexp.vkfast.presentation.main.AuthState
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl(application: Application) :NewsFeedRepository{

    private val token
        get() =  VKID.instance.accessToken?.token


    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<NewsItem>>()
    private val loadedListFlow = flow {


        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && newsItems.isNotEmpty()) {
                emit(newsItems)
                return@collect
            }
            val response =
                if (startFrom == null) {

                    apiService.loadRecommendations(getAccessToken())

                } else {
                    apiService.loadRecommendations(getAccessToken(), startFrom)
                }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _newsItems.addAll(posts)
            emit(newsItems)
        }


    }
        .map { NewsFeedResult.Success(post = it) as NewsFeedResult}
        .retry (2){
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.catch {

        emit(NewsFeedResult.Error)
    }

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _newsItems = mutableListOf<NewsItem>()
    private val newsItems: List<NewsItem>
        get() = _newsItems.toList()

    private var nextFrom: String? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotAuthorized)
    private val authStateFlow: StateFlow<AuthState> = _authState




    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Authorized
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
        }
    }
    suspend fun authorizeUser() {

        VKID.instance.authorize(
            callback = vkAuthCallback,
            params = VKIDAuthParams {
                scopes = setOf("phone", "email", "wall", "friends")
            }
        )
    }


    private val recommendations: StateFlow<NewsFeedResult> = loadedListFlow
        .mergeWith(refreshedListFlow.map { NewsFeedResult.Success(it) })
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = NewsFeedResult.Success(newsItems)
        )

    override fun getAuthStateFlow(): StateFlow<AuthState> {
        return _authState
    }

    override fun getRecommendations(): StateFlow<NewsFeedResult> {
       return recommendations
    }

    override suspend fun loadMoreNews() {
        nextDataNeededEvents.emit(Unit)
    }

    override fun getAccessToken(): String {

        return token ?: throw IllegalStateException("Token is empty")
    }

    override suspend fun updateLikeStatus(newsItem: NewsItem) {

        val response = if (newsItem.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = newsItem.groupId,
                postId = newsItem.newsId
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = newsItem.groupId,
                postId = newsItem.newsId
            )
        }
        val newLikesCount = response.likes.count
        val newStstistics = newsItem.stats.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = newsItem.copy(stats = newStstistics, isLiked = !newsItem.isLiked)

        val postIndex = _newsItems.indexOf(newsItem)

        _newsItems[postIndex] = newPost
        refreshedListFlow.emit(newsItems)

    }

    override suspend fun removeNewsItem(newsItem: NewsItem) {
        apiService.ignorePost(
            accessToken = getAccessToken(),
            ownerId = newsItem.groupId,
            postId = newsItem.newsId
        )
        _newsItems.remove(newsItem)
        refreshedListFlow.emit(newsItems)
    }

    override fun getComments(newsItem: NewsItem): StateFlow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            accessToken = getAccessToken(),
            ownerId = newsItem.groupId,
            postId = newsItem.newsId
        )
        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true

    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        listOf()
    )


    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}