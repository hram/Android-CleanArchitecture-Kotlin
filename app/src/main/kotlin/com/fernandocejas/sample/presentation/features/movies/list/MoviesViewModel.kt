/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.presentation.features.movies.list

import android.arch.lifecycle.MutableLiveData
import com.fernandocejas.sample.core.interactor.UseCase.None
import com.fernandocejas.sample.presentation.BaseViewModel
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: com.fernandocejas.sample.domain.GetMovies) : BaseViewModel() {

    var movies: MutableLiveData<List<com.fernandocejas.sample.presentation.MovieView>> = MutableLiveData()

    fun loadMovies() = getMovies(None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<com.fernandocejas.sample.domain.Movie>) {
        this.movies.value = movies.map { com.fernandocejas.sample.presentation.MovieView(it.id, it.poster) }
    }
}