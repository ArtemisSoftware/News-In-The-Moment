package di

import data.remote.source.NewsSource
import data.repository.NewsRepositoryImpl
import domain.repository.NewsRepository
import domain.usecases.GetArticlesUseCase
import domain.usecases.GetTopicsUseCase
import domain.usecases.LoadImageAsyncUseCase
import domain.usecases.SearchArticlesUseCase
import org.koin.dsl.module

val appModule = module {
    single<NewsSource> { NewsSource() }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single<GetArticlesUseCase> { GetArticlesUseCase(get()) }
    single<SearchArticlesUseCase> { SearchArticlesUseCase(get()) }
    single<GetTopicsUseCase> { GetTopicsUseCase() }
    single<LoadImageAsyncUseCase> { LoadImageAsyncUseCase() }
}
