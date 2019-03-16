package com.fernandocejas.sample.data

import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.functional.Either
import com.fernandocejas.sample.core.platform.NetworkHandler
import com.fernandocejas.sample.domain.MovieDetails
import com.fernandocejas.sample.data.service.MovieDetailsEntity
import com.fernandocejas.sample.domain.Movie
import com.fernandocejas.sample.domain.MoviesRepository
import com.fernandocejas.sample.data.service.MoviesService
import retrofit2.Call
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(private val networkHandler: NetworkHandler, private val service: MoviesService) : MoviesRepository {

    override fun movies(): Either<Failure, List<Movie>> {
        return when (networkHandler.isConnected) {
            true -> request(service.movies(), { list -> list.map { Movie(it.id, it.poster) } }, emptyList())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override fun movieDetails(movieId: Int): Either<Failure, MovieDetails> {
        return when (networkHandler.isConnected) {
            true -> request(service.movieDetails(movieId), { it.toMovieDetails() }, MovieDetailsEntity.empty())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}