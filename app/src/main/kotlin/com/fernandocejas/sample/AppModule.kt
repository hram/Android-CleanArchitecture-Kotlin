package com.fernandocejas.sample

import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.core.platform.NetworkHandler
import com.fernandocejas.sample.data.MoviesRepositoryImpl
import com.fernandocejas.sample.data.service.MoviesService
import com.fernandocejas.sample.domain.GetMovieDetails
import com.fernandocejas.sample.domain.GetMovies
import com.fernandocejas.sample.domain.MoviesRepository
import com.fernandocejas.sample.domain.PlayMovie
import com.fernandocejas.sample.presentation.features.login.Authenticator
import com.fernandocejas.sample.presentation.features.movies.details.MovieDetailsAnimator
import com.fernandocejas.sample.presentation.features.movies.details.MovieDetailsViewModel
import com.fernandocejas.sample.presentation.features.movies.list.MoviesAdapter
import com.fernandocejas.sample.presentation.features.movies.list.MoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    viewModel {
        MovieDetailsViewModel(get(), get())
    }

    viewModel {
        MoviesViewModel(get())
    }

    single<OkHttpClient> {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        okHttpClientBuilder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    single {
        MoviesService(get())
    }

    single<NetworkHandler> {
        NetworkHandler(get())
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

    single {
        Authenticator()
    }

    single {
        Navigator(get())
    }

    factory {
        GetMovieDetails(get())
    }

    factory {
        PlayMovie(get(), get())
    }

    factory {
        GetMovies(get())
    }

    factory {
        MoviesAdapter()
    }

    factory {
        MovieDetailsAnimator()
    }
}